package com.darkcom.decoration.service;

import com.darkcom.decoration.model.User;

import java.util.Date;

/**
 * @author yaojy
 */
public interface IUserService {
    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    User getUserInfo(Long userId);

    /**
     * 根据手机号和密码获取用户信息
     *
     * @param password
     * @param phone
     * @return
     */
    User selectByPhoneAndPwd(String phone, String password);

    /**
     * 更新用户最后登录时间
     *
     * @param date
     * @param userId
     */
    void updateLastLoginTime(Long userId, Date date);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    User getUserInfoByUserName(String username);

    /**
     * 注册用户
     *
     * @param user
     */
    void register(User user);

    /**
     * 编辑资料
     *
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 根据账号查询用户信息
     * @param account
     * @return
     */
    User selectUserByAccount(String account);
}
