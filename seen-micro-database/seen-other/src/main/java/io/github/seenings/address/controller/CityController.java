package io.github.seenings.address.controller;

import io.github.seenings.address.http.HttpCityService;
import io.github.seenings.address.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * 城市
 */
@RestController
@AllArgsConstructor
public class CityController implements HttpCityService {

    private CityService cityService;

    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {
        return cityService.idToName(ids);
    }
}
