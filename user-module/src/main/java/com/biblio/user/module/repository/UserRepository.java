package com.biblio.user.module.repository;




import com.biblio.user.module.entity.User;
import java.time.ZonedDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneById(Long id);

    
    User findByLogin(String login);
    
    @Query(value = "select distinct user from User user join fetch user.roles",
        countQuery = "select count(user) from User user")
    Page<User> findAllWithAuthorities(Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.nom LIKE :mot OR u.prenom like :mot OR u.tel LIKE :mot OR u.email LIKE :mot")
    Page<User> findByMoCle(@Param("mot")String mot,Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.nom LIKE :mot OR u.prenom like :mot OR u.tel LIKE :mot OR u.email LIKE :mot")
    List<User> findByMoCle(@Param("mot")String mot);

    @Override
    void delete(User t);

}
