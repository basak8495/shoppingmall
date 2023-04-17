package com.project.shoppingmall.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User userSave(UserRequestDto userRequestDto) {

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        userRequestDto.setPassword(encodedPassword);
        userRequestDto.setEnabled(true);

        User user = userRequestDto.toEntity();

        Role role = new Role();
        role.setId(2l);
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}