package com.pipiolo.getinline.repository;

import com.pipiolo.getinline.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
