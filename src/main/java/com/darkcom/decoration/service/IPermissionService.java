package com.darkcom.decoration.service;

import com.darkcom.decoration.model.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission> getPermissionsByUserId(Long userId);
}
