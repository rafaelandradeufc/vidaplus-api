package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.AuthDTO;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.model.type.RoleType;
import com.uninter.vidaplusapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthDTO.ApiResponse register(AuthDTO.RegisterRequest request) {
        // Verifica se o email já está cadastrado
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthDTO.ApiResponse("Email já está em uso", false);
        }
        // Cria o novo usuário
        final var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .isActive(true)
                .roleType(RoleType.STANDARD)
            .build();

        userRepository.save(user);

        return new AuthDTO.ApiResponse("Usuário registrado com sucesso", true);
    }

    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest request) {

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException e) {
            log.error("Erro de autenticação para {}: {}", request.getEmail(), e.getMessage(), e);
            throw new BadCredentialsException("Credenciais inválidas");
        } catch (Exception e) {
            log.error("Erro inesperado durante login: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno do servidor");
        }


        // Busca o usuário
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera o token JWT
        String jwt = jwtService.generateToken(user);

        return new AuthDTO.LoginResponse(
                jwt,
                "Bearer",
                user.getId(),
                user.getEmail()
        );
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}