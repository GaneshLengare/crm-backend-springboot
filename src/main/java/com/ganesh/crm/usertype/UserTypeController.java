package com.ganesh.crm.usertype;

import com.ganesh.crm.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping
    public ResponseEntity<List<UserType>> getUserTypes() {
        List<UserType> userType = userTypeService.getAllUserTypes();
        return ResponseEntity.ok(userType);
    }


    @PutMapping("/{id}/{typeName}")
    public ResponseEntity<UserType> updateUserType(@PathVariable Long id, @PathVariable String typeName) {
        UserType userType = userTypeService.updateUserTypes(id,typeName);
        return new ResponseEntity<>(userType, HttpStatus.OK);
    }



    //ISSUE : Cannot delete the User type because Multiple user associated with this type by id as FK <- Find the best Solution for it???
    @DeleteMapping("/{id}")
    public  void deleteUser(@PathVariable Long id) {
        userTypeService.deleteUserType(id);
    }
}
