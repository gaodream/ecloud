package com.dream.common.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


@Component
public class OAuth2Realm extends AuthorizingRealm {
	
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /**
         * 根据不同用户用户ID到仓库中查询相应的权限
         */
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        /**
         * 对用户进行认证：
         *  1.token是否失效
         *  2.账户是否存在
         *  3.账户是都锁定
         * 
         */

        //SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return null;
    }
}
