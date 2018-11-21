package com.baidu.test.salt;

import com.baidu.own.realm.CustomerRealm;
import com.baidu.own.realm.SaltRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
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
 *     加盐加密
 *
 *******************************/


public class SaltRealmTest {


    @Test
    public void saltRealmTest() {

        try {

            SaltRealm customerRealm = new SaltRealm();

            //        1、构建SecurityManager环境
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
            defaultSecurityManager.setRealm(customerRealm);


            HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

            // 设置加密方式,采用md5
            hashedCredentialsMatcher.setHashAlgorithmName("MD5");

            // 加密次数
            hashedCredentialsMatcher.setHashIterations(1);

            //设置如realm
            customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);


//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
            SecurityUtils.setSecurityManager(defaultSecurityManager);
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("张三", "123456");
            subject.login(usernamePasswordToken);

            System.out.println("自定义加盐Realm认证:" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println("账户不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("错误的凭证信息,账户或密码错误");
        } catch (UnauthorizedException e) {
            System.out.println("没有权限");
        }

    }


}
