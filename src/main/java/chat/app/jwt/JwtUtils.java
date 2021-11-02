package chat.app.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;

import chat.app.errors.unauthorized.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
  private static final int refreshDuration = 60 * 60 * 24 * 7;

  private static final String getSecret(Boolean refresh) {
    String secretEnv = System.getenv(refresh ? "jwt_secret_refresh" : "jwt_secret");
    return secretEnv == null ? refresh ? "secret_refresh" : "secret" : secretEnv;
  }

  public static final Jwt generateToken(String id, Boolean refresh) {
    Long now = System.currentTimeMillis();
    String jwt = Jwts.builder().setSubject(id).setIssuedAt(new Date(now))
        .setExpiration(refresh ? new Date(now + refreshDuration * 1000) : new Date(now + 300000))
        .signWith(SignatureAlgorithm.HS512, getSecret(refresh)).compact();
    return new Jwt(jwt);
  }

  public static final String getIdFromToken(String token, Boolean refresh) {
    try {
      return Jwts.parser().setSigningKey(getSecret(refresh)).parseClaimsJws(token).getBody().getSubject();
    } catch (Exception e) {
      throw new UnauthorizedException("Invalid token");
    }
  }

  public static final Cookie createRefreshToken(String username) {
    Cookie cookie = new Cookie("refresh_token", generateToken(username, true).getToken());
    cookie.setMaxAge(refreshDuration); // expires in 7 days
    cookie.setHttpOnly(true);
    return cookie;
  }
}
