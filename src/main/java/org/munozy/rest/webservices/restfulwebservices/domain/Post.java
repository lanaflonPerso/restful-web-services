package org.munozy.rest.webservices.restfulwebservices.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue
    long id;

    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "post", length = 600, nullable = false)
    String post;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
    LocalDateTime timestamp;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
}
