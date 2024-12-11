package com.app.LaborNakuriBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/hello")
    public ResponseEntity<String> test()
    {
        return ResponseEntity.ok("hello");
    }
}
