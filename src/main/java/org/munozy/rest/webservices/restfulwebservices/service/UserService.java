package org.munozy.rest.webservices.restfulwebservices.service;

import lombok.RequiredArgsConstructor;
import org.munozy.rest.webservices.restfulwebservices.domain.Post;
import org.munozy.rest.webservices.restfulwebservices.domain.User;
import org.munozy.rest.webservices.restfulwebservices.dto.PostDto;
import org.munozy.rest.webservices.restfulwebservices.dto.UserDto;
import org.munozy.rest.webservices.restfulwebservices.exception.ResourceNotFoundException;
import org.munozy.rest.webservices.restfulwebservices.mapper.PostMapper;
import org.munozy.rest.webservices.restfulwebservices.mapper.UserMapper;
import org.munozy.rest.webservices.restfulwebservices.repository.PostRepository;
import org.munozy.rest.webservices.restfulwebservices.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PostMapper postMapper;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public UserDto getUser(long id) {
       User user =  userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException(String.format("It isn't possible to retrieve user id %d, because it doesn't exist.", id)));
        return userMapper.toUserDto(user);
    }

    public  List<UserDto> getAllUsers() {
        return userMapper.toUserDtoList(userRepository.findAll(Sort.by("name")));
    }

    public  List<UserDto> getAllUsersWithName(String contain) {
        return userMapper.toUserDtoList(userRepository.findAllWithName(contain));
    }

    public User createOrUpdateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());
        if (optionalUser.isPresent()) {
            // Update
            User user = optionalUser.get();

            user.setBirthday(userDto.getBirthday());
            user.setName(userDto.getName());

            return userRepository.save(user);
        }
        // Create
        return userRepository.save(userMapper.toUser(userDto));
    }

    public void createUsers(List<UserDto> userDtos) {
        userRepository.saveAll(userMapper.toUserList(userDtos));
    }

    public void deleteUser(long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(String.format("It isn't possible to delete user with id %d, because it doesn't exist.", id));
        }
    }

    public PostDto createPost(long user_id, String message) {
        User user =  userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("It isn't possible to retrieve user id %d, because it doesn't exist.", user_id)));

        Post post = postRepository.save(Post.builder().post(message).user(user).timestamp(LocalDateTime.now()).build());
        return postMapper.toPostDto(post);
    }

    public void deletePost(long id) {
        try {
            postRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(String.format("It isn't possible to delete post with id %d, because it doesn't exist.", id));
        }
    }
}
