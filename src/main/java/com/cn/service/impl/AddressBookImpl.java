package com.cn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.entity.AddressBook;
import com.cn.mapper.AddressBookMapper;
import com.cn.service.AddressBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressBookImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Resource
    AddressBookMapper addressBookMapper;

    @Override
    public Long SaveBook(AddressBook addressBook) {
        Long userId = addressBook.getUserId();
        String consignee = addressBook.getConsignee();
        String phone = addressBook.getPhone();
        String sex = addressBook.getSex();
        String detail = addressBook.getDetail();
        String label = addressBook.getLabel();
        return addressBookMapper.SaveBook(userId,consignee,phone,sex,detail,label);
    }
}
