package codehelper.util;

import codehelper.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    private final String secret;
    private final long expireTime;

    @Autowired
    public JwtUtil(JwtProperties jwtProperties) {
        this.secret = jwtProperties.getSecretKey();
        this.expireTime = jwtProperties.getExpireTime();
    }

    /**
     * 生成包含用户ID和角色的JWT令牌
     * @param userId 用户ID
     * @param roles 角色列表
     * @return JWT令牌字符串
     */
    public String generateToken(Long userId, List<String> roles) {
        // 设置载荷信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("roles", roles);

        // 生成令牌
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 从令牌中提取角色列表
     * @param token JWT令牌
     * @return 角色列表
     */
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (List<String>) claims.get("roles");
    }

    /**
     * 从令牌中提取用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 验证令牌是否有效（包括签名验证和是否过期）
     * @param token JWT令牌
     * @return 有效返回true，否则返回false
     */
    public boolean validateToken(String token) {
        try {
            // 解析令牌（会自动验证签名）
            Claims claims = getClaimsFromToken(token);
            // 检查是否过期
            return !isTokenExpired(claims);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            // 签名错误、令牌格式错误、过期、不支持的格式、参数异常等均视为无效
            return false;
        }
    }

    /**
     * 检查令牌是否过期
     * @param token JWT令牌
     * @return 过期返回true，否则返回false
     */
    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        return isTokenExpired(claims);
    }

    /**
     * 从令牌中解析获取全部载荷信息
     * @param token JWT令牌
     * @return 载荷对象
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查令牌是否过期（内部工具方法）
     * @param claims 载荷对象
     * @return 过期返回true，否则返回false
     */
    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}