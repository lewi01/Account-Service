package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.DTOs.GetUser;
import com.lewisCode.accountservice.DTOs.GiveRoles;
import com.lewisCode.accountservice.entity.MyUserDetailService;
import com.lewisCode.accountservice.entity.User;
import com.lewisCode.accountservice.exeptions.RemoveAdministratorException;
import com.lewisCode.accountservice.exeptions.UserNotFoundException;
import com.lewisCode.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
@Validated
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?>getAllUsers(){
        return ResponseEntity.ok(userService.findAll().stream()
                .map(GetUser::new).collect(Collectors.toList()));
    }
    @PutMapping("/roles")
    public ResponseEntity<?> addUser(@AuthenticationPrincipal MyUserDetailService myUserDetailService,
                                     @Valid @RequestBody GiveRoles giveRoles){
        User user = userService.giveRoles(giveRoles.getUser(), giveRoles.getRole(), giveRoles.getOperation());
        return ResponseEntity.ok(new GetUser(user));
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<?>deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable String email){
        long users = userService.deleteUserByEmail(email);
        if (userDetails.getUsername().equals(email)){
            throw new RemoveAdministratorException("Can't remove ADMINISTRATOR role!");
        }
        if (users <= 0){
            throw new UserNotFoundException("User not found!");
        }
        return  ResponseEntity.ok(Map.of("user", email, "status", "Deleted successfully!"));
    }
}
