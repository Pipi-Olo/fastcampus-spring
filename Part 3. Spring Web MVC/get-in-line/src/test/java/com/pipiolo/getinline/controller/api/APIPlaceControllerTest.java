package com.pipiolo.getinline.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipiolo.getinline.constant.ErrorCode;
import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.controller.AuthController;
import com.pipiolo.getinline.domain.Place;
import com.pipiolo.getinline.dto.PlaceDTO;
import com.pipiolo.getinline.dto.PlaceRequest;
import com.pipiolo.getinline.service.PlaceService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("REST API 사용으로 API Controller 비활성 상태")
@DisplayName("API Controller - Place")
@WebMvcTest(APIPlaceController.class)
class APIPlaceControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean private PlaceService placeService;

    public APIPlaceControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][GET] 장소 리스트 조회")
    @Test
    void givenNothing_whenRequestingPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
        // Given
        given(placeService.getPlaces(any(), any(), any(), any())).willReturn(List.of(createPlaceDTO()));

        // When & Then
        mvc.perform(
                get("/api/places")
                        .queryParam("placeType", PlaceType.COMMON.name())
                        .queryParam("placeName", "placeName")
                        .queryParam("address", "address")
                        .queryParam("phoneNumber", "010-0000-0000")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("placeName"))
                .andExpect(jsonPath("$.data[0].address").value("address"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-0000-0000"))
                .andExpect(jsonPath("$.data[0].capacity").value(10))
                .andExpect(jsonPath("$.data[0].memo").value("memo"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(placeService).should().getPlaces(any(), any(), any(), any());
    }

    @DisplayName("[API][POST] 장소 생성")
    @Test
    void givenPlace_whenCreatingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        PlaceRequest placeRequest = PlaceRequest.of(
                1L,
                PlaceType.COMMON,
                "placeName",
                "address",
                "010-0000-0000",
                10,
                "memo"
        );

        given(placeService.createPlace(any())).willReturn(true);

        // When & Then
        mvc.perform(
                post("/api/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(placeRequest))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        verify(placeService).createPlace(any());
    }

    @DisplayName("[API][GET] 단일 장소 조회 - 장소 있는 경우")
    @Test
    void givenPlaceAndItsId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
        // Given
        Long placeId = 1L;
        given(placeService.getPlace(placeId)).willReturn(Optional.of(createPlaceDTO()));

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data.placeName").value("placeName"))
                .andExpect(jsonPath("$.data.address").value("address"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-0000-0000"))
                .andExpect(jsonPath("$.data.capacity").value(10))
                .andExpect(jsonPath("$.data.memo").value("memo"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        verify(placeService).getPlace(placeId);
    }

    @DisplayName("[API][GET] 단일 장소 조회 - 장소 없는 경우")
    @Test
    void givenPlaceId_whenRequestingPlace_thenReturnsEmptyInStandardResponse() throws Exception {
        // Given
        Long placeId = 2L;
        given(placeService.getPlace(placeId)).willReturn(Optional.empty());

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        verify(placeService).getPlace(placeId);
    }

    @DisplayName("[API][PUT] 장소 변경")
    @Test
    void givenPlace_whenModifyingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        Long placeId = 1L;
        PlaceRequest placeRequest = PlaceRequest.of(
                null,
                PlaceType.COMMON,
                "testName2",
                "testAddr2",
                "010-0000-0000",
                30,
                "testMemo2"
        );

        given(placeService.modifyPlace(eq(placeId), any())).willReturn(true);

        // When & Then
        mvc.perform(
                put("/api/places/" + placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(placeRequest))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        verify(placeService).modifyPlace(eq(placeId), any());
    }

    @DisplayName("[API][DELETE] 장소 삭제")
    @Test
    void givenPlaceId_whenDeletingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long placeId = 1L;
        given(placeService.removePlace(placeId)).willReturn(true);

        // When & Then
        mvc.perform(delete("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    private PlaceDTO createPlaceDTO() {
        return PlaceDTO.of(
                PlaceType.COMMON,
                "placeName",
                "address",
                "010-0000-0000",
                10,
                "memo"
        );
    }
}