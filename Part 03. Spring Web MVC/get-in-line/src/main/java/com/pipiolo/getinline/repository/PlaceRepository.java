package com.pipiolo.getinline.repository;

import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.domain.Event;
import com.pipiolo.getinline.domain.Place;
import com.pipiolo.getinline.dto.PlaceDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    default List<PlaceDTO> findPlaces(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber
    ) {
        return List.of();
    }

    default Optional<PlaceDTO> findPlace(Long placeId) {
        return Optional.empty();
    }

    default boolean insertPlace(PlaceDTO placeDTO) {
        return false;
    }

    default boolean updatePlace(Long placeId, PlaceDTO placeDTO) {
        return false;
    }

    default boolean deletePlace(Long placeId) {
        return false;
    }
}
