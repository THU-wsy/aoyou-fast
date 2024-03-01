package com.thuwsy.aoyou.support.config.security;

import com.alibaba.fastjson2.JSON;
import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.exception.ServiceException;
import com.thuwsy.aoyou.common.response.CommonResult;
import com.thuwsy.aoyou.common.utils.JwtUtil;
import com.thuwsy.aoyou.support.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * ClassName: SecurityConfig
 * Package: com.thuwsy.aoyou.support.config.security
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 19:47
 * @Version 1.0
 */
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private SysUserDetailsService sysUserDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtUtil jwtUtil;

    @Bean // 密码加密器（对密码进行加密，提高安全性）
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注册AuthenticationManager，将它与UserDetailsService、PasswordEncoder关联
     */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        // 它是AuthenticationProvider的一个常用实现类
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 关联使用的UserDetailsService
        provider.setUserDetailsService(sysUserDetailsService);
        // 关联使用的密码加密器
        provider.setPasswordEncoder(passwordEncoder);
        // 将AuthenticationProvider放入AuthenticationManager中
        return new ProviderManager(provider);
    }

    // 配置跨域
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    /**
     * 配置Security过滤器链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. 关闭CSRF防护
        http.csrf(conf -> conf.disable());

        // 2. 配置跨域
        http.cors(cors -> cors.configurationSource(configurationSource()));

        // 3. 设置需要认证的url
        http.authorizeHttpRequests(conf -> conf
                .requestMatchers("/auth/sys").permitAll() // 登录相关的url无需认证
                .anyRequest().authenticated() // 其余请求都需要认证(登录)
        );

        // 4. 退出登录相关配置
        http.logout(conf -> conf
                .logoutUrl("/auth/logout") // 退出登录提交post请求的url
                .logoutSuccessHandler(this::onLogoutSuccess) // 退出登录成功后的处理方法
        );

        // 5. 认证或授权失败时的处理方式
        http.exceptionHandling(conf -> conf
                // 用户未登录时的处理方法
                .authenticationEntryPoint(this::onUnauthorized)
                // 用户权限不足访问时的处理方法
                .accessDeniedHandler(this::onAccessDeny)
        );

        // 6. 使用基于token的校验，就需要将session改为无状态
        http.sessionManagement(conf -> conf
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 7. 将我们自定义的JwtAuthenticationFilter加入过滤器链
        // 注意，检验JWT的过滤器一定要在登录认证的过滤器之前
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 退出登录成功后的处理方法
    private void onLogoutSuccess(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Authentication authentication)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        // 1. 获取请求头中的token内容
        String headerToken = request.getHeader("Authorization");

        // 2. 删除Redis中的用户信息
        if (jwtUtil.deleteLoginUser(headerToken)) {
            writer.write(JSON.toJSONString(CommonResult.success()));
        } else {
            CommonResult result = CommonResult.error(HttpStatus.UNAUTHORIZED, "用户未登录");
            response.getWriter().write(JSON.toJSONString(result));
        }
    }

    // 用户未登录时的处理方法
    private void onUnauthorized(HttpServletRequest request,
                                HttpServletResponse response,
                                AuthenticationException exception)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        CommonResult result = CommonResult.error(HttpStatus.UNAUTHORIZED, "用户未登录");
        response.getWriter().write(JSON.toJSONString(result));
    }

    // 用户权限不足访问时的处理方法
    private void onAccessDeny(HttpServletRequest request,
                              HttpServletResponse response,
                              AccessDeniedException exception)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        CommonResult result = CommonResult.error(HttpStatus.FORBIDDEN, "权限不足");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
