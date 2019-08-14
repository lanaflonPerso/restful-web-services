package org.munozy.rest.webservices.restfulwebservices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.munozy.rest.webservices.restfulwebservices.domain.User;
import org.munozy.rest.webservices.restfulwebservices.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "posts", ignore = true)
    User toUser(UserDto userDto);

    @Mapping(target = "postDtos",
            expression = ("java(postMapper.toPostDtoList(user.getPosts()))"))
    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

    List<User> toUserList(List<UserDto> userUtos);
}
