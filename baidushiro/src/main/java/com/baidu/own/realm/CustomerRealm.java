package com.baidu.own.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/******************************
 * @author : liuyang
 * <p>ProjectName:shiro-project  </p>
 * @ClassName :  CustomerRealm
 * @date : 2018/11/21 0021
 * @time : 15:20
 * @createTime 2018-11-21 15:20
 * @version : 2.0
 * @description :
 *
 *    自定义realm
 *
 *******************************/

public class CustomerRealm extends AuthorizingRealm {


    Map<String, String> userMap = new HashMap<String, String>(16);

    {
        //此处进行了加密,如果不需要讲new Md5Hash去掉
        userMap.put("张三", new Md5Hash("123456").toString());
        super.setName("customerRealm");
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 1、从主体传过来的认证信息中，获得用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();

        // 2、从数据库或缓存中获取角色数据
        Set<String> roles = getRolesByUserName(userName);
        // 3、从数据库或缓存中获取权限数据
        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {

        Set<String> sets = new HashSet<String>();

        sets.add("user:add");
        sets.add("user:delete");

        return sets;
    }

    private Set<String> getRolesByUserName(String userName) {

        Set<String> sets = new HashSet<String>();

        sets.add("admin");
        sets.add("user");

        return sets;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 1、从主体传过来的认证信息中，获得用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 2、通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);

        if (password == null) {
            return null;
        }


        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo
                ("张三", password, "customerRealm");

        return simpleAuthenticationInfo;
    }

    /**
     * 模拟数据库凭证
     *
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
