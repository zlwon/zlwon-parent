package com.zlwon.api.shrio;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.entity.User;
import com.zlwon.server.service.UserService;
import com.zlwon.utils.JWTUtil;
import com.zlwon.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userServiceImpl;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("登录验证后进行权限认证....");
        String username = JWTUtil.getUsername(principals.toString());
        User user = userServiceImpl.findUserByName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        Role role = user.getRole();
//        if(role != null){
//            simpleAuthorizationInfo.addRole(user.getRole().getRoleCode());
//            for(Permission p : role.getPermissions()){
//                if(p!=null) {
//                    simpleAuthorizationInfo.addStringPermission(p.getPermCode());
//                }
//            }
//        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new CommonException(StatusCode.PERMISSION_ERROR);// "token invalid");
        }

        User userBean = userServiceImpl.findUserByName(username);
        if (userBean == null) {
            throw new CommonException(StatusCode.USER_NOT_EXIST);
        }

        if (!JWTUtil.verify(token, username, MD5Utils.encode(userBean.getPassword()))) {
            throw new CommonException(StatusCode.PASSWORD_INVALID);
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
