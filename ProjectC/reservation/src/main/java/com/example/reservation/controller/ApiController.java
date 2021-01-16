package com.example.reservation.controller;

import com.example.reservation.domain.ProductImage;
import com.example.reservation.dto.DisplayInfoDto;
import com.example.reservation.dto.DisplayInfoImageDto;
import com.example.reservation.dto.ProductImageDto;
import com.example.reservation.service.CategoryService;
import com.example.reservation.service.DisplayInfoService;
import com.example.reservation.service.ProductService;
import com.example.reservation.service.PromotionService;
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
    private final PromotionService promotionService;
    private final ProductService productService;

    @Autowired
    public ApiController(CategoryService categoryService, DisplayInfoService displayInfoService, PromotionService promotionService, ProductService productService) {
        this.categoryService = categoryService;
        this.displayInfoService = displayInfoService;
        this.promotionService = promotionService;
        this.productService = productService;
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
                                               @RequestParam(name = "start", required = false) int start, // TODO paging
                                               @RequestParam(name = "productId", required = false, defaultValue = "0") long productId) {
        Map<String, Object> map = new HashMap<>();

        if(productId != 0) {

        }


        long totalCount;
        List<DisplayInfoDto.Response> displayInfos;
        if(categoryId == 0) {
            totalCount = displayInfoService.getDisplayInfoTotalCount();
            displayInfos = displayInfoService.findAllProductInfos(start);
        }
        else {
            totalCount = displayInfoService.getDisplayInfoCountByCatgoryId(categoryId);
            displayInfos = displayInfoService.findAllProductInfosByCategoryId(categoryId, start);
        }

        map.put("totalCount", totalCount);
        map.put("productCount", displayInfos.size());
        map.put("products", displayInfos);
        return map;
    }

    @GetMapping("/displayInfos/{displayId}")
    public Map<String, Object> getDisplayInfoByDisplayId(@PathVariable(name = "displayId") long displayId) {
        DisplayInfoDto.Response product = displayInfoService.findByDisplayId(displayId);
        ProductImageDto.Response productImage = productService.getImageByDisplayId(displayId);
        DisplayInfoImageDto.Response displayInfoImage = displayInfoService.getDisplayInfoImageByDisplayId(displayId);
        // TODO avgScore, productPrices
        return null;
    }

    @GetMapping("/promotions")
    public Map<String, Object> getPromotionInfoList() {
        Map<String, Object> map = new HashMap<>();
        map.put("size", promotionService.getNumberOfPromotions());
        map.put("items", promotionService.findAll());
        return map;
    }


}
