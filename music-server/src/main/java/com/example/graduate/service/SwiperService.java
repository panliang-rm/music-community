package com.example.graduate.service;


import com.example.graduate.domain.Swiper;

import java.util.List;



public interface SwiperService {

    boolean addSwiper (Swiper swiper);

    boolean updateSwiperMsg(Swiper swiper);

    boolean updateSwiperPhoto(Swiper swiper);

    boolean deleteSwiper(Integer id);

    List<Swiper> allSwiper();
}
