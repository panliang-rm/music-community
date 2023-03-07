package com.example.graduate.controller;

import com.example.graduate.common.ErrorMessage;
import com.example.graduate.common.FatalMessage;
import com.example.graduate.common.SuccessMessage;
import com.example.graduate.constant.Constants;
import com.example.graduate.domain.Admin;
import com.example.graduate.domain.Consumer;
import com.example.graduate.service.impl.AdminServiceImpl;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    // 判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/admin/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session) {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        boolean res = adminService.veritypasswd(name, password);
        if (res) {
            session.setAttribute("name", name);
            return new SuccessMessage<>("登录成功", adminService.adminLoginStatus(name)).getMessage();
        } else {
            return new ErrorMessage("用户名或密码错误").getMessage();
        }
    }

    /**
     * 返回所有管理员
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Object allUser() {
        return new SuccessMessage<>(null, adminService.allAdminUser()).getMessage();
    }

    /**
     * 更新管理员头像
     */
    @ResponseBody
    @RequestMapping(value = "/admin/avatar/update", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = Constants.PROJECT_PATH + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "avatorImages";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/avatorImages/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Admin admin = new Admin();
            admin.setId(id);
            admin.setAvator(imgPath);
            boolean res = adminService.updateUserAvator(admin);
            if (res) {
                return new SuccessMessage<String>("上传成功", imgPath).getMessage();
            } else {
                return new ErrorMessage("上传失败").getMessage();
            }
        } catch (IOException e) {
            return new FatalMessage("上传失败" + e.getMessage()).getMessage();
        }
    }
}
