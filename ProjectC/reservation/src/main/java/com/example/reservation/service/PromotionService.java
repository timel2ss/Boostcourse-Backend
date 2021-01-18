package com.example.reservation.service;

import com.example.reservation.domain.Category;
import com.example.reservation.domain.Product;
import com.example.reservation.domain.Promotion;
import com.example.reservation.dto.PromotionDto;
import com.example.reservation.repository.CategoryRepository;
import com.example.reservation.repository.ProductImageRepository;
import com.example.reservation.repository.ProductRepository;
import com.example.reservation.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository, CategoryRepository categoryRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.promotionRepository = promotionRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public long getNumberOfPromotions() {
        return promotionRepository.getNumberOfPromotions();
    }

    public List<PromotionDto.Response> findAll() {
        List<Promotion> promotions = promotionRepository.findAll();
        List<PromotionDto.Response> result = new LinkedList<>();

        for(Promotion promotion : promotions) {
            Product product = productRepository.findById(promotion.getProduct_id());
            Category category = categoryRepository.findById(product.getCategory_id());
            long fileId = productImageRepository.findFileIdByProductId(product.getId());
            PromotionDto.Response response = new PromotionDto.Response(promotion.getId(), product.getId(), category.getId(), category.getName(), product.getDescription(), fileId);
            result.add(response);
        }
        return result;
    }
}
