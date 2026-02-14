package com.ganesh.crm.user;

import com.ganesh.crm.usertype.UserTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public Page<UserDTO> listAllUsers(int page, int size, String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<User> users = userRepository.findAll(pageRequest);
        return users.map(eachUser -> {
            UserDTO userDTO = modelMapper.map(eachUser, UserDTO.class);
            userDTO.setStatus(eachUser.getStatus().name());
            return userDTO;
        });

    }

    @Override
    public UserDTO sigleUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new EntityNotFoundException());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    @Override
    public List<UserDTO> searchUser(String keyword) {
        List<User> search = userRepository.searchUsers(keyword);

        return search.stream().map(eachSearch -> {
            UserDTO userDTO = modelMapper.map(eachSearch, UserDTO.class);
            userDTO.setStatus(eachSearch.getStatus().name());
            return userDTO;
        }).toList();
    }

    //ISSUE : we have to set all new values here
    //TASK : Use Mapper
    //ISSUE + TASK : Here we are  not tracking the updated Time, its needed to track the history when last time user updated,and also ***who updated
    @Override
    public UserDTO updateUser(String phoneNumber, UserDTO userDTO) {
        User userold = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new EntityNotFoundException("User Not Found With this phoneNumber"));

        //userold.setPhoneNumber(userDTO.getPhoneNumber());
        userold.setFirstName(userDTO.getFirstName());
        userold.setLastName(userDTO.getLastName());
        userold.setEmail(userDTO.getEmail());
        userold.setAddress(userDTO.getAddress());
        userold.setPassword(passwordEncoder.encode(userDTO.getPassword()));    //Note : Do not forgot use passwordEncoder here
        userold.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(userold);

        UserDTO userNew = modelMapper.map(updated, UserDTO.class);
        userNew.setStatus(updated.getStatus().name());
        userNew.setUpdatedAt(updated.getUpdatedAt());

        return userNew;

    }

}
