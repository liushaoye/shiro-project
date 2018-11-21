package com.baidu.test.ownjdbc;

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
 *    自定义Jdbc
 *
 *******************************/

@Slf4j
public class OwnJdbcAuthorizedTest {


    static DruidDataSource druidDataSource = new DruidDataSource();


    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/shiro");

        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");

    }

    @Test
    public void authenticationTest() {
        int count = 1;
        if (count == 0) {
            try {
                String sql = "select password from own_jdbc where user_name=?";
                Method method = new Method().invoke(sql);
                Subject subject = method.getSubject();

                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("张三", "123456");
                subject.login(usernamePasswordToken);
                System.out.println("姓名认证:" + subject.isAuthenticated());
            } catch (UnknownAccountException e) {
                System.out.println("账户不存在");
            } catch (IncorrectCredentialsException e) {
                System.out.println("账户或密码错误");
            } catch (UnauthorizedException e) {
                System.out.println("没有权限");
            }
        } else {
            try {
                String sql2 = "select password from own_jdbc where phone=?";
                Method method2 = new Method().invoke(sql2);
                Subject subject2 = method2.getSubject();

                UsernamePasswordToken usernamePasswordToken2 = new UsernamePasswordToken("1991001", "123456");
                subject2.login(usernamePasswordToken2);

                System.out.println("手机号认证:" + subject2.isAuthenticated());
            } catch (UnknownAccountException e) {
                System.out.println("**************************");
                System.out.println("账户不存在");
            } catch (IncorrectCredentialsException e) {
                System.out.println("账户或密码错误");
            } catch (UnauthorizedException e) {
                System.out.println("没有权限");
            }
        }
    }

    public class Method {

        private JdbcRealm jdbcRealm1;
        private Subject subject;

        public JdbcRealm getJdbcRealm1() {
            return jdbcRealm1;
        }

        public Subject getSubject() {
            return subject;
        }

        public Method invoke(String sql) {
            jdbcRealm1 = new JdbcRealm();
            jdbcRealm1.setDataSource(druidDataSource);
            jdbcRealm1.setPermissionsLookupEnabled(true);

            jdbcRealm1.setAuthenticationQuery(sql);

//        1、构建SecurityManager环境
            DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
            defaultSecurityManager.setRealm(jdbcRealm1);

//          2、主体提交认证请求,主体授权,交给了SecurityManager授权,SecurityManager使用Authorizer进行授权,通过Realm获取缓存中的数据
            SecurityUtils.setSecurityManager(defaultSecurityManager);
            subject = SecurityUtils.getSubject();
            return this;
        }
    }

}
