package com.app.ecom.service;

import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();
//    private Long nextId = 1L;

    public List<User> fetchAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
//        user.setId(nextId++);
        userRepository.save(user);
    }


    public Optional<User> fetchUser(Long id) {
        return userRepository.findById(id);
    }
    public boolean updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
}