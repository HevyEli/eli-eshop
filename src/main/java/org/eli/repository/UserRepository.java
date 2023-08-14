package org.eli.repository;
import org.eli.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends  JpaRepository <User, Long> {
    User findUserByUserName (String username);

}
