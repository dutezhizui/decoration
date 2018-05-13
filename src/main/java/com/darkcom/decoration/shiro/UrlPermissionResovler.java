package com.darkcom.decoration.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @author yaojy
 */
public class UrlPermissionResovler implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("/")) {
            return new UrlPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }

}