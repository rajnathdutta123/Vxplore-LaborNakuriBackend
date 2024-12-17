package com.app.LaborNakuriBackend.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DummyController {

    private static final Logger logger = LogManager.getLogger(DummyController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> test() {
        logger.info("Info level log example");
        logger.debug("Debug level log example");
        logger.error("Error level log example");
        return ResponseEntity.ok("hello");
    }
    @GetMapping("/hi")
    public ResponseEntity<String> newTest() {
        return ResponseEntity.ok("Hiiiiiii");
    }
    @GetMapping("/dummy")
    public ResponseEntity<String> dummy() {
        return ResponseEntity.ok("Dummy API");
    }


    @GetMapping("/test")
    public String testEndpoint() {
        // Simulate some work by adding a delay
        try {
            Thread.sleep(15000); // Simulate some delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "Hello from the API!";
    }
}
