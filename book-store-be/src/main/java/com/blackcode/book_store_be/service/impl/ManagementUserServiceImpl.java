package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.service.ManagementUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagementUserServiceImpl implements ManagementUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<ManagementUserRes> getListAll() {
        List<User> userList = userRepository.findAll();
        List<ManagementUserRes> userResList = new ArrayList<>();
        for (User rowUser : userList){
            System.out.println("Data User "+rowUser.getUserId());

            ManagementUserRes userManagementRes = new ManagementUserRes();
            userManagementRes.setUserId(rowUser.getUserId());
            userManagementRes.setUserFullName(rowUser.getUserFullName());
            userManagementRes.setUserEmail(rowUser.getUserEmail());
            userManagementRes.setUserName(rowUser.getUserName());
            userManagementRes.setUserPassword(rowUser.getUserPassword());
            userManagementRes.setUserPassword(rowUser.getUserPassword());
            userResList.add(userManagementRes);
        }

        System.out.println("heck data :"+userResList.size());
        return userResList;
    }

    @Override
    public ManagementUserRes getFindById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        ManagementUserRes userManagementRes = null;
        if(user.isPresent()){
            userManagementRes = new ManagementUserRes();
            userManagementRes.setUserId(user.get().getUserId());
            userManagementRes.setUserFullName(user.get().getUserFullName());
            userManagementRes.setUserEmail(user.get().getUserEmail());
            userManagementRes.setUserName(user.get().getUserName());
            userManagementRes.setUserPassword(user.get().getUserPassword());
            userManagementRes.setUserPassword(user.get().getUserPassword());
            return userManagementRes;
        }
        return null;
    }
}
