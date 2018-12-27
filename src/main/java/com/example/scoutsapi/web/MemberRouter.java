package com.example.scoutsapi.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class MemberRouter {
    @Bean
    public RouterFunction<ServerResponse> route(MemberHandler handler) {
        return RouterFunctions
                .route(GET("/members").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/members/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/members").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(DELETE("/members/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
    }
}
