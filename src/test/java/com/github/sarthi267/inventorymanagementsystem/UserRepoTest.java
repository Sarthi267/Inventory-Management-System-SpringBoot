package com.github.sarthi267.inventorymanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldSaveAndFindUser() {
        User user = new User();
       user.setUsername("test");
       user.setPassword("test");
       user.setRole(Role.USER);
       userRepository.save(user);

       Optional<User> found = userRepository.findById(user.getId());

       assertThat(found).isPresent();
       assertThat(found.get().getUsername()).isEqualTo("test");

        }
    @Test
    void shouldFindUserByUsername() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        userRepository.save(user);
        Optional<User> found = userRepository.findByUsername("test");
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("test");
    }
    @Test
    void shouldFindById() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        User saved = userRepository.save(user);

        Optional<User> found = userRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("test");
    }
    @Test
    void userQuery() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);

        testEntityManager.persistAndFlush(user);

        assertThat(userRepository.findByUsername("test")).isPresent();
        assertThat(userRepository.findByUsername("test").get()
                .getUsername()).isEqualTo("test");
    }
    }



