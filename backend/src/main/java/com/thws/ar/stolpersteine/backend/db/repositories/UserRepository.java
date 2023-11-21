package com.thws.ar.stolpersteine.backend.db.repositories;

import com.thws.ar.stolpersteine.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
