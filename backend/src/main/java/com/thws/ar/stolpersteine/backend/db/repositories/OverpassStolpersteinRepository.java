package com.thws.ar.stolpersteine.backend.db.repositories;

import com.thws.ar.stolpersteine.backend.db.entity.OverpassStolperstein;
import com.thws.ar.stolpersteine.backend.db.entity.Stolperstein;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverpassStolpersteinRepository extends JpaRepository<OverpassStolperstein, Long> {

    @Query(value = "SELECT * FROM overpass_stolperstein AS entity" +
            " WHERE ST_DWithin(ST_MakePoint(entity.lng, entity.lat), ST_MakePoint(:user_lng, :user_lat),:radius)", nativeQuery = true)
    List<OverpassStolperstein> findByCurrentLocationInRadius(@Param("user_lng") float userLng, @Param("user_lat") float user_lat, @Param("radius") double radius);
}
