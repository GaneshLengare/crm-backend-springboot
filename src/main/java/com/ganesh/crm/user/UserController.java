package com.ganesh.crm.user;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO userDTO1 = userService.registerUser(userDTO);
        return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);
    }


    //Error : The error Type 'org.hibernate.query.Page' does not have type parameters happens because Hibernate's internal Page class is legacy and does not support Generics (<UserDTO>).
    // NOTE : Page size must not less than one
    @GetMapping
    public ResponseEntity<Page<UserDTO>> listAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "phoneNumber") String sortBy){
        Page<UserDTO> pageList = userService.listAllUsers(page, size, sortBy);
        return ResponseEntity.ok(pageList);
    }


    @GetMapping("/{phoneNumber}")
    public ResponseEntity<UserDTO> singleUser(@PathVariable String phoneNumber) {
        UserDTO singleUser = userService.sigleUser(phoneNumber);
        return ResponseEntity.ok(singleUser);
    }


    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDTO>> searchUser(@PathVariable String keyword) {
        List<UserDTO> userDTOS = userService.searchUser(keyword);
        return ResponseEntity.ok(userDTOS);
    }

    @PutMapping("/update/{phoneNumber}")
    public ResponseEntity<UserDTO> updateUserDetails(@PathVariable String phoneNumber, @RequestBody UserDTO userDTO) {
        UserDTO userDTO1 = userService.updateUser(phoneNumber, userDTO);
        return ResponseEntity.ok(userDTO1);
    }

}
