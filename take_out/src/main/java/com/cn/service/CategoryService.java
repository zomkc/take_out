package com.cn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
