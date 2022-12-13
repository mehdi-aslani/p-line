package ir.peeco.pline.controllers;

import ir.peeco.pline.models.PlineUser;
import ir.peeco.pline.pline.ApiResult;
import ir.peeco.pline.repositories.PlineUsersRepository;
import ir.peeco.pline.tools.GlobalsTools;

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

        ApiResult result = new ApiResult();
        PlineUser user = plineUsersRepository.findByUsername(form.get("username"));
        if (user == null || user.getPassword().equals(GlobalsTools.MD5(form.get("password"))) == false) {
            result.setHasError(true);
            result.addMessage("username or password is incorrect");
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
        Map<String, Object> resultLogin = new HashMap<>();
        resultLogin.put("token", token);
        resultLogin.put("username", form.get("username"));
        resultLogin.put("fullname", user.getFullname());
        resultLogin.put("uid", user.getId().toString());
        resultLogin.put("isAuth", true);
        resultLogin.put("hasError", false);
        return ResponseEntity.ok(resultLogin);
    }
}
