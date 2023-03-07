package com.example.graduate.service;

import com.example.graduate.domain.Admin;
import com.example.graduate.domain.Consumer;

import java.util.List;

public interface AdminService {


    List<Admin> adminLoginStatus(String name);

    List<Admin> allAdminUser();

    boolean veritypasswd(String name, String password);

    boolean updateUserAvator(Admin admin);
}
