package com.validationtutorial.task2;

import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @PostMapping("/products")
    public Map<String, String> products(@RequestBody @Validated ProductRequest productRequest) {
        return Map.of("message", "successfully created");
    }
}
