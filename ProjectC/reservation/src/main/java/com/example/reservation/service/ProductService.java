package com.example.reservation.service;

import com.example.reservation.domain.FileInfo;
import com.example.reservation.domain.ProductImage;
import com.example.reservation.dto.ProductImageDto;
import com.example.reservation.repository.FileInfoRepository;
import com.example.reservation.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductImageRepository productImageRepository;
    private final FileInfoRepository fileInfoRepository;

    @Autowired
    public ProductService(ProductImageRepository productImageRepository, FileInfoRepository fileInfoRepository) {
        this.productImageRepository = productImageRepository;
        this.fileInfoRepository = fileInfoRepository;
    }

    public ProductImageDto.Response getImageByDisplayId(long displayId) {
        ProductImage productImage = productImageRepository.findByDisplayId(displayId);
        FileInfo fileInfo = fileInfoRepository.findById(productImage.getFile_id());
        return new ProductImageDto.Response(productImage.getProduct_id(), productImage.getId(), productImage.getType(), productImage.getFile_id(), fileInfo.getFile_name(), fileInfo.getSave_file_name(), fileInfo.getContent_type(), fileInfo.isDelete_flag(), fileInfo.getCreate_date(), fileInfo.getModify_date());
    }
}
