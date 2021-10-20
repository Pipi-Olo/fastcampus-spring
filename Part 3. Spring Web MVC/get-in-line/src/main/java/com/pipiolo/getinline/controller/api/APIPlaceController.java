package com.pipiolo.getinline.controller.api;

import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.dto.APIDataResponse;
import com.pipiolo.getinline.dto.PlaceRequest;
import com.pipiolo.getinline.dto.PlaceResponse;
import com.pipiolo.getinline.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

//@Validated
@RequiredArgsConstructor
//@RequestMapping("/api")
//@RestController
public class APIPlaceController {

    private final PlaceService placeService;

    @GetMapping("/places")
    public APIDataResponse<List<PlaceResponse>> getPlaces(
            @NotNull PlaceType placeType,
            @NotBlank String placeName,
            @NotBlank String address,
            @NotBlank String phoneNumber
    ) {
        List<PlaceResponse> response = placeService
                .getPlaces(placeType, placeName, address, phoneNumber)
                .stream().map(PlaceResponse::from).toList();

        return APIDataResponse.of(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public APIDataResponse<String> createPlace(@Valid @RequestBody PlaceRequest placeRequest) {
        boolean result = placeService.createPlace(placeRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceResponse> getPlace(@PathVariable Long placeId) {
        PlaceResponse response = PlaceResponse
                .from(placeService.getPlace(placeId).orElse(null));

        return APIDataResponse.of(response);
    }

    @PutMapping("/places/{placeId}")
    public APIDataResponse<String> modifyPlace(
            @PathVariable Long placeId,
            @RequestBody PlaceRequest placeRequest
    ) {
        boolean result = placeService.modifyPlace(placeId, placeRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<String> removePlace(@PathVariable Long placeId) {
        boolean result = placeService.removePlace(placeId);
        return APIDataResponse.of(Boolean.toString(result));
    }
}
