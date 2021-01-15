package com.gmail.burinigor7.messenger.security;

import com.gmail.burinigor7.messenger.domain.Status;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static String ROLE_PREFIX = "ROLE_";
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        return new SecurityUser(
                user.getUsername(), user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                Set.of(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name()))
        );
    }
}
