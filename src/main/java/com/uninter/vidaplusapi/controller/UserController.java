package com.uninter.vidaplusapi.controller;

import com.uninter.vidaplusapi.dto.UserRequestDTO;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Operações sobre usuários")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Busca paginada de usuários", description = "Retorna página")
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "username,asc") String sortParam) {

        String[] parts = sortParam.split(",");
        Sort sort = Sort.by(parts[0]);
        if (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Page<UserResponseDTO> pageResult = service.findAll( page, size, sort);
        return ResponseEntity.ok(pageResult);
    }
    @Operation(summary = "Adiciona um novo usuário", description = "Cria um novo usuário no sistema")
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(
            @RequestBody final UserRequestDTO userRequest,
            @RequestHeader UUID organizationId) {
        User user = service.save(userRequest,organizationId).orElseThrow();
        return ResponseEntity.ok(user.toResponseDTO());
    }

    @Operation(summary = "Remove um usuário", description = "Remove um usuário existente pelo ID")
    @DeleteMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        service.deleteById(userId);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Busca um usuário pelo ID", description = "Retorna os detalhes de um usuário específico pelo ID")
    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID userId) {
        UserResponseDTO userDTO = service.findById(userId).toResponseDTO();
        return ResponseEntity.ok(userDTO);
    }


}
