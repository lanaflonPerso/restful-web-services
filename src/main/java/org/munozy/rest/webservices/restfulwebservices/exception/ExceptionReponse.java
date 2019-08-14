package org.munozy.rest.webservices.restfulwebservices.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor(staticName = "of")
public class ExceptionReponse {
    @NonNull
    LocalDate timestamp;
    @NonNull
    String message;
    @NonNull
    private String description;
}
