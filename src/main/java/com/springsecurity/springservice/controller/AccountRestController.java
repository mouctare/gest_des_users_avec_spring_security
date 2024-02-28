package com.springsecurity.springservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springsecurity.springservice.entity.AppRole;
import com.springsecurity.springservice.entity.AppUser;
import com.springsecurity.springservice.service.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController

public class AccountRestController {
    @Autowired
    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }

    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('PATRON')")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);

    }

    @PostMapping(path = "/roles")
   public AppRole appRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);

    }


    @PostMapping(path = "/addRoleToUser")
   public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());

    }
    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
      String authToken = request.getHeader("Authorization");
      if(authToken !=null && authToken.startsWith("Bearer ")){
          try {
              String jwt = authToken.substring(7);
              Algorithm algorithm = Algorithm.HMAC256("mySecret1234");
              JWTVerifier jwtVerifier = JWT.require(algorithm).build();
              DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
              String username = decodedJWT.getSubject();
              AppUser appUser = accountService.loadUserByUsername(username);


          }catch (Exception e){
              response.setHeader("error-message", e.getMessage());
             // response.sendError(HttpServletResponse.SC_FORBIDDEN);
          }
      }
    }
}
    @Data
    class RoleUserForm{
        private String username;
        private String roleName;
    }


    // http://localhost:8080/login -> se connecter à l'api

