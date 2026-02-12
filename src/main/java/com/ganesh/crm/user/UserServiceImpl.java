package com.ganesh.crm.user;

import com.ganesh.crm.usertype.UserTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        if(userRepository.existsById(userDTO.getPhoneNumber())){
            throw new IllegalArgumentException("User with this phone number already exists");
        }

        var userType = userTypeRepository.findById(userDTO.getUserTypeId()).orElseThrow(() -> new EntityNotFoundException("userType is not found"));

        // DTO to User and set the values
        User user = modelMapper.map(userDTO, User.class);
        user.setUserType(userType);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setStatus(User.Status.valueOf("ACTIVE"));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // save to db
        User saveUser = userRepository.save(user);

        // User to UserDTO
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setUserTypeId(user.getUserType().getId());
        dto.setPassword(null);

        return dto;
    }
}
