package com.pipiolo.getinline.dto;

import com.pipiolo.getinline.constant.PlaceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record PlaceRequest(
        @NotNull @Positive Long id,
        @NotNull PlaceType placeType,
        @NotBlank String placeName,
        @NotBlank String address,
        @NotNull String phoneNumber,
        @NotNull @Positive Integer capacity,
        String memo
) {

    public static PlaceRequest of(
            Long id,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceRequest(
                id,
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo
        );
    }

    public PlaceDTO toDTO() {
        return PlaceDTO.of(
                this.placeType(),
                this.placeName(),
                this.address(),
                this.phoneNumber(),
                this.capacity(),
                this.memo()
        );
    }
}
