package com.example.service;

import com.example.entity.Money;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.MoneyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MoneyService extends ServiceImpl<MoneyMapper, Money> {

    @Resource
    private MoneyMapper moneyMapper;

}
