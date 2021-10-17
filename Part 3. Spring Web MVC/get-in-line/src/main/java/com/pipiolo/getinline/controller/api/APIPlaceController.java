package com.pipiolo.getinline.controller.api;

import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.dto.APIDataResponse;
import com.pipiolo.getinline.dto.PlaceDTO;
import com.pipiolo.getinline.dto.PlaceRequest;
import com.pipiolo.getinline.dto.PlaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceResponse>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceResponse.of(
                PlaceType.COMMON,
                "testName",
                "testAddr",
                "010-0000-0000",
                30,
                "testMemo"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public APIDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceResponse> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L))
            return APIDataResponse.empty();

        return APIDataResponse.of(PlaceResponse.of(
                PlaceType.COMMON,
                "testName",
                "testAddr",
                "010-0000-0000",
                30,
                "testMemo"
        ));
    }

    @PutMapping("/places/{placeId}")
    public APIDataResponse<Void> modifyPlace(
            @PathVariable Long placeId,
            @RequestBody PlaceRequest placeRequest
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return APIDataResponse.empty();
    }
}
