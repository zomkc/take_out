package com.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.common.CustomException;
import com.cn.dto.SetmealDto;
import com.cn.entity.Setmeal;
import com.cn.entity.SetmealDish;
import com.cn.mapper.SetmealMapper;
import com.cn.service.SetmealDishService;
import com.cn.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息,操作setmeal
        this.save(setmealDto);

        //保存套餐和菜品的关联信息,操作setmeal_dish
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //遍历setmeal_dish,给每一个id赋值setmeal的id
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);


    }

    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐是否停售
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids)
                .eq(Setmeal::getStatus,1);
        long count = this.count(queryWrapper);
        if (count > 0){
            throw new CustomException("套餐正在售卖中,不能删除");
        }
        //删除套餐表数据
        this.removeByIds(ids);

        //删除套餐关联菜品表数据
        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(queryWrapper2);
    }

    @Resource
    private SetmealMapper setmealMapper;
    @Override
    public void stopById(int status, Long id) {
        setmealMapper.stopById(status,id);
    }
}
