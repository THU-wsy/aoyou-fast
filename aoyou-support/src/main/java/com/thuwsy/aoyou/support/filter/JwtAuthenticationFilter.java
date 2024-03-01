package com.thuwsy.aoyou.support.filter;

import com.thuwsy.aoyou.common.domain.vo.LoginUserVO;
import com.thuwsy.aoyou.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ClassName: JwtAuthenticationFilter
 * Package: com.thuwsy.aoyou.support.filter
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 14:26
 * @Version 1.0
 */
// 继承OncePerRequestFilter表示每次请求过滤一次
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 首先从请求头中取出JWT并解析，获取用户信息
        String headerToken = request.getHeader("Authorization");
        LoginUserVO user = jwtUtil.getLoginUser(headerToken);

        // 2. 如果jwt合法，则将用户信息保存到SecurityContext，表示已完成验证
        if (user != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 3. 最后放行，执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}
