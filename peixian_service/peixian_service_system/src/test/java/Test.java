import io.jsonwebtoken.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;
import java.util.UUID;

/**
 * @author gzj
 * @description
 * @date 2020/11/9
 */

public class Test {

    @org.junit.jupiter.api.Test
    public void Test(){
        String secret = "kwokchikit";
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, secret)
                        .setId(UUID.randomUUID().toString())
                        .setSubject("大家好")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis()+3600000L))
                        .compact();
        System.out.println("token: "+token);
    }

    @org.junit.jupiter.api.Test
    public void test02(){
        String secret = "kwokchikit";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyZDc1N2E4Ni01MjY2LTRkM2MtODkwNy05Njc5YmRjNzY2ZGIiLCJzdWIiOiLlpKflrrblpb0iLCJpYXQiOjE2MDQ5MTgyMTQsImV4cCI6MTYwNDkyMTgxNH0.tydOEPkx8xw9gjlaqSZnBgTkcQWdoexXlNRML7CGjeE";
        System.out.println(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody());

    }

}
