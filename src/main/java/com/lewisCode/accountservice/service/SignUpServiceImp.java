package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.MySignUpDetailService;
import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.errors.UserExistException;
import com.lewisCode.accountservice.repository.SignUpRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignUpServiceImp implements UserDetailsService {
    private final SignUpRepository signUpRepository;

    public SignUpServiceImp(SignUpRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<SignUp> signUp = signUpRepository.findByEmail(email);
        if (signUp.isEmpty()){
            throw  new UsernameNotFoundException("Not found" + email);
        }
        return new MySignUpDetailService(signUp.get());
    }

    public SignUp registration(SignUp signUp){
            Optional<SignUp> user1 = signUpRepository.findByEmail(signUp.getEmail());
            if (user1.isPresent()){
                throw new UserExistException();
            }

           return signUpRepository.save(signUp);
    }
    public List<SignUp> showAllUsers(){
        return signUpRepository.findAll();
    }
    public Optional<SignUp> getUserByEmail(String email){
        return signUpRepository.findByEmail(email);
    }
}
