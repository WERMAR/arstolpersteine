package com.thws.ar.stolpersteine.backend.db.repositories;

import com.thws.ar.stolpersteine.backend.db.entity.OverpassStolperstein;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverpassStolpersteinRepository extends JpaRepository<OverpassStolperstein, Long> {
}
