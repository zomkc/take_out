package com.cn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.dto.SetmealDto;
import com.cn.entity.Setmeal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //新增套餐,同时保存套餐和菜品
    public void saveWithDish(SetmealDto setmealDto);


    //删除套餐
    public void removeWithDish(List<Long> ids);

    //停售
    public void stopById(@PathVariable int status, @RequestParam Long id);
}
