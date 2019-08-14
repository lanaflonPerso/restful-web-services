package org.munozy.rest.webservices.restfulwebservices.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    long id;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "name", length = 150, nullable = false)
    String name;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "login", length = 150, nullable = false, unique = true)
    String login;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    LocalDate birthday;

    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
    List<Post> posts;
}
