package com.example.reservation.controller;

import com.example.reservation.domain.ProductPrice;
import com.example.reservation.dto.DisplayInfoDto;
import com.example.reservation.dto.DisplayInfoImageDto;
import com.example.reservation.dto.ProductImageDto;
import com.example.reservation.dto.ReservationUserCommentDto;
import com.example.reservation.service.*;
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
    private final ReservationService reservationService;

    @Autowired
    public ApiController(CategoryService categoryService, DisplayInfoService displayInfoService, PromotionService promotionService, ProductService productService, ReservationService reservationService) {
        this.categoryService = categoryService;
        this.displayInfoService = displayInfoService;
        this.promotionService = promotionService;
        this.productService = productService;
        this.reservationService = reservationService;
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
                                               @RequestParam(name = "start", required = false, defaultValue = "0") int start,
                                               @RequestParam(name = "productId", required = false, defaultValue = "0") long productId) {
        Map<String, Object> map = new HashMap<>();

        if(productId != 0) {
            int totalCount = reservationService.getTotalCount(productId);
            List<ReservationUserCommentDto.Response> comments = reservationService.getCommentsByProductId(productId, start);
            int commentCount = comments.size();

            map.put("totalCount", totalCount);
            map.put("commentCount", commentCount);
            map.put("reservationUserComments", comments);
            return map;
        }


        long totalCount;
        List<DisplayInfoDto.Response> displayInfos;
        if(categoryId == 0) {
            totalCount = displayInfoService.getDisplayInfoTotalCount();
            displayInfos = displayInfoService.findAllProductInfos(start);
        }
        else {
            totalCount = displayInfoService.getDisplayInfoCountByCategoryId(categoryId);
            displayInfos = displayInfoService.findAllProductInfosByCategoryId(categoryId, start);
        }

        map.put("totalCount", totalCount);
        map.put("productCount", displayInfos.size());
        map.put("products", displayInfos);
        return map;
    }

    @GetMapping("/displayinfos/{displayId}")
    public Map<String, Object> getDisplayInfoByDisplayId(@PathVariable(name = "displayId") long displayId) {
        DisplayInfoDto.Response product = displayInfoService.findByDisplayId(displayId);
        List<ProductImageDto.Response> productImages = productService.getImageByDisplayId(displayId);
        DisplayInfoImageDto.Response displayInfoImage = displayInfoService.getDisplayInfoImageByDisplayId(displayId);
        int avgScore = reservationService.getAvgScoreByProductId(product.getId());
        List<ProductPrice> priceInfos = productService.getPriceInfoByProductId(product.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("productImages", productImages);
        map.put("displayInfoImages", displayInfoImage);
        map.put("avgScore", avgScore);
        map.put("productPrices", priceInfos);
        return map;
    }

    @GetMapping("/promotions")
    public Map<String, Object> getPromotionInfoList() {
        Map<String, Object> map = new HashMap<>();
        map.put("size", promotionService.getNumberOfPromotions());
        map.put("items", promotionService.findAll());
        return map;
    }


}
