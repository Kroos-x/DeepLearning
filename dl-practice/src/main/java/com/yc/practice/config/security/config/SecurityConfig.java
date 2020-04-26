package com.yc.practice.config.security.config;

import com.yc.common.propertie.SecurityProperties;
import com.yc.core.system.mapper.SysUserMapper;
import com.yc.practice.config.security.filter.JwtAuthenticationTokenFilter;
import com.yc.practice.config.security.filter.SessionInformationExpiredStrategySelf;
import com.yc.practice.config.security.filter.UsernamePasswordAuthenticationFilterSelf;
import com.yc.practice.config.security.service.TokenService;
import com.yc.practice.config.security.service.impl.UserDetailsServiceImpl;
import com.yc.practice.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * 功能描述：Security 核心配置类
 * <p>版权所有：</p>
 * 未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2020-03-20
 * @Version: 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final SecurityProperties securityProperties;
    private final SysUserMapper sysUserMapper;
    private final TokenService tokenService;
    private final RedisTemplate<String,String> redisTemplate;
    private final UserDetailsServiceImpl userDetailsService;
    private final SysLogService sysLogService;

    @Autowired
    public SecurityConfig (JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                           SecurityProperties securityProperties, RedisTemplate<String,String> redisTemplate,
                           SysUserMapper sysUserMapper,TokenService tokenService,
                           UserDetailsServiceImpl userDetailsService, SysLogService sysLogService){
        this.tokenService = tokenService;
        this.sysLogService = sysLogService;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.redisTemplate = redisTemplate;
        this.sysUserMapper = sysUserMapper;
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 配置需要拦截的url路径、jwt过滤器及异常后的处理器
     * @param httpSecurity  httpSecurity
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(securityProperties.getExcludes()).permitAll()
                .anyRequest().authenticated()
                // 登出
                .and().logout()
                .permitAll()
                .invalidateHttpSession(true)
                // 会话管理
                .and().sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(new SessionInformationExpiredStrategySelf());
        httpSecurity.addFilterAt(new UsernamePasswordAuthenticationFilterSelf(authenticationManager(),
                                sysUserMapper, tokenService,redisTemplate,sysLogService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 开放静态资源
     * @param web web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers( "/css/**",
                "/js/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/v2/**",
                "/swagger-resources/**");
    }

}
