package com.example.reservation.service;

import com.example.reservation.domain.Category;
import com.example.reservation.domain.DisplayInfo;
import com.example.reservation.domain.Product;
import com.example.reservation.dto.DisplayInfoDto;
import com.example.reservation.repository.CategoryRepository;
import com.example.reservation.repository.DisplayInfoImageRepository;
import com.example.reservation.repository.DisplayInfoRepository;
import com.example.reservation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DisplayInfoService {
    private final DisplayInfoRepository displayInfoRepository;
    private final DisplayInfoImageRepository displayInfoImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DisplayInfoService(DisplayInfoRepository displayInfoRepository, DisplayInfoImageRepository displayInfoImageRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.displayInfoRepository = displayInfoRepository;
        this.displayInfoImageRepository = displayInfoImageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<DisplayInfoDto.Response> findAllProductInfosByCategoryId(long categoryId, int start) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        Category category = categoryRepository.findById(categoryId);
        List<DisplayInfoDto.Response> result = new LinkedList<>();
        for(Product product : products) {
            List<DisplayInfo> displayInfos = displayInfoRepository.findByProductId(product.getId());
            for(DisplayInfo displayInfo: displayInfos) {
                Long fileId = displayInfoImageRepository.findFileIdByDisplayInfoId(displayInfo.getId());
                DisplayInfoDto.Response response = new DisplayInfoDto.Response(product.getId(), categoryId, displayInfo.getId(), category.getName(), product.getDescription(), product.getContent(), product.getEvent(), displayInfo.getOpening_hours(), displayInfo.getPlace_name(), displayInfo.getPlace_lot(), displayInfo.getPlace_street(), displayInfo.getTel(), displayInfo.getHomepage(), displayInfo.getEmail(), displayInfo.getCreate_date(), displayInfo.getModify_date(), fileId);
                result.add(response);
            }
        }
        return result.subList(start, result.size());
    }

    public long getTotalSize(long categoryId) {
        return displayInfoRepository.getDisplayInfoCountByCategoryId(categoryId);
    }
}
