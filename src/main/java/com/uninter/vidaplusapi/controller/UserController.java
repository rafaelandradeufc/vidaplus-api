package com.uninter.vidaplusapi.controller;

import com.uninter.vidaplusapi.dto.UserRequestDTO;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "size", defaultValue = "20") int size,
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
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody final UserRequestDTO userRequest) {
        User user = service.save(userRequest).orElseThrow();
        return ResponseEntity.ok(user.toDTO());
    }

}
