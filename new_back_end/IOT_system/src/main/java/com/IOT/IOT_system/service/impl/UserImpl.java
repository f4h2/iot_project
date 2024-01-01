package com.IOT.IOT_system.service.impl;

import com.IOT.IOT_system.DTO.LoginDTO;
import com.IOT.IOT_system.DTO.Login_response;
import com.IOT.IOT_system.DTO.UserDTO;
import com.IOT.IOT_system.model.User;
import com.IOT.IOT_system.repository.UserDTO_Repository;
import com.IOT.IOT_system.repository.UserRepository;
import com.IOT.IOT_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserDTO_Repository userDTO_repo;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User(
                (Long) userDTO.getUser_id(),
                userDTO.getUser_ten(),
                userDTO.getUser_tk(),
//                this.passwordEncoder.encode(userDTO.getUser_mk()),
                userDTO.getUser_mk(),
                userDTO.getRole()
        );
        return userRepo.save(user);
    }
    UserDTO userDTO;
    @Override
    public Login_response  loginUser(LoginDTO loginDTO) {
        User user = null;
        String message="";
        Optional<User> userOptional = userRepo.findByTen_tk(loginDTO.getUser_tk());
        user = userOptional.get();
        if (userOptional.isPresent()) {


            String password = loginDTO.getUser_mk();
//            String encodedPassword = user.getMk_user();
//            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            String Password = user.getMk_user();
            if (Password.equals(password)) {

                    message = "Login Ok";

            } else {
                message = "password Not Match";
            }
        }
        else {
             message = "Email not exits";
        }

        if(user == null){
            Login_response login_response = new Login_response(message, "xxxx","xxxx" );
            return login_response;
        }else{
        Login_response login_response = new Login_response(message, user.getTk_user(),user.getRoles() );
        return login_response;}
    }

    public String deleteUserDTO( Long id){
        Optional<UserDTO> userOptional = userDTO_repo.findById(id);
        if(userOptional.isPresent()) {
            UserDTO user_DTO = userOptional.get();
            userDTO_repo.delete(user_DTO);
            return "delete ok";
        }else{
            return "delete -ok";
        }
    }
    public List<User> listAll() {
        return userRepo.findAll();
    }
    @Override
    public String save_UserDTO_to_db(UserDTO userDTO) {
        userDTO_repo.save(userDTO);
        return userDTO.getUser_ten();
    }
}
