package com.example.graduate.service;

import com.example.graduate.domain.Admin;

public interface AdminService {

    boolean veritypasswd(String name, String password);

    boolean updateUserAvator(Admin admin);
}
