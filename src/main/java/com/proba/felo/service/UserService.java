package com.proba.felo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proba.felo.model.dto.UserDto;
import com.proba.felo.model.entity.IsEmailValid;
import com.proba.felo.model.entity.User;
import com.proba.felo.repository.IsEmailValidRepository;
import com.proba.felo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final String URI = "https://jsonplaceholder.typicode.com/users";
    private final HttpMethod METHOD = HttpMethod.GET;
    private final int TEST_CALL_TIMES = 10;

    @Autowired
    UserRepository userRepository;
    @Autowired
    IsEmailValidRepository isEmailValidRepository;
    @Autowired
    LoggingService loggingService;
    @Qualifier("jsonMapper")
    @Autowired
    ObjectMapper objectMapper;
    @Qualifier("modelMapper")
    @Autowired
    ModelMapper modelMapper;

    private List<User> getUsers() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(URI, METHOD, null, String.class);
        loggingService.logApiCall(response, URI, METHOD);

        List<UserDto> users = objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });
        List<User> savedUsers = mapUsersAndSave(users);
        saveUserEmailsValid(savedUsers);

        return savedUsers;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testUsersEndpoint() throws JsonProcessingException {
        Integer usersSize = null;
        for (int i = 0; i < TEST_CALL_TIMES; i++) {
            if (usersSize == null) {
                usersSize = getUsers().size();
            } else {
                int currentUsersSize = getUsers().size();
                if (currentUsersSize != usersSize) {
                    loggingService.getLogger().error("Test " + i + " unSuccesful\n usersSize was " +
                            currentUsersSize + " instead of " + usersSize);
                    break;
                }
                loggingService.getLogger().info("Test " + i + " succesful\n----------------------------------------");
            }
        }
        loggingService.getLogger().info(" All " + TEST_CALL_TIMES + " Test calls were succesful");

        exit();
    }

    private User mapUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private void saveIsEmailValid(User user, boolean isEmailValid) {
        if (!isEmailValidRepository.findBy_user_id(user.getId()).isPresent()) {
            IsEmailValid isEmailValidToSave = new IsEmailValid();
            isEmailValidToSave.setValid(isEmailValid);
            isEmailValidToSave.setEmail(user.getEmail());
            isEmailValidToSave.set_user(user);
            isEmailValidRepository.save(isEmailValidToSave);
        }
    }

    private boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher m = p.matcher(email);
        return m.find();
    }

    private void saveUserEmailsValid(List<User> users) {
        users.forEach(u -> saveIsEmailValid(u, isEmailValid(u.getEmail())));
    }

    private List<User> mapUsersAndSave(List<UserDto> users) {
        return saveUsers(users.stream().map(this::mapUser).collect(Collectors.toList()));
    }

    @Transactional()
    private List<User> saveUsers(List<User> users) {
        for (User u : users) {
            if (!userRepository.existsById(u.getId())) {
                userRepository.save(u);
            }
        }
        return users;
    }

    private void exit() {
        System.exit(0);
    }
}
