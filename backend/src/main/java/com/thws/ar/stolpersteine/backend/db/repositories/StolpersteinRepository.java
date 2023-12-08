package com.thws.ar.stolpersteine.backend.db.repositories;

import com.thws.ar.stolpersteine.backend.db.entity.Stolperstein;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StolpersteinRepository extends JpaRepository<Stolperstein, Long> {
}
