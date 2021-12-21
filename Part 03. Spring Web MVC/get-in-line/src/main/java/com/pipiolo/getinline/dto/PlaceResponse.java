package com.pipiolo.getinline.dto;

import com.pipiolo.getinline.constant.PlaceType;

public record PlaceResponse(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {

    public static PlaceResponse of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo
        );
    }

    public static PlaceResponse from(PlaceDTO placeDTO) {
        if (placeDTO == null)
            return null;

        return new PlaceResponse(
                placeDTO.placeType(),
                placeDTO.placeName(),
                placeDTO.address(),
                placeDTO.phoneNumber(),
                placeDTO.capacity(),
                placeDTO.memo()
        );
    }
}
