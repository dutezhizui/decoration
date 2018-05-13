package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.PermissionMapper;
import com.darkcom.decoration.model.Permission;
import com.darkcom.decoration.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaojy
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Permission> getPermissionsByUserId(Long userId) {
        return permissionMapper.getPermissionsByUserId(userId);
    }
}
