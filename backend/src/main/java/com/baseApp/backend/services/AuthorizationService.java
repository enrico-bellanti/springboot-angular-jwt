package com.baseApp.backend.services;

import com.baseApp.backend.models.Permission;
import com.baseApp.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Autowired
    private RoleRepository roleRepository;

    public boolean hasPermissions(Set<String> permissions){
        //todo check toList() instead collect(Collectors.toList())
        return new HashSet<>(SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .flatMap(r -> roleRepository.findByName(r.getAuthority()).get().getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList())
                .containsAll(permissions);
    }

}
