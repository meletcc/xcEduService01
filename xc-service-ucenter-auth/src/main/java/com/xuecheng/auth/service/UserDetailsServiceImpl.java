package com.xuecheng.auth.service;

import com.xuecheng.auth.client.UserClient;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务调用spring security接口申请令牌，spring security接口会调用
 * UserDetailsServiceImpl，它调用用户中心服务从数据库查询用户信息、权限信息
 * 如果查询不到则返回 NULL，表示不存在；
 * 在 UserDetailsServiceImpl中将正确的密码返回，
 * spring security 会自动去比对输入密码的正确性
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserClient userClient;

    @Autowired
    ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                // 密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 远程调用用户中心根据账号查询用户信息
        XcUserExt userext = userClient.getUserext(username);
        if (userext == null) {
            // 用户不存在：返回空给spring security
            return null;
        }
        // 权限暂时用静态的：帐号、密码
//        XcUserExt userext = new XcUserExt();
//        userext.setUsername("itcast");
//        userext.setPassword(new BCryptPasswordEncoder().encode("123"));
        //userext.setPermissions(new ArrayList<XcMenu>());

        // 取出正确密码（hash值）
        String password = userext.getPassword();
        // 这里暂时使用静态密码
//       String password ="123";

        // 用户权限，这里暂时使用静态数据，最终会从数据库读取
        // 从数据库获取权限
        List<XcMenu> permissions = userext.getPermissions();
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        List<String> user_permission = new ArrayList<>();
        permissions.forEach(item -> user_permission.add(item.getCode()));

        // 使用静态的权限表示用户所拥有的权限
//        user_permission.add("course_get_baseinfo");// 查询课程信息权限
//        user_permission.add("course_pic_list");// 图片查询权限
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");


        UserJwt userDetails = new UserJwt(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(userext.getId());
        userDetails.setUtype(userext.getUtype());// 用户类型
        userDetails.setCompanyId(userext.getCompanyId());// 所属企业
        userDetails.setName(userext.getName());// 用户名称
        userDetails.setUserpic(userext.getUserpic());// 用户头像
       /* UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
//                AuthorityUtils.createAuthorityList("course_get_baseinfo","course_get_list"));


        // 返回该对象给spring security，会自动校验输入的密码和取得的密码是否相同。
        return userDetails;
    }
}
