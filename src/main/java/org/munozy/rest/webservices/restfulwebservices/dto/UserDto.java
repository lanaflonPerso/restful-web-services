package org.munozy.rest.webservices.restfulwebservices.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "All details about the UserDto.")
@JsonFilter("UserFilter")
public class UserDto {
    @ApiModelProperty(notes = "This property must be ignored. Id is generated automatically")
    @JsonIgnore
    long id;

    @NotNull
    @Size(min = 2, max = 150)
    @ApiModelProperty(notes = "Name should have between 2 and 150 characters.")
    String name;

    @NotNull
    @Size(min = 2, max = 150)
    @ApiModelProperty(notes = "Login should have between 2 and 150 characters.")
    String login;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "Birth date should be in the past and respect this format 'yyyy-MM-dd'.")
    LocalDate birthday;

    @ApiModelProperty(notes = "List of posts")
    List<PostDto> postDtos;
}
