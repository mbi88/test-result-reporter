package com.mbi.api.controllers;

import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.UsersRequestModel;
import com.mbi.api.models.response.UserCreatedModel;
import com.mbi.api.models.response.UserResponseModel;
import com.mbi.api.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UsersController {

    @Autowired
    private UsersService service;

    @RequestMapping(method = POST, path = "/users", produces = "application/json")
    public ResponseEntity<UserCreatedModel> create(@Valid @RequestBody UsersRequestModel usersRequestModel) {
        return service.createUser(usersRequestModel);
    }

    @RequestMapping(method = GET, path = "/users/{id}", produces = "application/json")
    public ResponseEntity<UserResponseModel> getById(@PathVariable(value = "id") long id) throws NotFoundException {
        return service.getUserById(id);
    }

    @RequestMapping(method = GET, path = "/users", produces = "application/json")
    public ResponseEntity<List<UserResponseModel>> getList(@RequestParam(value = "name", required = false) String name) {
        return service.getUsersList(name);
    }

    @RequestMapping(method = DELETE, path = "/users/{id}", produces = "application/json")
    public ResponseEntity delete(@PathVariable(value = "id") long id) throws NotFoundException {
        return service.deleteUser(id);
    }

    @RequestMapping(method = PATCH, path = "/users/{id}", produces = "application/json")
    public ResponseEntity update(
            @PathVariable(value = "id") long id,
            @Valid @RequestBody UsersRequestModel usersRequestModel) throws NotFoundException {
        return service.updateUser(id, usersRequestModel);
    }
}
