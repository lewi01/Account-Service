package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.enums.Operation;
import com.lewisCode.accountservice.enums.Roles;
import com.lewisCode.accountservice.entity.MyUserDetailService;
import com.lewisCode.accountservice.entity.User;
import com.lewisCode.accountservice.exeptions.*;
import com.lewisCode.accountservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {


    public static final int MAX_FAILED_ATTEMPTS = 5;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw  new UsernameNotFoundException("Not found" + email);
        }
        return new MyUserDetailService(user.get());
    }

    public User registration(User user){
           return userRepository.save(user);
    }
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User giveRoles(String user, String role, String operation){
        //validating roles
        Roles roles;
        try {
           roles = Roles.valueOf(role);
        }catch (IllegalArgumentException e){
            throw new RoleNotFoundException("Role not found");
        }
        //validating operations
        Operation operation1;
        try {
            operation1 = Operation.valueOf(operation);
        }catch (IllegalArgumentException e){
            throw new NotSupportedRoleOperationException("Invalid operation!");
        }

        //validating Users
        Optional<User> user1 = userRepository.findByEmail(user);
        if (user1.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }
        Set<Roles> roles1 = user1.get().getRoles();
        switch (operation1){
            case REMOVE:
                if (roles == Roles.ADMINISTRATION) {
                    throw new RemoveAdministratorException("Can't remove ADMINISTRATOR role!");
                }

                if (!roles1.contains(roles)) {
                    throw new UserHasNotRoleSuchException("The user does not have a role!");
                }

                if (roles1.size() <= 1) {
                    throw new EmptyRoleException("The user must have at least one role!");
                }
                user1.get().removeRole(roles);
                break;
            case GRANT:
                if (roles == Roles.ADMINISTRATION || roles1.contains(Roles.ADMINISTRATION)){
                    throw  new CombinedRolesException("The user cannot combine administrative and \" +\n" +
                            "        \"business roles!");
                }
                user1.get().addRole(roles);
                break;

        }
        return userRepository.save(user1.get());
    }
    @Transactional
    public long deleteUserByEmail(String email) {
         return userRepository.deleteUserByEmail(email);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public void changeUserAccountNonLocked(String email, boolean isAccountNonLocked) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.get().hasRole(Roles.ADMINISTRATION)) {
            throw new BlockAdministratorException("Can't lock the ADMINISTRATOR!");
        }
        userRepository.updateUserAccountNonLocked(isAccountNonLocked, email);
    }

    public void loginAttemptsCount(String userEmail,  int value) {
        userRepository.updateFailedAttempts(value,userEmail);
    }

}
