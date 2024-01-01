package com.IOT.IOT_system.controller;

import com.IOT.IOT_system.DTO.Login_response;
import com.IOT.IOT_system.DTO.UserDTO;
import com.IOT.IOT_system.DTO.LoginDTO;
import com.IOT.IOT_system.model.User;
import com.IOT.IOT_system.repository.UserDTO_Repository;
import com.IOT.IOT_system.service.impl.UserImpl;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserImpl userService;
    @Autowired
    private UserDTO_Repository userDTO_repo;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping(path = "/saveUser")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    public User addUser_to_DB(@RequestBody UserDTO userDTO)
    {
        User userx = userService.addUser(userDTO);
        return userx;
    }
    @PostMapping(path = "/saveUserDTO")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String addUserDTO_to_DB(@RequestBody UserDTO userDTO)
    {

        String ten =  userService.save_UserDTO_to_db(userDTO);
        return ten;
    }

    @PostMapping(path = "/login")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('ROLE_USER')")

    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        Login_response login_response =  userService.loginUser(loginDTO);
        return ResponseEntity.ok(login_response);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(path = "/listDTO")
    public List<UserDTO> getAllUserDTO() {
        return userDTO_repo.findAll();
    }

    @DeleteMapping (path = "/deletex/{id}")
    public ResponseEntity<Map<String, Boolean>>deleteUserDTO(@PathVariable Long id){
        userDTO_repo.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}

