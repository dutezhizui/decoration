package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.UserMapper;
import com.darkcom.decoration.model.User;
import com.darkcom.decoration.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author yaojy
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUserInfo(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(timeout = 3000)
    public User selectByPhoneAndPwd(String account, String password) {
        return userMapper.selectByPhoneAndPwd(account, password);
    }

    @Override
    public void updateLastLoginTime(Long userId, Date lastLoginTime) {
        userMapper.updateLastLoginTime(userId, lastLoginTime);
    }

    @Override
    public User getUserInfoByUserName(String username) {
        return userMapper.getUserInfoByUserName(username);
    }

    @Override
    public void register(User user) {
        userMapper.insert(user);

    }

    @Override
    public void updateUserInfo(User user) {
        userMapper.updateUserByAccount(user);
    }

    @Override
    public User selectUserByAccount(String account) {
        return userMapper.selectUserByAccount(account);
    }
}
