package top.felixu.utils;

import com.sun.xml.internal.bind.v2.TODO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * JWT处理类，用来创建token，解析token
 *
 * @Author felixu
 * @Date 2018/6/14
 */
public class JwtUtils {

    /**
     * create token with userInfo(userId or userName)
     *
     * @param userInfo
     * @param key
     * @param expireDate
     * @return
     */
    public static String createToken(String userInfo, String key, Date expireDate) {
        return Jwts.builder()
                .setSubject(userInfo)
                .signWith(SignatureAlgorithm.HS512, key(key))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    /**
     * parse token
     *
     * @param token
     * @param key
     * @return
     */
    public static Claims parseToken(String token, String key) {
        // FIXME: 2018/6/14 token is null
        return Jwts.parser()
                .setSigningKey(key(key))
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key key(String key) {
        return new SecretKeySpec(key.getBytes(), "AES");
    }

    public static void main(String[] args) {
        String token = createToken("felixu", "aaaa", null);
        System.out.println(token);
        Claims claims = parseToken(token, "aaaa");
        System.out.println(claims.getSubject());
    }
}
