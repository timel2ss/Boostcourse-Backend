package com.example.reservation.service;

import com.example.reservation.domain.Category;
import com.example.reservation.dto.CategoryDto;
import com.example.reservation.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto.Response> findAllCategories() {
        List<Category> categories = categoryRepository.findAllCategories();
        List<CategoryDto.Response> result = new LinkedList<>();
        for (Category category : categories) {
            Long count = categoryRepository.getCategoryCountById(category.getId());
            CategoryDto.Response response = new CategoryDto.Response(category.getId(), category.getName(), count);
            result.add(response);
        }
        return result;
    }

    public Long getCategoryCountbyId(long id) {
        return categoryRepository.getCategoryCountById(id);
    }

    public Long getCategoriesSize() {
        return categoryRepository.getCategoriesSize();
    }
}
