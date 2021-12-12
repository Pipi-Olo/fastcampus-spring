package com.pipiolo.getinline.service;

import com.pipiolo.getinline.constant.ErrorCode;
import com.pipiolo.getinline.dto.PlaceDTO;
import com.pipiolo.getinline.repository.PlaceRepository;
import com.querydsl.core.types.Predicate;
import com.pipiolo.getinline.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Transactional
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public List<PlaceDTO> getPlaces(Predicate predicate) {
        try {
            return StreamSupport.stream(placeRepository.findAll(predicate).spliterator(), false)
                    .map(PlaceDTO::of)
                    .toList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<PlaceDTO> getPlace(Long placeId) {
        try {
            return placeRepository.findById(placeId).map(PlaceDTO::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean upsertPlace(PlaceDTO placeDTO) {
        try {
            if (placeDTO.id() != null) {
                return modifyPlace(placeDTO.id(), placeDTO);
            } else {
                return createPlace(placeDTO);
            }
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createPlace(PlaceDTO placeDTO) {
        try {
            if (placeDTO == null) {
                return false;
            }

            placeRepository.save(placeDTO.toEntity());
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyPlace(Long placeId, PlaceDTO DTO) {
        try {
            if (placeId == null || DTO == null) {
                return false;
            }

            placeRepository.findById(placeId)
                    .ifPresent(place -> placeRepository.save(DTO.updateEntity(place)));

            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removePlace(Long placeId) {
        try {
            if (placeId == null) {
                return false;
            }

            placeRepository.deleteById(placeId);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

}