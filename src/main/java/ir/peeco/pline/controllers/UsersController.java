package ir.peeco.pline.controllers;

import ir.peeco.pline.models.PlineUser;
import ir.peeco.pline.repositories.PlineUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@CrossOrigin(value = "*", maxAge = 3600)
public class UsersController {

    @Value("${pline.jwtExpirationSeconds}")
    private Long jwtExpirationSeconds;

    @Value("${pline.jwtSecret}")
    private String jwtSecret;

    private PlineUsersRepository plineUsersRepository;

    @Autowired
    public void setPlineUsersRepository(PlineUsersRepository plineUsersRepository) {
        this.plineUsersRepository = plineUsersRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest req, @RequestBody Map<String, String> form) {

        Map<String, Object> result = new HashMap<>();
        PlineUser user = plineUsersRepository.findByUsername(form.get("username"));
        if (user == null || user.getPassword().equals(form.get("password")) == false) {
            result.put("token", null);
            result.put("has_error", true);
            result.put("error_messages", new String[] { "username or password is incorrect" });
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(result);
        }

        String token = Jwts.builder()
                .setSubject(form.get("username"))
                .setIssuedAt(new Date())
                .setId("1")
                .setExpiration(new Date((new Date()).getTime() + (jwtExpirationSeconds * 1000)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        user.setToken(token);
        plineUsersRepository.save(user);
        result.put("token", token);
        result.put("username", form.get("username"));
        result.put("uid", user.getId());
        result.put("isAuth", true);
        result.put("has_error", false);
        return ResponseEntity.ok(result);
    }
}
