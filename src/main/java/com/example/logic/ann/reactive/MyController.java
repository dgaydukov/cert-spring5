package com.example.logic.ann.reactive;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/person")
public class MyController {
    @GetMapping("/a")
    public String handleGetA(){
        return "hello";
    }

    @RequestMapping(value = "/b", produces = MediaType.TEXT_EVENT_STREAM_VALUE, method = RequestMethod.GET)
    public Flux<String> handleGetB(){
        return Flux.just("a","b","c").delayElements(Duration.ofSeconds(1));
    }
}
