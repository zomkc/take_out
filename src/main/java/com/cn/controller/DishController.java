package com.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.R;
import com.cn.dto.DishDto;
import com.cn.entity.Category;
import com.cn.entity.Dish;
import com.cn.entity.DishFlavor;
import com.cn.service.CategoryService;
import com.cn.service.DishFlavorService;
import com.cn.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Resource
    private RedisTemplate redisTemplate;


    //新增菜品
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);

        //新增后清理相应分类下的redis缓存
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);

        return R.success("新增菜品成功");
    }

    //分页
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category byId = categoryService.getById(categoryId);
            if (byId != null) {
                String categoryName = byId.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());


        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);

    }


    //根据id查询对应的菜品信息,和口味信息
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);

        //修改后相应菜品redis缓存
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);

        return R.success("修改菜品成功");
    }

    //停售菜品
    @PostMapping("status/{status}")
    public R<String> Stop(@PathVariable int status, @RequestParam List<Long> ids) {
        ids.forEach(id -> {
            Dish dish = dishService.getById(id);
            String key = "dish_"+dish.getCategoryId()+"_1";
            //停售/起售 菜品后,删除对应分类redis缓存
            redisTemplate.delete(key);
            dishService.updateStatusById(status, id);
        });


        return R.success("停售成功");
    }


    @Autowired
    DishFlavorService dishFlavorService;
    //根据条件查询菜品数据,查询套餐里的菜品
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;
        //动态构造key
        String key = "dish_"+ dish.getCategoryId()+"_"+dish.getStatus();
        //从redis获取缓存数据
        dishDtoList =  (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null){
            //如果存在直接访问数据库,无需访问
            return R.success(dishDtoList);
        }

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                .orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime)
                .eq(Dish::getStatus, 1);//只查询 启售菜品

        List<Dish> list = dishService.list(queryWrapper);

            dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category byId = categoryService.getById(categoryId);
            if (byId != null) {
                String categoryName = byId.getName();
                dishDto.setCategoryName(categoryName);
            }

            //菜品id
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId,id);
            //根据菜品id查询并赋值口味
            List<DishFlavor> flavors = dishFlavorService.list(wrapper);
            dishDto.setFlavors(flavors);

            return dishDto;
        }).collect(Collectors.toList());

        //如果不存在,则查询数据库后,将查询到的菜品缓存到redis
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        ids.forEach(id -> {
            Dish dish = dishService.getById(id);
            String key = "dish_"+dish.getCategoryId()+"_1";
//            List<DishDto> o = (List<DishDto>) redisTemplate.opsForValue().get(key);
//            System.out.println(o.toString());
            //删除菜品后,删除对应分类redis缓存
            redisTemplate.delete(key);
            dishService.removeById(id);
        });

        return R.success("删除成功");
    }
}
