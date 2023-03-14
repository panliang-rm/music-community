package com.example.graduate.dao;

import com.example.graduate.domain.Swiper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiperMapper {

    List<Swiper> allSwiper();


    int deleteByPrimaryKey(Integer id);


    int insertSwiper(Swiper swiper);

    int updateByPrimaryKeySelective(Swiper swiper);


    
}
