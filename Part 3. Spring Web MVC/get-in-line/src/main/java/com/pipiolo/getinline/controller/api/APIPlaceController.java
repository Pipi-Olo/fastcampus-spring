package com.pipiolo.getinline.controller.api;

import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.dto.APIDataResponse;
import com.pipiolo.getinline.dto.PlaceDTO;
import com.pipiolo.getinline.dto.PlaceRequest;
import com.pipiolo.getinline.dto.PlaceResponse;
import com.pipiolo.getinline.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class APIPlaceController {

    private final PlaceService placeService;

    @GetMapping("/places")
    public APIDataResponse<List<PlaceResponse>> getPlaces(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber
    ) {
        List<PlaceResponse> response = placeService
                .getPlaces(placeType, placeName, address, phoneNumber)
                .stream().map(PlaceResponse::from).toList();

        return APIDataResponse.of(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public APIDataResponse<String> createPlace(@RequestBody PlaceRequest placeRequest) {
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
