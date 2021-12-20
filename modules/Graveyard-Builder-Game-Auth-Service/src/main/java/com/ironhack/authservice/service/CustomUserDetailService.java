package com.ironhack.authservice.service;

import com.ironhack.authservice.dto.UserAuthDTO;
import com.ironhack.authservice.proxy.UserModelProxy;
import com.ironhack.authservice.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserModelProxy userModelProxy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthDTO storedUser = userModelProxy.getUserAuth(username);
        log.info("Loading user with username: {}", username);
        System.out.println("Loading user with username: " + username);
        if (storedUser == null) throw new UsernameNotFoundException("User not found.");
        return new CustomUserDetails(storedUser);
    }

}
