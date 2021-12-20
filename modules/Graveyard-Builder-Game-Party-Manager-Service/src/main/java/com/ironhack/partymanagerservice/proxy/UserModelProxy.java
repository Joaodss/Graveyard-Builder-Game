package com.ironhack.partymanagerservice.proxy;

import com.ironhack.partymanagerservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-model-service", path = "/api/v1/users")
public interface UserModelProxy {

    @PutMapping("/update/{username}")
    UserDTO updateUser(@PathVariable String username, @RequestBody UserDTO user);

}
