package com.ddoko.web.rest.vm;

import com.ddoko.service.dto.UserDTO;
import javax.validation.constraints.Size;
import com.ddoko.domain.Address;
import java.util.HashSet;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private Set<Address> addresses = new HashSet<>();

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
