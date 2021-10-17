package com.pipiolo.getinline.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipiolo.getinline.constant.ErrorCode;
import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.controller.AuthController;
import com.pipiolo.getinline.domain.Place;
import com.pipiolo.getinline.dto.PlaceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIPlaceController.class)
class APIPlaceControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

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

        // When & Then
        mvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("testName"))
                .andExpect(jsonPath("$.data[0].address").value("testAddr"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-0000-0000"))
                .andExpect(jsonPath("$.data[0].capacity").value(30))
                .andExpect(jsonPath("$.data[0].memo").value("testMemo"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][POST] 장소 생성")
    @Test
    void givenPlace_whenCreatingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        PlaceRequest placeRequest = PlaceRequest.of(
                null,
                PlaceType.COMMON,
                "testName",
                "testAddr",
                "010-0000-0000",
                30,
                "testMemo"
        );

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
    }

    @DisplayName("[API][GET] 단일 장소 조회 - 장소 있는 경우")
    @Test
    void givenPlaceAndItsId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
        // Given
        int placeId = 1;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data.placeName").value("testName"))
                .andExpect(jsonPath("$.data.address").value("testAddr"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-0000-0000"))
                .andExpect(jsonPath("$.data.capacity").value(30))
                .andExpect(jsonPath("$.data.memo").value("testMemo"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 단일 장소 조회 - 장소 없는 경우")
    @Test
    void givenPlaceId_whenRequestingPlace_thenReturnsEmptyInStandardResponse() throws Exception {
        // Given
        Long placeId = 2L;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
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
    }

    @DisplayName("[API][DELETE] 장소 삭제")
    @Test
    void givenPlaceId_whenDeletingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long placeId = 1L;

        // When & Then
        mvc.perform(delete("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }
}