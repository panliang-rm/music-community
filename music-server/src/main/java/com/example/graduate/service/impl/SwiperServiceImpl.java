package com.example.graduate.service.impl;

import com.example.graduate.dao.SwiperMapper;
import com.example.graduate.domain.Swiper;
import com.example.graduate.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiperServiceImpl implements SwiperService {

    @Autowired
    private SwiperMapper swiperMapper;

    @Override
    public boolean addSwiper(Swiper swiper) {
        return swiperMapper.insertSwiper(swiper) > 0;
    }

    @Override
    public boolean updateSwiperMsg(Swiper swiper) {
        return swiperMapper.updateByPrimaryKeySelective(swiper) > 0;
    }

    @Override
    public boolean updateSwiperPhoto(Swiper swiper) {
        return swiperMapper.updateSwiperPhoto(swiper) > 0;
    }

    @Override
    public boolean deleteSwiper(Integer id) {
        return swiperMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<Swiper> allSwiper() {
        return swiperMapper.allSwiper();
    }
}
