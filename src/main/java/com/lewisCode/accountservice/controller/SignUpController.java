package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.DTOs.ChangePassword;
import com.lewisCode.accountservice.DTOs.GetUser;
import com.lewisCode.accountservice.DTOs.NewUser;
import com.lewisCode.accountservice.entity.Roles;
import com.lewisCode.accountservice.entity.MyUserDetailService;
import com.lewisCode.accountservice.entity.User;
import com.lewisCode.accountservice.exeptions.BreachedPasswordException;
import com.lewisCode.accountservice.exeptions.SamePasswordException;
import com.lewisCode.accountservice.exeptions.UserExistException;
import com.lewisCode.accountservice.service.BreachedPassword;
import com.lewisCode.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class SignUpController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BreachedPassword breachedPassword;
    private static boolean isFirst = true;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody NewUser newUser){
        if (userService.getUserByEmail(newUser.getEmail()).isPresent()){
            throw new UserExistException("User Exist!");
        }
        if (breachedPassword.isBreached(newUser.getPassword())) {
            throw new BreachedPasswordException("The password is in the hacker's database");
        }
        User user = new User();
        user.setName(newUser.getName());
        user.setLastname(newUser.getLastname());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRoles(Set.of(isFirst? Roles.ADMINISTRATION:Roles.USER));
        isFirst = false;

        return ResponseEntity.ok(new GetUser(userService.registration(user))) ;
    }
    @PostMapping("/auth/changepass")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword,
                                            @AuthenticationPrincipal MyUserDetailService
                                                    myUserDetailService){
            if (breachedPassword.isBreached(changePassword.getPassword())){
                throw new BreachedPasswordException("The password is in the hacker's database");
            }
            if (passwordEncoder.matches(changePassword.getPassword(),
                    myUserDetailService.getPassword())){
                throw new SamePasswordException("The passwords must be different");
            }
            Optional<User> signUp =
                    userService.getUserByEmail(myUserDetailService.getUsername());
            String mail ="";
            if (signUp.isPresent()){
              signUp.get().setPassword(passwordEncoder.encode(changePassword.getPassword()));
              userService.registration(signUp.get());
              mail = signUp.get().getEmail();
            }
            return  ResponseEntity.ok(Map.of("email:",mail ,
                    "status:", "The password has been updated successfully"));
    }
}
