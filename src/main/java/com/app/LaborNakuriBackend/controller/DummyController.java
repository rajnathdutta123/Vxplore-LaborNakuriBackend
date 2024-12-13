package com.app.LaborNakuriBackend.controller;

import com.app.LaborNakuriBackend.config.MapAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DummyController {

    private static final Logger logger = LogManager.getLogger(DummyController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> test() {
        logger.info("Info level log example");
        logger.debug("Debug level log example");
        logger.error("Error level log example");
        return ResponseEntity.ok("hello");
    }
}
