package com.baidu.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;


/******************************
 * @author : liuyang
 * <p>ProjectName:shiro-project  </p>
 * @ClassName :  AuthenticationTest
 * @date : 2018/11/20 0020
 * @time : 22:09
 * @createTime 2018-11-20 22:09
 * @version : 2.0
 * @description :
 *
 *     Shiro授权
 *
 *******************************/

@Slf4j
public class AuthorizedTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    SimpleAccountRealm simpleAccountRealm2 = new SimpleAccountRealm();

    @Before
    public void addUser() {
        //提前添加用户在环境中
        simpleAccountRealm.addAccount("zhangsan", "123456","admin");
        //同时多个用户授权
        simpleAccountRealm2.addAccount("zhangsan", "123456","admin","user");
    }

    @Test
    public void AuthorizedTest() {

//        1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 提前的参数加入环境中
        defaultSecurityManager.setRealm(simpleAccountRealm);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan", "123456");
        subject.login(usernamePasswordToken);


        System.out.println(subject.isAuthenticated());

        subject.checkRole("admin");
    }


    @Test
    public void authorized2Test() {

//        1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 提前的参数加入环境中
        defaultSecurityManager.setRealm(simpleAccountRealm2);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan", "123456");
        subject.login(usernamePasswordToken);


        System.out.println(subject.isAuthenticated());

        subject.checkRoles("admin","user");
    }
}
