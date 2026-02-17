package com.ganesh.crm.user;


import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);


    Page<UserDTO> listAllUsers(int page, int size, String sortBy);

    UserDTO sigleUser(String phoneNumber);

    List<UserDTO> searchUser(String keyword);


    UserDTO updateUser(String phoneNumber, UserDTO userDTO);

    void resetPassword(String phoneNumber, UserPasswordResetDTO dto);

    void disableUser(String phoneNumber);

    void enableUser(String phoneNumber);

    byte[] generateUserCSV();

}
