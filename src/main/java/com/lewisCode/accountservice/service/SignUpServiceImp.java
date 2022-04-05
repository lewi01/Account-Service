package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.MySignUpDetailService;
import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.repository.SignUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SignUpServiceImp implements UserDetailsService {

    private final SignUpRepository signUpRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<SignUp> signUp = signUpRepository.findByEmail(email);
        if (signUp.isEmpty()){
            throw  new UsernameNotFoundException("Not found" + email);
        }
        return new MySignUpDetailService(signUp.get());
    }

    public SignUp registration(SignUp signUp){
           return signUpRepository.save(signUp);
    }
    public Optional<SignUp> getUserByEmail(String email){
        return signUpRepository.findByEmail(email);
    }
}
