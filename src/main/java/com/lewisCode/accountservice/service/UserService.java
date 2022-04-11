package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.myUserDetailService;
import com.lewisCode.accountservice.entity.User;
import com.lewisCode.accountservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw  new UsernameNotFoundException("Not found" + email);
        }
        return new myUserDetailService(user.get());
    }

    public User registration(User user){
           return userRepository.save(user);
    }
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
