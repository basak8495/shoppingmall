package com.project.shoppingmall.goods;

import com.project.shoppingmall.product.Product;
import com.project.shoppingmall.product_option.ProductColor;
import com.project.shoppingmall.product_option.ProductColorRepository;
import com.project.shoppingmall.product_option.ProductSize;
import com.project.shoppingmall.product_option.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    public Goods save(Product product, String color, String size, Integer stock, GoodsRequestDto goodsRequestDto) {

        goodsRequestDto.setStock(stock);

        goodsRequestDto.setProduct(product);

        ProductColor productColor = productColorRepository.findByColor(color);
        goodsRequestDto.setProductColor(productColor);

        ProductSize productSize = productSizeRepository.findBySize(size);
        goodsRequestDto.setProductSize(productSize);

        Goods goods = goodsRequestDto.toEntity();

        return goodsRepository.save(goods);
    }
}
