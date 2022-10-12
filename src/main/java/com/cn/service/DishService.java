package com.cn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.dto.DishDto;
import com.cn.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品,同时插入菜品对应的口味数据,同时操作两张表: dish , dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询对应的菜品信息,和口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品,同时更新对应的口味
    public void updateWithFlavor(DishDto dishDto);

    public int updateStatusById(int status,Long id);

}
