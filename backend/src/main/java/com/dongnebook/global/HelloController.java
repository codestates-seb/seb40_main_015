package com.dongnebook.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public ResponseEntity<LocalDateTime> getHello(){
        return new ResponseEntity<>(LocalDateTime.now(), HttpStatus.OK);
    }

}
