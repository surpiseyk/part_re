package com.example.service;

import com.example.entity.Conference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ConferenceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConferenceService extends ServiceImpl<ConferenceMapper, Conference> {

    @Resource
    private ConferenceMapper conferenceMapper;

}
