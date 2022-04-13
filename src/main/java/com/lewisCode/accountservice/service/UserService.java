package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.Operation;
import com.lewisCode.accountservice.entity.Roles;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        User user1 = userRepository.findFirstByEmail(user);
        if (user1 == null){
            throw new UserNotFoundException("User not found!");
        }
        Set<Roles> roles1 = user1.getRoles();
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
                user1.removeRole(roles);
                break;
            case GRANT:
                if (roles == Roles.ADMINISTRATION || roles1.contains(Roles.ADMINISTRATION)){
                    throw  new CombinedRolesException("The user cannot combine administrative and \" +\n" +
                            "        \"business roles!");
                }
                user1.addRole(roles);
                break;

        }
        return userRepository.save(user1);
    }
    @Transactional
    public long deleteUserByEmail(String email) {
         return userRepository.deleteUserByEmail(email);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
