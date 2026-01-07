package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);
    }

    @Test
    void findAll_mapsUsersToDto_and_respectsPaginationAndSort() {
        User u1 = new User();
        UUID id1 = UUID.randomUUID();
        u1.setId(id1);
        u1.setUsername("alice");
        u1.setEmail("alice@example.com");
        u1.setFullName("Alice A");

        User u2 = new User();
        UUID id2 = UUID.randomUUID();
        u2.setId(id2);
        u2.setUsername("bob");
        u2.setEmail("bob@example.com");
        u2.setFullName("Bob B");

        Page<User> page = new PageImpl<>(List.of(u1, u2));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        int pageIndex = 0;
        int pageSize = 10;
        Sort sort = Sort.by(Sort.Direction.ASC, "username");

        Page<UserResponseDTO> result = service.findAll(pageIndex, pageSize, sort);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository, times(1)).findAll(captor.capture());
        Pageable used = captor.getValue();
        assertEquals(pageIndex, ((PageRequest) used).getPageNumber());
        assertEquals(pageSize, ((PageRequest) used).getPageSize());
        assertEquals(sort, used.getSort());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());

        List<UserResponseDTO> content = result.getContent();
        assertEquals(id1, content.get(0).getId());
        assertEquals("alice", content.get(0).getUsername());
        assertEquals("alice@example.com", content.get(0).getEmail());
        assertEquals("Alice A", content.get(0).getFullName());
        assertNotNull(content.get(0).getCreatedAt());

        assertEquals(id2, content.get(1).getId());
        assertEquals("bob", content.get(1).getUsername());
        assertEquals("bob@example.com", content.get(1).getEmail());
        assertEquals("Bob B", content.get(1).getFullName());
        assertNotNull(content.get(1).getCreatedAt());
    }
}
