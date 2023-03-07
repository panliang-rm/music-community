package com.example.graduate.dao;

import com.example.graduate.domain.Admin;
import com.example.graduate.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {


    List<Admin> allAdminUser();

    List<Admin> adminLoginStatus(String name);


    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    int verifyPassword(String username, String password);

    int updateAdminAvator(Admin record);
}
