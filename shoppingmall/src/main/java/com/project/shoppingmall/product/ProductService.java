package com.project.shoppingmall.product;

import com.project.shoppingmall.product_image.*;
import com.project.shoppingmall.product_option.ProductColor;
import com.project.shoppingmall.product_option.ProductColorRepository;
import com.project.shoppingmall.product_option.ProductSize;
import com.project.shoppingmall.product_option.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMainImageRepository productMainImageRepository;

    @Autowired
    private ProductDetailImageRepository productDetailImageRepository;

    public ProductColor productColorSave(ProductColor productColor) {

        return productColorRepository.save(productColor);
    }

    public ProductSize productSizeSave(ProductSize productSize) {

        return productSizeRepository.save(productSize);
    }

    @Transactional
    public void productRegister(ProductRequestDto productRequestDto) {

        Map<String, Object> entityMap = dtoToEntity(productRequestDto);
        
        Product product = (Product) entityMap.get("product");
        productRepository.save(product);

        List<ProductMainImage> productMainImageList = (List<ProductMainImage>) entityMap.get("productMainImageList");
        productMainImageList.forEach(productMainImage -> {
            productMainImageRepository.save(productMainImage);
        });

        List<ProductDetailImage> productDetailImageList = (List<ProductDetailImage>) entityMap.get("productDetailImageList");
        productDetailImageList.forEach(productDetailImage -> {
            productDetailImageRepository.save(productDetailImage);
        });
    }

    public Map<String, Object> dtoToEntity(ProductRequestDto productRequestDto) {

        Map<String, Object> entityMap = new HashMap<>();

        Product product = Product.builder()
                .category(productRequestDto.getCategory())
                .subCategoryId(productRequestDto.getSubCategoryId())
                .subCategory(productRequestDto.getSubCategory())
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .build();
        entityMap.put("product", product);

        List<ProductMainImageDto> productMainImageDtoList = productRequestDto.getProductMainImageDtoList();

        if(productMainImageDtoList != null && productMainImageDtoList.size() > 0) {
            List<ProductMainImage> productMainImageList = productMainImageDtoList.stream().map(productMainImageDto -> {
                ProductMainImage productMainImage = ProductMainImage.builder()
                        .path(productMainImageDto.getPath())
                        .uuid(productMainImageDto.getUuid())
                        .imgName(productMainImageDto.getImgName())
                        .product(product)
                        .build();
                return productMainImage;
            }).collect(Collectors.toList());
            entityMap.put("productMainImageList", productMainImageList);
        }

        List<ProductDetailImageDto> productDetailImageDtoList = productRequestDto.getProductDetailImageDtoList();

        if(productDetailImageDtoList != null && productDetailImageDtoList.size() > 0) {
            List<ProductDetailImage> productDetailImageList = productDetailImageDtoList.stream().map(productDetailImageDto -> {
                ProductDetailImage productDetailImage = ProductDetailImage.builder()
                        .path(productDetailImageDto.getPath())
                        .uuid(productDetailImageDto.getUuid())
                        .imgName(productDetailImageDto.getImgName())
                        .product(product)
                        .build();
                return productDetailImage;
            }).collect(Collectors.toList());
            entityMap.put("productDetailImageList", productDetailImageList);
        }

        return entityMap;
    }

    public List<ProductResponseDto> getProductList() {

        List<Product> productList = productRepository.findAll();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : productList) {
            List<ProductMainImage> productMainImageList = productMainImageRepository.findByProduct(product);
            List<ProductDetailImage> productDetailImageList = productDetailImageRepository.findByProduct(product);

            ProductResponseDto productResponseDto = entityToDto(product, productMainImageList, productDetailImageList);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    public List<ProductResponseDto> getSubCategoryProductList(String subCategoryId) {

        List<Product> productList = productRepository.findBySubCategoryId(subCategoryId);

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : productList) {
            List<ProductMainImage> productMainImageList = productMainImageRepository.findByProduct(product);
            List<ProductDetailImage> productDetailImageList = productDetailImageRepository.findByProduct(product);

            ProductResponseDto productResponseDto = entityToDto(product, productMainImageList, productDetailImageList);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    public ProductResponseDto entityToDto(Product product, List<ProductMainImage> productMainImageList, List<ProductDetailImage> productDetailImageList) {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(product.getId())
                .category(product.getCategory())
                .subCategoryId(product.getSubCategoryId())
                .subCategory(product.getSubCategory())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        List<ProductMainImageDto> productMainImageDtoList = productMainImageList.stream().map(productMainImage -> {
            return ProductMainImageDto.builder()
                    .imgName(productMainImage.getImgName())
                    .path(productMainImage.getPath())
                    .uuid(productMainImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        List<ProductDetailImageDto> productDetailImageDtoList = productDetailImageList.stream().map(productDetailImage -> {
            return ProductDetailImageDto.builder()
                    .imgName(productDetailImage.getImgName())
                    .path(productDetailImage.getPath())
                    .uuid(productDetailImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        productResponseDto.setProductMainImageDtoList(productMainImageDtoList);
        productResponseDto.setProductDetailImageDtoList(productDetailImageDtoList);

        return productResponseDto;
    }

    public ProductResponseDto getProduct(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        List<ProductMainImage> productMainImageList = productMainImageRepository.findByProduct(product);
        List<ProductDetailImage> productDetailImageList = productDetailImageRepository.findByProduct(product);

        ProductResponseDto productResponseDto = entityToDto(product, productMainImageList, productDetailImageList);

        return productResponseDto;
    }

}
