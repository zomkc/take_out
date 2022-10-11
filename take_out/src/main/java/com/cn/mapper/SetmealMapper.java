package com.cn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    public void stopById(@PathVariable int status, @RequestParam Long id);

    }
