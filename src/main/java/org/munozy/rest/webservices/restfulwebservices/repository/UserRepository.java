package org.munozy.rest.webservices.restfulwebservices.repository;

import org.munozy.rest.webservices.restfulwebservices.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name LIKE %:contain%")
    List<User> findAllWithName(@Param("contain") String contain);

    Optional<User> findByLogin(String login);

    //  Same thing that "findAllWithName(@Param("contain") String contain)"
    List<User> findByNameContaining(String name);
}
