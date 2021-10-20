package com.pipiolo.getinline.service;

import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.dto.PlaceDTO;
import com.pipiolo.getinline.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author : moon
 */

@RequiredArgsConstructor
@Service
public class PlaceService {
    
    private final PlaceRepository placeRepository;
    
    public List<PlaceDTO> getPlaces(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber
    ) {
        return placeRepository.findPlaces(
                placeType,
                placeName,
                address,
                phoneNumber
        );
    }
    
    public Optional<PlaceDTO> getPlace(Long placeId) {
        return placeRepository.findPlace(placeId);
    }

    public boolean createPlace(PlaceDTO placeDTO) {
        return placeRepository.insertPlace(placeDTO);
    }

    public boolean modifyPlace(Long placeId, PlaceDTO placeDTO) {
        return placeRepository.updatePlace(placeId, placeDTO);
    }

    public boolean removePlace(Long placeId) {
        return placeRepository.deletePlace(placeId);
    }
}
