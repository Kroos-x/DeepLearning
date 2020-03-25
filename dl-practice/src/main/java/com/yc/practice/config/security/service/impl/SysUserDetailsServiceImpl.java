package com.yc.practice.config.security.service.impl;

import com.yc.core.system.mapper.SysUserMapper;
import com.yc.core.system.model.vo.CurrUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 功能描述：SpringSecurity定义的核心接口，用于根据用户名获取用户信息
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2020-03-20
 * @Version: 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    @Autowired
    public SysUserDetailsServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        System.out.println("========================= 调用嘛 =======================");
        System.out.println("========================= 调用嘛 =======================");
        System.out.println("========================= 调用嘛 =======================");
        System.out.println("========================= 调用嘛 =======================");
        CurrUserVO userVO = sysUserMapper.loginByName(loginName);
        // if (userVO == null) {
        //     throw new RunningException("用户不存在");
        // } else if (StringUtils.isBlank(userVO.getRoleId())) {
        //     throw new ErrorException(HaitaoError.SysUserNoLoginPurview);
        // } else if (StringUtils.equals(userVo.getState(), ZhyqConsts.PublicState.DISABLE)) {
        //     throw new ErrorException(Error.UserDisabled);
        // } else if (StringUtils.equals(userVo.getState(), ZhyqConsts.PublicState.ENABLE)) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(userVO.getSysUserId()));
            return new User(userVO.getLoginName(), userVO.getPassword(), authorities);
        // } else {
        //     throw new ErrorException(Error.UserError);
        // }
    }
}
