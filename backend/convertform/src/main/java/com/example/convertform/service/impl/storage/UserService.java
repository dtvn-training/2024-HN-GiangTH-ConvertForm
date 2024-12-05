package com.example.convertform.service.impl.storage;

import com.example.convertform.entity.User;
import com.example.convertform.sqlmapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetail = userMapper.getUserByName(username);

        if (userDetail == null) throw new UsernameNotFoundException("User not found: " + username);

        return new CustomUserDetails(userDetail);
    }

    public void addUser(User userInfo) {
        userMapper.insertUser(userInfo);
    }

    public boolean isUserExist(String userName) {
        User user = userMapper.getUserByName(userName);

        return user != null;
    }
}
