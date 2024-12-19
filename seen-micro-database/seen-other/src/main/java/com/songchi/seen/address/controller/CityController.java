package com.songchi.seen.address.controller;

import com.songchi.seen.address.http.HttpCityService;
import com.songchi.seen.address.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * CityController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@AllArgsConstructor
@RequestMapping(FEIGN_VERSION + "address/city")
public class CityController implements HttpCityService {

    private CityService cityService;

    @Override
    @PostMapping("id-to-name")
    public Map<Integer, String> idToName(@RequestBody Set<Integer> ids) {
        return cityService.idToName(ids);
    }
}
