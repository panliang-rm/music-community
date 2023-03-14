package com.example.graduate.controller;


import com.example.graduate.common.ErrorMessage;
import com.example.graduate.common.FatalMessage;
import com.example.graduate.common.SuccessMessage;
import com.example.graduate.constant.Constants;
import com.example.graduate.domain.Swiper;
import com.example.graduate.service.impl.SwiperServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class SwiperController {

    @Autowired
    private SwiperServiceImpl swiperService;

    @Configuration
    public static class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/swiper/**")
                    .addResourceLocations(Constants.SWIPER_PIC_PATH);
        }
    }

    //返回所有banner
    @RequestMapping(value = "/swiper", method = RequestMethod.GET)
    public Object allSwiper() {
        return new SuccessMessage<>(null, swiperService.allSwiper()).getMessage();
    }

    // 删除banner
    @RequestMapping(value = "/swiper/delete", method = RequestMethod.GET)
    public Object deleteSwiper(HttpServletRequest req) {
        String id = req.getParameter("id");
        boolean res = swiperService.deleteSwiper(Integer.parseInt(id));
        if (res) {
            return new SuccessMessage<ObjectUtils.Null>("删除成功").getMessage();
        } else {
            return new ErrorMessage("删除失败").getMessage();
        }
    }

    // 更新swiper banner
    @ResponseBody
    @RequestMapping(value = "/swiper/photo/update", method = RequestMethod.POST)
    public Object updateSwiperPhoto(@RequestParam("file") MultipartFile photoFile, @RequestParam("id") int id) {
        String fileName = System.currentTimeMillis() + photoFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "music-server" + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "swiper";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/swiper/" + fileName;
        try {
            photoFile.transferTo(dest);
            Swiper swiper = new Swiper();
            swiper.setId(id);
            swiper.setPhoto(imgPath);

            boolean res = swiperService.updateSwiperPhoto(swiper);
            if (res) {
                return new SuccessMessage<>("上传成功", imgPath).getMessage();
            } else {
                return new ErrorMessage("上传失败").getMessage();
            }
        } catch (IOException e) {
            return new FatalMessage("上传失败" + e.getMessage()).getMessage();
        }
    }

    // 更新swiper banner isshow
    @ResponseBody
    @RequestMapping(value = "/swiper/update/isshow", method = RequestMethod.POST)
    public Object updateSingerMsg(HttpServletRequest req) {
        String id = req.getParameter("id").trim();
        String isshow = req.getParameter("isshow").trim();
        Swiper swiper = new Swiper();
        swiper.setId(Integer.parseInt(id));
        swiper.setisShow(new Byte(isshow));
        boolean res = swiperService.updateSwiperMsg(swiper);
        if (res) {
            return new SuccessMessage<ObjectUtils.Null>("修改成功").getMessage();
        } else {
            return new ErrorMessage("修改失败").getMessage();
        }
    }


}

