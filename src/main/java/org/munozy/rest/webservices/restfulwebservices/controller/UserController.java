package org.munozy.rest.webservices.restfulwebservices.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.munozy.rest.webservices.restfulwebservices.domain.User;
import org.munozy.rest.webservices.restfulwebservices.dto.PostDto;
import org.munozy.rest.webservices.restfulwebservices.dto.UserDto;
import org.munozy.rest.webservices.restfulwebservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(UserController.PATH)
public class UserController {
    static final String PATH = "/users";

    @Autowired
    MessageSource messageSource;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserService userService;

    @GetMapping(path = "/")
    public MappingJacksonValue getAllUsers() {

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("login", "name", "birthday");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllUsers());
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    @GetMapping(path = "/{id}")
    public MappingJacksonValue getUser(@PathVariable long id) {

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("login", "name", "birthday", "postDtos");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getUser(id));
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    @GetMapping(path = "/withName")
    public MappingJacksonValue getAllUsersWithName(@RequestParam(value = "contain") String contain) {
        List<UserDto> userDtos = userService.getAllUsersWithName(contain);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("login", "name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userDtos);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> createtUser(@Valid @RequestBody UserDto userDto) {
       User userCreated = userService.createOrUpdateUser(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDto) {
        User userCreated = userService.createOrUpdateUser(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/creates")
    public ResponseEntity createtUsers(@RequestBody String userDtosJson) throws IOException {
        JsonNode rootNode = mapper.readTree(userDtosJson);

        @Valid List<UserDto> userDtos = mapper.readValue(rootNode.toString(), new TypeReference<List<UserDto>>() {});
        userService.createUsers(userDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/{id}/hello")
    public String helloUser(@PathVariable long id) {
        UserDto userDto = userService.getUser(id);

        return messageSource.getMessage("hello.message", null, LocaleContextHolder.getLocale())
                + " " + userDto.getName() + "!";
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getPost(@PathVariable long id) {
        return userService.getUser(id).getPostDtos();
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity createPost(@PathVariable long id, @RequestBody String post) {
        PostDto postCreated = userService.createPost(id, post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{user_id}/posts/{post_id}")
    public ResponseEntity deletePost(@PathVariable("post_id") long post_id) {
        userService.deletePost(post_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
