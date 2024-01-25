package com.example.test.service.auth;

import com.example.test.domain.User;
import com.example.test.domain.enumration.ERole;
import com.example.test.repository.IUserRepository;
import com.example.test.service.auth.request.LoginRequest;
import com.example.test.service.auth.request.RegisterRequest;
import com.example.test.util.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService, IAuthService {
    private final IUserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    public AuthService(IUserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().toString())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }

    public void register(RegisterRequest request) {
        if (userRepository.findUserByUsername(request.username()).isPresent()) {
            throw new IllegalStateException("Username is already taken");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(ERole.ROLE_USER);
        user.setEnabled(true);
        userRepository.save(user);

    }

    public String login(LoginRequest request) {
        UserDetails userDetails = loadUserByUsername(request.username());
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new IllegalStateException("Wrong password or username");
        }
        return jwtTokenProvider.generateToken(request.username());
    }
}