package org.munozy.rest.webservices.restfulwebservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "All details about the PostDto.")
public class PostDto {
    @ApiModelProperty(notes = "This property must be ignored. " +
            "Id is generated automatically and it is impossible to update a post.")
    long id;

    @NotNull
    @Size(min = 1, max = 600)
    @ApiModelProperty(notes = "Post should have between 1 and 600 characters.")
    String post;

    @ApiModelProperty(notes = "This property must be ignored. " +
            "Timestamp is generated automatically and it is impossible to update a post.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a")
    LocalDateTime timestamp;
}
