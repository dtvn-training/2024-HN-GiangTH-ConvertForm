package com.example.convertform.service.impl.repository;

import com.example.convertform.entity.CustomUserDetails;
import com.example.convertform.entity.User;
import com.example.convertform.sqlmapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetail = userMapper.getUserByName(username);

        if (userDetail == null) throw new UsernameNotFoundException("User not found: " + username);

        return new CustomUserDetails(userDetail);
    }

    public String addUser(User userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userMapper.insertUser(userInfo);
        return "User Added Successfully";
    }
}
