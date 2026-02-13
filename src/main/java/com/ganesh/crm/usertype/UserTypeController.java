package com.ganesh.crm.usertype;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usertypes")
@RequiredArgsConstructor
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @PostMapping
    public ResponseEntity<UserType> createUserType(@RequestBody UserType userType) {
        UserType userType1 = userTypeService.createUserType(userType);
        return new ResponseEntity<>(userType1, HttpStatus.CREATED);
    }
}
