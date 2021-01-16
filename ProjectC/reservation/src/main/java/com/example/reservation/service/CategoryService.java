package com.example.reservation.service;

import com.example.reservation.domain.Category;
import com.example.reservation.dto.CategoryDto;
import com.example.reservation.repository.CategoryRepository;
import com.example.reservation.repository.DisplayInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DisplayInfoRepository displayInfoRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DisplayInfoRepository displayInfoRepository) {
        this.categoryRepository = categoryRepository;
        this.displayInfoRepository = displayInfoRepository;
    }

    public List<CategoryDto.Response> findAllCategories() {
        List<Category> categories = categoryRepository.findAllCategories();
        List<CategoryDto.Response> result = new LinkedList<>();
        for (Category category : categories) {
            Long count = displayInfoRepository.getDisplayInfoCountByCategoryId(category.getId());
            CategoryDto.Response response = new CategoryDto.Response(category.getId(), category.getName(), count);
            result.add(response);
        }
        return result;
    }

    public Long getCategoriesSize() {
        return categoryRepository.getCategoriesSize();
    }
}
