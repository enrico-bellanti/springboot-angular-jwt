package com.baseApp.backend.services;

import com.baseApp.backend.payloads.responses.RoleResponse;
import com.baseApp.backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Page<RoleResponse> getAll(PageRequest pageRequest){
        return roleRepository.findAll(pageRequest)
                .map(r -> new RoleResponse(r));
    }

}
