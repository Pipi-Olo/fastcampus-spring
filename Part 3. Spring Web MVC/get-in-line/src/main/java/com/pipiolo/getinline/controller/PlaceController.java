package com.pipiolo.getinline.controller;

import com.pipiolo.getinline.constant.EventStatus;
import com.pipiolo.getinline.constant.PlaceType;
import com.pipiolo.getinline.dto.EventResponse;
import com.pipiolo.getinline.dto.PlaceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/places")
@Controller
public class PlaceController {

    @GetMapping
    public ModelAndView places() {
        Map<String, Object> map = new HashMap<>();

        // TODO : 임시 데이터, 추후 삭제 예정
        map.put("places", List.of(
                PlaceResponse.of(
                        PlaceType.COMMON,
                        "placeName",
                        "address",
                        "010-0000-0000",
                        10,
                        "testMemo"
                ),
                PlaceResponse.of(
                        PlaceType.COMMON,
                        "placeName2",
                        "address2",
                        "010-0000-0000",
                        10,
                        "testMemo2")));

        return new ModelAndView("place/index", map);
    }

    @GetMapping("/{placeId}")
    public ModelAndView placeDetail(@PathVariable Integer placeId) {
        Map<String, Object> map = new HashMap<>();

        // TODO : 임시 데이터, 추후 삭제 예정
        map.put("places", PlaceResponse.of(
                        PlaceType.COMMON,
                        "placeName",
                        "address",
                        "010-0000-0000",
                        10,
                        "testMemo")
        );

        return new ModelAndView("place/detail", map);
    }
}
