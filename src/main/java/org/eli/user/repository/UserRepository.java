package org.eli.user.repository;
import org.eli.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends  JpaRepository <User, Long> {
    User findUserByUserName (String username);

}