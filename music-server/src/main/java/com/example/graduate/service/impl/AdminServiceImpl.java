package com.example.graduate.service.impl;

import com.example.graduate.dao.AdminMapper;
import com.example.graduate.domain.Admin;
import com.example.graduate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> adminLoginStatus(String name) {
        return adminMapper.adminLoginStatus(name);
    }

    @Override
    public List<Admin> allAdminUser() {
        return adminMapper.allAdminUser();
    }

    @Override
    public boolean veritypasswd(String name, String password) {

        return adminMapper.verifyPassword(name, password)>0?true:false;
    }

    @Override
    public boolean updateUserAvator(Admin admin) {
        return adminMapper.updateAdminAvator(admin) > 0;
    }
}
