package com.example.reservation.service;

import com.example.reservation.domain.FileInfo;
import com.example.reservation.domain.ProductImage;
import com.example.reservation.domain.ProductPrice;
import com.example.reservation.dto.ProductImageDto;
import com.example.reservation.repository.FileInfoRepository;
import com.example.reservation.repository.ProductImageRepository;
import com.example.reservation.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductImageRepository productImageRepository;
    private final FileInfoRepository fileInfoRepository;
    private final ProductPriceRepository productPriceRepository;

    @Autowired
    public ProductService(ProductImageRepository productImageRepository, FileInfoRepository fileInfoRepository, ProductPriceRepository productPriceRepository) {
        this.productImageRepository = productImageRepository;
        this.fileInfoRepository = fileInfoRepository;
        this.productPriceRepository = productPriceRepository;
    }

    public List<ProductImageDto.Response> getImageByDisplayId(long displayId) {
        List<ProductImage> productImages = productImageRepository.findByDisplayId(displayId);
        List<ProductImageDto.Response> result = new LinkedList<>();
        for(ProductImage productImage : productImages) {
            FileInfo fileInfo = fileInfoRepository.findById(productImage.getFile_id());
            result.add(new ProductImageDto.Response(productImage.getProduct_id(), productImage.getId(), productImage.getType(), productImage.getFile_id(), fileInfo.getFile_name(), fileInfo.getSave_file_name(), fileInfo.getContent_type(), fileInfo.isDelete_flag(), fileInfo.getCreate_date(), fileInfo.getModify_date()));
        }
        return result;
    }

    public List<ProductPrice> getPriceInfoByProductId(long productId) {
        return productPriceRepository.findByProductId(productId);
    }
}
