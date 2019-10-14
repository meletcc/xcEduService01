package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * mysql是继承springData的jpa
 */
public interface XcUserRepository extends JpaRepository<XcUser, String> {
    // 根据账号查询用户基本信息
    XcUser findByUsername(String username);
}
