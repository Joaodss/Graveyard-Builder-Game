package com.ironhack.authservice.proxy;


import com.ironhack.authservice.dto.UserAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-model-service", path = "/api/v1/users")
public interface UserModelProxy {

    @GetMapping("/auth/{username}")
    UserAuthDTO getUserAuth(@PathVariable String username);

}
