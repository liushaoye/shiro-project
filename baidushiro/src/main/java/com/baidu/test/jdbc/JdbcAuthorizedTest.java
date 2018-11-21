package com.baidu.test.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/******************************
 * @author : liuyang
 * <p>ProjectName:shiro-project  </p>
 * @ClassName :  IniRealmTest
 * @date : 2018/11/21 0021
 * @time : 9:48
 * @createTime 2018-11-21 9:48
 * @version : 2.0
 * @description :
 *
 *    Jdbc认证
 *
 *******************************/

@Slf4j
public class JdbcAuthorizedTest {


    DruidDataSource druidDataSource = new DruidDataSource();


    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/shiro");

        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");

    }


    @Test
    public void authenticationTest() {

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

//        1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("tom", "123456");
            subject.login(usernamePasswordToken);

            subject.checkRole("admin");
            subject.checkPermission("user:select");
            System.out.println("认证:" + subject.isAuthenticated());


        } catch (UnknownAccountException e) {
            System.out.println("账户不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("账户或密码错误");
        } catch (UnauthorizedException e) {
            System.out.println("没有权限");
        }


    }
}
