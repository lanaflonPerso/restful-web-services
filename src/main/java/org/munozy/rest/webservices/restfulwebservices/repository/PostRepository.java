package org.munozy.rest.webservices.restfulwebservices.repository;

import org.munozy.rest.webservices.restfulwebservices.domain.Post;
import org.munozy.rest.webservices.restfulwebservices.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long>, JpaRepository<Post, Long> {

}
