package com.darkcom.decoration.service;

import com.darkcom.decoration.model.Role;

import java.util.List;

public interface IRoleService {
    /**
     * 根据用户Id查询用户所拥有的角色
     *
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(Long userId);
}
