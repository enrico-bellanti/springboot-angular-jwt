package com.baseApp.backend.services;

import com.baseApp.backend.models.User;
import com.baseApp.backend.payloads.responses.UserResponse;
import com.baseApp.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public Page<UserResponse> getAll(PageRequest pageRequest){
        return userRepository.findAll(pageRequest)
                .map(r -> new UserResponse(r));
    }

    public Optional<User> getById(UUID id){
        return userRepository.findById(id);
    }

    public  Optional<User> first() {
        return userRepository.findFirstByOrderByCreatedAtDesc();
    }

    public User insertOrUpdate(User user){

        var optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();

            //todo manage missing details
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(encoder.encode(user.getPassword()));
            existingUser.setIsEnabled(user.getIsEnabled());
            existingUser.setPhone(user.getPhone());
            existingUser.setPreferredLang(user.getPreferredLang());
            existingUser.setRoles(user.getRoles());

            return userRepository.save(existingUser);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

}
