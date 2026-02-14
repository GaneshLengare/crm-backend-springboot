package com.ganesh.crm.usertype;

import java.util.List;

public interface UserTypeService {

    //Create User Type <- this permission is very important only Admin or who manage the application can access this endpoint
    UserType createUserType(UserType userType);

    //To see the which are the usertypes are present
    List<UserType> getAllUserTypes();

    //For updating the User Types
    UserType updateUserTypes(Long id, String type);

    //To delete the User Type
    void deleteUserType(long id);


}
