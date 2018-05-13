package com.darkcom.decoration.shiro;

import com.alibaba.fastjson.JSON;
import com.darkcom.decoration.model.Permission;
import com.darkcom.decoration.model.Role;
import com.darkcom.decoration.model.User;
import com.darkcom.decoration.service.IPermissionService;
import com.darkcom.decoration.service.IRoleService;
import com.darkcom.decoration.service.IUserService;
import com.darkcom.decoration.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yaojy
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
        /*User token = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = token.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
        List<Role> roleList = roleService.getRolesByUserId(userId);
        Set<String> roleSet = new HashSet<String>();
        for (Role role : roleList) {
            roleSet.add(role.getType());
        }
        info.setRoles(roleSet);
        //根据用户ID查询权限（permission），放入到Authorization里。
        List<Permission> permissionList = permissionService.getPermissionsByUserId(userId);
        Set<String> permissionSet = new HashSet<String>();
        for (Permission permission : permissionList) {
            permissionSet.add(permission.getName());
        }
        info.setStringPermissions(permissionSet);*/
        String username = JWTUtil.getUsername(principals.toString());
        User user = userService.getUserInfoByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.getRolesByUserId(user.getUserId());
        Set<String> roleSet = new HashSet<String>();
        for (Role role : roleList) {
            roleSet.add(role.getType());
        }
        simpleAuthorizationInfo.setRoles(roleSet);
        //根据用户ID查询权限（permission），放入到Authorization里。
        List<Permission> permissionList = permissionService.getPermissionsByUserId(user.getUserId());
        Set<String> permissionSet = new HashSet<String>();
        for (Permission permission : permissionList) {
            permissionSet.add(permission.getUrl());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        simpleAuthorizationInfo.addStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
        //return info;
    }

    /**
     * 认证
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("身份认证方法：MyShiroRealm.doGetAuthenticationInfo(),token={}", JSON.toJSONString(authcToken));
        /*UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 从数据库获取对应用户名密码的用户
        User user = userService.selectByPhoneAndPwd(token.getUsername(), String.valueOf(token.getPassword()));

        if (null == user) {
            throw new BusinessException("帐号或密码不正确！");
        } else if (user.getStatus() == 0) {
            throw new DisabledAccountException("帐号已经禁止登录！");
        } else {
            //更新登录时间 last login time
            userService.updateLastLoginTime(user.getUserId(),new Date());
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());*/

        String token = (String) authcToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        User user = userService.getUserInfoByUserName(username);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (!JWTUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, this.getClass().getName());
    }
}
