package ir.peeco.pline.configuration;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import ir.peeco.pline.models.PlineUser;
import ir.peeco.pline.repositories.PlineUsersRepository;

@Component
public class jwtAuthorizationCheck implements Filter {

  @Value("${pline.jwtSecret}")
  private String jwtSecret;

  private PlineUsersRepository usersRepository;

  @Autowired
  public void setUsersRepository(PlineUsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

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
        return;
      }
    } catch (Exception ex) {
      ((HttpServletResponse) response).setStatus(500);
    }

    String token = httpRequest.getHeader("Authorization");
    if (token.startsWith("Bearer")) {
      token = token.substring(7);
    } else {
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    var jwtParser = Jwts.parser().setSigningKey(jwtSecret);
    if (jwtParser.isSigned(token)) {
      // Long id = Long.parseLong(jwtParser.parseClaimsJws(token).getBody().getId());
      HttpSession session = httpRequest.getSession();
      Optional<PlineUser> user = usersRepository.findByToken(token);
      if (user.isPresent()) {
        session.setAttribute("user", user.get());
        chain.doFilter(httpRequest, response);
      } else {
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } else {
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

  }

}
