package com.opmile.register_user.service;

import com.opmile.register_user.dto.fetch_api.UserApiDTO;

import com.opmile.register_user.dto.user.UserCreateDTO;
import com.opmile.register_user.dto.user.UserUpdateDTO;
import com.opmile.register_user.exceptions.DuplicateUserException;
import com.opmile.register_user.exceptions.UserNotFoundException;
import com.opmile.register_user.mapper.UserMapper;
import com.opmile.register_user.model.Gender;
import com.opmile.register_user.model.Location;
import com.opmile.register_user.model.User;
import com.opmile.register_user.repository.UserRepository;
import com.opmile.register_user.service.client.RandomUserApiClient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RandomUserApiClient apiClient;

    public UserService(UserRepository userRepository, RandomUserApiClient apiClient) {
        this.userRepository = userRepository;
        this.apiClient = apiClient;
    }

    public List<User> getAllUser() {
        List<User> all = userRepository.findAll();
        System.out.println(all.size());
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public User registerUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateUserException("Usuário já cadastrado");
        }
        return userRepository.save(new User(dto));
    }

    @Transactional
    public User updateUser(Long id, UserUpdateDTO dto) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        updatedUser.setFullName(dto.fullName());
        updatedUser.setEmail(dto.email());
        updatedUser.setUsername(dto.username());
        updatedUser.setAge(dto.age());
        updatedUser.setGender(Gender.fromString(dto.gender()));
        updatedUser.setLocation(new Location(dto.location()));

        return userRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User deleteUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        userRepository.delete(deleteUser);
    }

    public List<User> registerFetch(int quantity) {
        List<UserApiDTO> usersApi = apiClient.fetchApi(quantity);

        List<User> fetchedUsers = usersApi.stream()
                .map(UserMapper::toEntity)
                .toList();

        return userRepository.saveAll(fetchedUsers);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
