package org.ipan.restaurant.auth.controllers;


import org.bte.auth.connector.api.UsersApi;
import org.bte.auth.connector.model.AppUserDTO;
import org.springframework.http.ResponseEntity;

public class UsersController implements UsersApi {
    @Override
    public ResponseEntity<AppUserDTO> createUser() {
        return null;
    }

    @Override
    public ResponseEntity<AppUserDTO> getUsers(Integer offset, Integer limit) {
        return null;
    }
}
