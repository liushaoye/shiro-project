package com.baidu.test.authentication;

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
 *     Shiro环境搭建
 *
 *******************************/

@Slf4j
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        //提前添加用户在环境中
        simpleAccountRealm.addAccount("zhangsan", "123456");
    }

    @Test
    public void authenticationTest() {

//        1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 提前的参数加入环境中
        defaultSecurityManager.setRealm(simpleAccountRealm);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan", "123456");
        subject.login(usernamePasswordToken);

        boolean uthenticated = subject.isAuthenticated();

        log.info("是否认证通过");
        if (uthenticated) {
            log.info("\n");
            log.info("通过结果:" + "是");
        } else {
            log.info("\n");
            log.info("通过结果:" + "否");
        }


        subject.logout();

        log.info("是否认证通过"+subject.isAuthenticated());
        System.out.println(subject.isAuthenticated());
    }
}
