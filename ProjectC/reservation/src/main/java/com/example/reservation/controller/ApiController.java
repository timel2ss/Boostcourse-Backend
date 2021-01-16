package com.example.reservation.controller;

import com.example.reservation.dto.DisplayInfoDto;
import com.example.reservation.service.CategoryService;
import com.example.reservation.service.DisplayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/*")
public class ApiController {
    private final CategoryService categoryService;
    private final DisplayInfoService displayInfoService;

    @Autowired
    public ApiController(CategoryService categoryService, DisplayInfoService displayInfoService) {
        this.categoryService = categoryService;
        this.displayInfoService = displayInfoService;
    }

    @GetMapping("/categories")
    public Map<String, Object> getCategoryList() {
        Map<String, Object> map = new HashMap<>();
        map.put("size", categoryService.getCategoriesSize());
        map.put("items", categoryService.findAllCategories());
        return map;
    }

    @GetMapping("/displayinfos")
    public Map<String, Object> getDisplayInfos(@RequestParam(name = "categoryId", required = false, defaultValue = "0") long categoryId,
                                               @RequestParam(name = "start", required = false) int start,
                                               @RequestParam(name = "productId", required = false, defaultValue = "0") long productId) {
        Map<String, Object> map = new HashMap<>();

        if(productId != 0) {

        }

        long totalCount = 0;

        if(categoryId == 0) {
//            totalCount = //total
        }
        else {
            totalCount = displayInfoService.getTotalSize(categoryId);
        }

        List<DisplayInfoDto.Response> displayInfos = displayInfoService.findAllProductInfosByCategoryId(categoryId, start);
        map.put("totalCount", totalCount);
        map.put("productCount", displayInfos.size());
        map.put("products", displayInfos);
        return map;
    }

    @GetMapping("/displayInfos/{displayId}")
    public Map<String, Object> getDisplayInfoByDisplayId(@PathVariable(name = "displayId") long displayId) {
        return null;
    }

    @GetMapping("/promotions")
    public Map<String, Object> getPromotionInfoList() {
        return null;
    }


}
