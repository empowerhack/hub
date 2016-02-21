package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUid(@Param("uid") String uid);

    @Query("SELECT u FROM User u WHERE u.name <> ''")
    Iterable<User> findAll();

    @Query("SELECT u FROM User u WHERE u.isPrivate = false AND u.name <> ''")
    Iterable<User> findAllPublic();
}
