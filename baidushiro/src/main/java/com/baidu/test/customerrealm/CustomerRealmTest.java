package com.baidu.test.customerrealm;

import com.baidu.own.realm.CustomerRealm;
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
 * @ClassName :  CustomerRealmTest
 * @date : 2018/11/21 0021
 * @time : 15:35
 * @createTime 2018-11-21 15:35
 * @version : 2.0
 * @description :
 *
 *     使用时去掉cutomerRealm的MD5加密
 *
 *******************************/


public class CustomerRealmTest {


    @Test
    public void customerRealmTest() {

        try {

            CustomerRealm customerRealm = new CustomerRealm();

            //        1、构建SecurityManager环境
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
            defaultSecurityManager.setRealm(customerRealm);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
            SecurityUtils.setSecurityManager(defaultSecurityManager);
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("张三", "123456");
            subject.login(usernamePasswordToken);

            System.out.println("自定义Realm认证:" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println("账户不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("账户或密码错误");
        } catch (UnauthorizedException e) {
            System.out.println("没有权限");
        }

    }

    @Test
    public void testSimpleAuthorizationInfo() {
        try {

            CustomerRealm customerRealm = new CustomerRealm();

            //        1、构建SecurityManager环境
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
            defaultSecurityManager.setRealm(customerRealm);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
            SecurityUtils.setSecurityManager(defaultSecurityManager);
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("张三", "123456");
            subject.login(usernamePasswordToken);


            subject.checkRole("admin");
            subject.checkPermissions("user:add", "user:delete");

            System.out.println("自定义Realm授权:" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println("账户不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("账户或密码错误");
        } catch (UnauthorizedException e) {
            System.out.println("没有权限");
        }

    }

}
