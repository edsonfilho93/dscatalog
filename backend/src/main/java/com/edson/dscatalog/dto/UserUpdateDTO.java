package com.edson.dscatalog.dto;

import com.edson.dscatalog.services.validation.UserInsertValid;
import com.edson.dscatalog.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO {
    private static final long serialVersionUID = 29926102646523573L;
}
