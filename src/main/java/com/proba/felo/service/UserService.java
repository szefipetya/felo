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
//@RequiredArgsConstructor
public class UserService {
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

    @EventListener(ApplicationReadyEvent.class)
    public void getUsers() {
        final String URI = "https://jsonplaceholder.typicode.com/users";
        final HttpMethod METHOD = HttpMethod.GET;

        RestTemplate restTemplate = new RestTemplate();
        //String result = restTemplate.getForObject(uri, String.class);
        List<UserDto> users;
        ResponseEntity<String> response = restTemplate.exchange(URI, METHOD, null, String.class);
        int statusCode = response.getStatusCodeValue();
        loggingService.logApiCall(response, URI, METHOD);

        try {
            users = objectMapper.readValue(response.getBody(), new TypeReference<List<UserDto>>() {
            });
            //save users
            List<User> savedUsers = saveUsers(users);
            saveUserEmailsValid(savedUsers);

            //save user email validations
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private User mapUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Transactional()
    private void saveIsEmailValid(User user, boolean isEmailValid) {
        IsEmailValid isEmailValidToSave = new IsEmailValid();
        isEmailValidToSave.setValid(isEmailValid);
        isEmailValidToSave.setEmail(user.getEmail());
        isEmailValidToSave.set_user(user);
        isEmailValidRepository.save(isEmailValidToSave);
    }


    private boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher m = p.matcher(email);
        return m.find();
    }


    private void saveUserEmailsValid(List<User> users) {
        users.stream().forEach(u -> saveIsEmailValid(u, isEmailValid(u.getEmail())));
    }

    private List<User> saveUsers(List<UserDto> users) {
        return save(users.stream().map(this::mapUser).collect(Collectors.toList()));
    }

    @Transactional()
    private List<User> save(List<User> users) {
        return userRepository.saveAll(users);
    }
}
