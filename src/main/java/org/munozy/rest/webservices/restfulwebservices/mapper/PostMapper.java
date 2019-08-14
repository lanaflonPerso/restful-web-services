package org.munozy.rest.webservices.restfulwebservices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.munozy.rest.webservices.restfulwebservices.domain.Post;
import org.munozy.rest.webservices.restfulwebservices.dto.PostDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "user", ignore = true)
    Post toPost(PostDto postDto);

    PostDto toPostDto(Post post);

    List<PostDto> toPostDtoList(List<Post> posts);

    List<Post> toPostList(List<PostDto> postDtos);
}
