package com.baseApp.backend.services;

import com.baseApp.backend.models.Role;
import com.baseApp.backend.models.User;
import com.baseApp.backend.payloads.responses.RoleResponse;
import com.baseApp.backend.payloads.responses.UserResponse;
import com.baseApp.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserResponse> getAll(PageRequest pageRequest){
        return userRepository.findAll(pageRequest)
                .map(r -> new UserResponse(r));
    }

    public User insertOrUpdate(User user){

        var optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setPhone(user.getPhone());
            existingUser.setPreferredLang(user.getPreferredLang());
            existingUser.setRoles(user.getRoles());

            return userRepository.save(existingUser);
        } else {
            return userRepository.save(user);
        }
    }

}
