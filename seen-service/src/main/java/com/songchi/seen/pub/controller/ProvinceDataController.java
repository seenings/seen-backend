package com.songchi.seen.pub.controller;

import com.songchi.seen.address.http.HttpProvinceService;
import com.songchi.seen.common.model.CascaderString;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 省份 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-05-23
 */
@RestController
@RequestMapping(PublicConstant.PUBLIC + "pub/province")
public class ProvinceDataController {

    @Resource
    private HttpProvinceService httpProvinceService;

    @GetMapping("to-province-and-city")
    public R<List<CascaderString>> toProvinceAndCity() {
        return ResUtils.ok(httpProvinceService.toProvinceAndCity());
    }
}
