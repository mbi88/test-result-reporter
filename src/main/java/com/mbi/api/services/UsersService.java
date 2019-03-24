package com.mbi.api.services;

import com.mbi.api.entities.users.UsersEntity;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.UsersRequestModel;
import com.mbi.api.models.response.UserCreatedModel;
import com.mbi.api.models.response.UserResponseModel;
import com.mbi.api.repositories.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<UserCreatedModel> createUser(UsersRequestModel usersRequestModel) {
        UsersEntity usersEntity = new ModelMapper().map(usersRequestModel, UsersEntity.class);
        usersRepository.save(usersEntity);
        UserCreatedModel userCreatedModel = new ModelMapper().map(usersEntity, UserCreatedModel.class);

        return new ResponseEntity<>(userCreatedModel, HttpStatus.CREATED);
    }

    public ResponseEntity<UserResponseModel> getUserById(Long id) throws NotFoundException {
        if (!usersRepository.findById(id).isPresent()) {
            throw new NotFoundException(UsersEntity.class);
        }
        UsersEntity usersEntity = usersRepository.findById(id).get();
        UserResponseModel userResponseModel = new ModelMapper().map(usersEntity, UserResponseModel.class);

        return new ResponseEntity<>(userResponseModel, HttpStatus.OK);
    }

    public ResponseEntity<List<UserResponseModel>> getUsersList(String name) {
        List<UsersEntity> users = (name == null || name.isEmpty())
                ? (List<UsersEntity>) usersRepository.findAll()
                : usersRepository.findByName(name);
        List<UserResponseModel> responseModels = users
                .stream()
                .map(u -> new ModelMapper().map(u, UserResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseModels, HttpStatus.OK);
    }

    public ResponseEntity deleteUser(long id) throws NotFoundException {
        if (!usersRepository.findById(id).isPresent()) {
            throw new NotFoundException(UsersEntity.class);
        }
        usersRepository.deleteById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity updateUser(long id, UsersRequestModel usersRequestModel) throws NotFoundException {
        if (!usersRepository.findById(id).isPresent()) {
            throw new NotFoundException(UsersEntity.class);
        }
        UsersEntity usersEntity = new ModelMapper().map(usersRequestModel, UsersEntity.class);
        usersEntity.setId(id);
        usersRepository.save(usersEntity);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
