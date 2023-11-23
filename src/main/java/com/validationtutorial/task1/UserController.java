package com.validationtutorial.task1;

import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/users")
    public Map<String, String> users(@RequestBody @Validated UserRequest userRequest) {
        return Map.of("message", "successfully created");
    }
}
