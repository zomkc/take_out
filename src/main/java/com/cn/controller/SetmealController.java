package com.cn.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.R;
import com.cn.dto.SetmealDto;
import com.cn.entity.Category;
import com.cn.entity.Dish;
import com.cn.entity.Setmeal;
import com.cn.entity.SetmealDish;
import com.cn.service.CategoryService;
import com.cn.service.DishService;
import com.cn.service.SetmealDishService;
import com.cn.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.*;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    //新增套餐
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }

    @Autowired
    private CategoryService categoryService;
    //分页
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Setmeal::getName,name)
                .orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        copyProperties(pageInfo,setmealDtoPage,"records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            copyProperties(item,setmealDto);

            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category byId = categoryService.getById(categoryId);
            if (byId != null) {
                String categoryName = byId.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());


        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);

    }

    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)//删除套餐,删除当前属性下的所有数据
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    //停售套餐
    @PostMapping("status/{status}")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> Stop(@PathVariable int status,@RequestParam List<Long> ids){

        ids.forEach((id) -> {
            setmealService.stopById(status,id);
        });

        return R.success("停售成功");
    }


    //根据条件查询菜品数据,查询套餐里的菜品
    @GetMapping("/list")
    @Cacheable({"setmealCache"})
    public R<List<Setmeal>> list(Setmeal dish) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Setmeal::getCategoryId, dish.getCategoryId())
                .orderByAsc(Setmeal::getUpdateTime)
                .eq(Setmeal::getStatus, 1);//只查询 启售菜品

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }

    //修改套餐信息
    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> put(@RequestBody SetmealDto setmealDto){
        setmealService.put(setmealDto);

        return R.success("修改套餐成功");
    }


    //管理端套餐详情
    @GetMapping("/{id}")
    public R<SetmealDto> rundish(@PathVariable Long id){
        SetmealDto dto = new SetmealDto();

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        //将套餐菜品信息赋值给dto
        dto.setSetmealDishes(list);

        Setmeal setmeal = setmealService.getById(id);
        //将套餐信息赋值给dot
        BeanUtils.copyProperties(setmeal,dto);

        return R.success(dto);
    }

    //客户端套餐详情
    @Autowired
    private DishService dishService;
    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> dish(@PathVariable Long id){
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

       list.forEach(i -> {
           //根据SetmealDish的菜品id查询dish表
           Dish dish = dishService.getById(i.getDishId());
           //将dish的image赋值给SetmealDish
           i.setImage(dish.getImage());
       });

        return R.success(list);
    }

}
