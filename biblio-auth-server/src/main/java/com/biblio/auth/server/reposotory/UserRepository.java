package com.biblio.auth.server.reposotory;




import com.biblio.auth.server.User;
import java.time.ZonedDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {




    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneById(Long id);

    
    User findByLogin(String login);
    
    @Query(value = "select distinct user from User user join fetch user.roles",
        countQuery = "select count(user) from User user")
    Page<User> findAllWithAuthorities(Pageable pageable);

    @Override
    void delete(User t);

}