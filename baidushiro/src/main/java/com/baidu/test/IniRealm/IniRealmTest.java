package com.baidu.test.IniRealm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
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
 *          认证
 *
 *******************************/

@Slf4j
public class IniRealmTest {


    @Test
    public void authenticationTest() {

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

//        1、构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("tom", "123456");
            subject.login(usernamePasswordToken);
            System.out.println("已经认证:" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println("账户不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("账户或密码错误");
        }


    }
}
