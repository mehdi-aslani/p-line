package ir.peeco.pline.configuration;

import java.io.IOException;
import java.net.URI;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class FeignClientInterceptor implements Filter {

  @Value("${pline.jwtSecret}")
  private String jwtSecret;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String methode = httpRequest.getMethod();
    if (methode.equalsIgnoreCase("OPTIONS")) {
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
      chain.doFilter(httpRequest, response);
      return;
    }
    try {
      URI newUri = new URI(httpRequest.getRequestURL().toString());
      if (newUri.getPath().equalsIgnoreCase("/users/login")) {
        chain.doFilter(httpRequest, response);
      }
    } catch (Exception ex) {
      ((HttpServletResponse) response).setStatus(500);
    }

    String token = httpRequest.getHeader("Authorization");

    if (!Jwts.parser().setSigningKey(jwtSecret).isSigned(token)) {
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    } else {
      // .parseClaimsJws(token).getBody().;
      chain.doFilter(httpRequest, response);
    }

  }

}
