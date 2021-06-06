package com.knits.smartfactory.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knits.smartfactory.security.jwt.JWTFilter;
import com.knits.smartfactory.security.jwt.TokenProvider;
import com.knits.smartfactory.web.rest.vm.LoginVM;
import javax.validation.Valid;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        String request = new String(
            new StringBuilder()
                .append("{\n" + "  \"password\": \"")
                .append(loginVM.getPassword())
                .append("\",\n" + "  \"rememberMe\": true,\n" + "  \"username\": \"")
                .append(loginVM.getUsername())
                .append("\"" + "\n}")
        );

        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("https://dmknitscore.herokuapp.com:443/api/authenticate-application");
        //        WebTarget target = client.target("http://localhost:9000/api/authenticate-application");

        Response response = target.request().post(Entity.entity(request, "application/json"));

        JSONObject jsonObject = new JSONObject(response);

        JWTToken jwtToken = new JWTToken(jsonObject.getString("token"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwtToken);

        //        String s = jsonObject.getJSONObject("metadata").getJSONArray("Authorization").getString(0);
        //        s = s.substring(7);
        //        System.err.println(s);

        return new ResponseEntity<>(jwtToken, httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
