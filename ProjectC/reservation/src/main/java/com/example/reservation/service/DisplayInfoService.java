package com.example.reservation.service;

import com.example.reservation.domain.*;
import com.example.reservation.dto.DisplayInfoDto;
import com.example.reservation.dto.DisplayInfoImageDto;
import com.example.reservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class DisplayInfoService {
    private final DisplayInfoRepository displayInfoRepository;
    private final DisplayInfoImageRepository displayInfoImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileInfoRepository fileInfoRepository;

    @Autowired
    public DisplayInfoService(DisplayInfoRepository displayInfoRepository, DisplayInfoImageRepository displayInfoImageRepository, ProductRepository productRepository, CategoryRepository categoryRepository, FileInfoRepository fileInfoRepository) {
        this.displayInfoRepository = displayInfoRepository;
        this.displayInfoImageRepository = displayInfoImageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileInfoRepository = fileInfoRepository;
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

    public List<DisplayInfoDto.Response> findAllProductInfos(int start) {
        List<Product> products = productRepository.findAll(start);
        List<DisplayInfoDto.Response> result = new LinkedList<>();
        for(Product product : products) {
            List<DisplayInfo> displayInfos = displayInfoRepository.findByProductId(product.getId());
            for(DisplayInfo displayInfo: displayInfos) {
                Long fileId = displayInfoImageRepository.findFileIdByDisplayInfoId(displayInfo.getId());
                Category category = categoryRepository.findById(product.getCategory_id());
                DisplayInfoDto.Response response = new DisplayInfoDto.Response(product.getId(), category.getId(), displayInfo.getId(), category.getName(), product.getDescription(), product.getContent(), product.getEvent(), displayInfo.getOpening_hours(), displayInfo.getPlace_name(), displayInfo.getPlace_lot(), displayInfo.getPlace_street(), displayInfo.getTel(), displayInfo.getHomepage(), displayInfo.getEmail(), displayInfo.getCreate_date(), displayInfo.getModify_date(), fileId);
                result.add(response);
            }
        }
        return result.subList(start, result.size());
    }

    public DisplayInfoDto.Response findByDisplayId(long displayId) {
        Product product = productRepository.findByDisplayId(displayId);
        DisplayInfo displayInfo = displayInfoRepository.findById(displayId);
        Long fileId = displayInfoImageRepository.findFileIdByDisplayInfoId(displayId);
        Category category = categoryRepository.findById(product.getCategory_id());
        return new DisplayInfoDto.Response(product.getId(), category.getId(), displayId, category.getName(), product.getDescription(), product.getContent(), product.getEvent(), displayInfo.getOpening_hours(), displayInfo.getPlace_name(), displayInfo.getPlace_lot(), displayInfo.getPlace_street(), displayInfo.getTel(), displayInfo.getHomepage(), displayInfo.getEmail(), displayInfo.getCreate_date(), displayInfo.getModify_date(), fileId);
    }

    public DisplayInfoImageDto.Response getDisplayInfoImageByDisplayId(long displayId) {
        DisplayInfoImage displayInfoImage = displayInfoImageRepository.findByDisplayId(displayId);
        FileInfo fileInfo = fileInfoRepository.findById(displayInfoImage.getFile_id());
        return new DisplayInfoImageDto.Response(displayInfoImage.getId(), displayInfoImage.getDisplay_info_id(), displayInfoImage.getFile_id(), fileInfo.getFile_name(), fileInfo.getSave_file_name(), fileInfo.getContent_type(), fileInfo.isDelete_flag(), fileInfo.getCreate_date(), fileInfo.getModify_date());
    }

    public long getDisplayInfoCountByCategoryId(long categoryId) {
        return displayInfoRepository.getDisplayInfoCountByCategoryId(categoryId);
    }

    public long getDisplayInfoTotalCount() {
        return displayInfoRepository.getDisplayInfoTotalCount();
    }
}
