package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Integer> {
    //Jpa naming query (method name) (query creation)
    User findByUsername(String username);
}
