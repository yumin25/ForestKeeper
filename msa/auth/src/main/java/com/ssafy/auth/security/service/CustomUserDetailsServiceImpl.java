package com.ssafy.auth.security.service;

import com.ssafy.auth.domain.dao.user.User;
import com.ssafy.auth.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmailAndDelete(email, false);

        if (user != null) return new CustomUserDetailsImpl(user);
        else throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

    }
}
