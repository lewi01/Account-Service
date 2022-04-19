package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.DTOs.ChangeStatus;
import com.lewisCode.accountservice.DTOs.GetUser;
import com.lewisCode.accountservice.DTOs.GiveRoles;
import com.lewisCode.accountservice.entity.MyUserDetailService;
import com.lewisCode.accountservice.entity.User;
import com.lewisCode.accountservice.enums.Actions;
import com.lewisCode.accountservice.enums.Operation;
import com.lewisCode.accountservice.exeptions.RemoveAdministratorException;
import com.lewisCode.accountservice.exeptions.UserNotFoundException;
import com.lewisCode.accountservice.service.LogService;
import com.lewisCode.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
@Validated
public class AdminController {

    private final UserService userService;

    private final LogService logService;

    @GetMapping
    public ResponseEntity<?>getAllUsers(){
        return ResponseEntity.ok(userService.findAll().stream()
                .map(GetUser::new).collect(Collectors.toList()));
    }
    @PutMapping("/roles")
    public ResponseEntity<?> addUser(@AuthenticationPrincipal MyUserDetailService myUserDetailService,
                                     @Valid @RequestBody GiveRoles giveRoles,
                                     HttpServletRequest httpServletRequest){
        User user = userService.giveRoles(giveRoles.getUser(), giveRoles.getRole(),
                giveRoles.getOperation());
        Actions actions = Actions.valueOf(giveRoles.getRole());
        Operation operation = Operation.valueOf(giveRoles.getRole());
        String joint = Operation.GRANT == operation? "to": "from";
        String subject = String.format("%s role %s %s %s", operation.getActionNameCapitalized(),
                giveRoles.getRole(),joint,giveRoles.getUser());
        logService.createLogs(actions, myUserDetailService.getUsername(),
                subject,httpServletRequest.getRequestURI() );
        return ResponseEntity.ok(new GetUser(user));
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<?>deleteUser(@AuthenticationPrincipal MyUserDetailService myUserDetailService,
                                       @PathVariable String email,HttpServletRequest httpServletRequest){
        long users = userService.deleteUserByEmail(email);
        if (myUserDetailService.getUsername().equals(email)){
            throw new RemoveAdministratorException("Can't remove ADMINISTRATOR role!");
        }
        if (users <= 0){
            throw new UserNotFoundException("User not found!");
        }
        logService.createLogs(Actions.DELETE_USER,myUserDetailService.getUsername(),
                email,httpServletRequest.getRequestURI());
        return  ResponseEntity.ok(Map.of("user", email, "status", "Deleted successfully!"));
    }
    @PutMapping("/access")
    public ResponseEntity<?> changeUserAccess(@RequestBody @Valid ChangeStatus operation,
                                              HttpServletRequest httpServletRequest,
                                              @AuthenticationPrincipal MyUserDetailService myUserDetailService) {
        String username = operation.getUser();
        boolean isAccountNonLocked = operation.getOperation().equals("UNLOCK");
        userService.changeUserAccountNonLocked(username, isAccountNonLocked);

        Map<String, String> response = Map.of("status",
                String.format("User %s %sed!", username, operation.getOperation().toLowerCase(Locale.ROOT)));


        logService.createLogs(isAccountNonLocked ? Actions.UNLOCK_USER : Actions.LOCK_USER,
                myUserDetailService.getUsername(),
                operation.getOperationCapitalized() + " user " + username,
                httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
