package com.example.logic.ann.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class MyFunctionalController {
    @Bean
    RouterFunction<ServerResponse> routes() {
        return
            route(GET("/employee"), req -> ok().body(Flux.just("a", "b"), String.class))
                .and(route(GET("/employee/{id}"), req -> ok().body(Mono.just("a"), String.class)));
    }
}
