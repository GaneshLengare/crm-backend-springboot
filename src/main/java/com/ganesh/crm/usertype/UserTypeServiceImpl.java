package com.ganesh.crm.usertype;

import com.ganesh.crm.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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

    @Override
    public List<UserType> getAllUserTypes() {
        List<UserType> userType = userTypeRepository.findAll();
        return userType;
    }

    @Override
    public UserType updateUserTypes(Long id, String typeName) {
        UserType userType = userTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id NOT FOUND!"));
        userType.setTypeName(typeName);
        userTypeRepository.save(userType);
        return userType;
    }

    @Override
    public void deleteUserType(long id) {
        userTypeRepository.deleteById(id);
    }
}
