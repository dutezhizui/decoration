package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.RoleMapper;
import com.darkcom.decoration.model.Role;
import com.darkcom.decoration.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yaojy
 */
@Service
public class RoleServiceImpl implements IRoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional
    public List<Role> getRolesByUserId(Long userId) {
        return this.roleMapper.getRolesByUserId(userId);
    }
}
