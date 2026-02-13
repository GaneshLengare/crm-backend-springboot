package com.ganesh.crm.usertype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeServiceImpl implements UserTypeService{

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public UserType createUserType(UserType userType) {

        userTypeRepository.findByTypeName(userType.getTypeName())
                .ifPresent( u-> {
                    throw new RuntimeException("UserType Already Exixts!");
                });
        return userTypeRepository.save(userType);
    }
}
