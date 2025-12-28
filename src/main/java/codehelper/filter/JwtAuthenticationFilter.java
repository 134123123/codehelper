package codehelper.filter;

import codehelper.config.JwtProperties;
import codehelper.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(jwtProperties.getTokenHeader());

        // 加上 try-catch 块，防止旧 Token 导致登录接口报错
        try {
            if (authHeader != null && authHeader.startsWith(jwtProperties.getTokenPrefix())) {
                String token = authHeader.substring(jwtProperties.getTokenPrefix().length());

                // 解析 Token (如果 Token 过期或非法，这里会抛出异常)
                if (!jwtUtil.isTokenExpired(token)) {
                    Long userId = jwtUtil.getUserIdFromToken(token);
                    List<String> roles = jwtUtil.getRolesFromToken(token);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userId, null, authorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            // 【核心逻辑】如果 Token 解析失败（过期、格式错等），不要抛出异常！
            // 只是清空认证信息，然后让请求继续走。
            SecurityContextHolder.clearContext();
            // 打印日志：System.out.println("Invalid Token: " + e.getMessage());
        }

        // 无论 Token 是否有效，都必须放行，让 Spring Security 的配置去决定是否允许访问
        filterChain.doFilter(request, response);
    }
}