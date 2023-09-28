package com.example.controller;

import com.example.controller.request.CreateUserDto;
import com.example.models.ERole;
import com.example.models.RoleEntity;
import com.example.models.UserEntity;
import com.example.repositories.UserRepository;
import com.example.security.SecurityConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PrincipalController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @GetMapping("/hello")
    public String hello(){
        return "hello world not secured";
    }

    @GetMapping("/helloSecured")
    public String helloSecured(){
        return "hello world secured";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody  CreateUserDto createUserDto){

        Set<RoleEntity> roles = createUserDto.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .email(createUserDto.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id: ".concat(id);
    }
}
