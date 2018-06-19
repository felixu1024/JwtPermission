package top.felixu.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author felixu
 * @Date 2018/6/16
 */
@Configuration
public class JwtProperties {

    @Value("jwt.permission.authorization")
    private String authorization;

    @Value("jwt.permission.key")
    private String key;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
