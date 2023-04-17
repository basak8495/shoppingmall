package com.project.shoppingmall.cart;

import com.project.shoppingmall.ProductImage.ProductMainImage;
import com.project.shoppingmall.ProductImage.ProductMainImageDto;
import com.project.shoppingmall.ProductImage.ProductMainImageRepository;
import com.project.shoppingmall.account.User;
import com.project.shoppingmall.account.UserRepository;
import com.project.shoppingmall.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMainImageRepository productMainImageRepository;

    public Cart save(String username, CartRequestDto cartRequestDto) {

        User user = userRepository.findByUsername(username);

        int productPrice = cartRequestDto.getGoods().getProduct().getPrice();
        int cartQuantity = cartRequestDto.getCartQuantity();
        int cartPrice = productPrice * cartQuantity;

        cartRequestDto.setCartPrice(cartPrice);
        cartRequestDto.setUser(user);

        Cart cart = cartRequestDto.toEntity();

        return cartRepository.save(cart);
    }

    public List<CartResponseDto> getList(String username) {

        User user = userRepository.findByUsername(username);

        List<Cart> cartList = cartRepository.findByUser(user);

        List<CartResponseDto> cartResponseDtoList = new ArrayList<>();

        for(Cart cart : cartList) {
            Long id = cart.getGoods().getProduct().getId();
            Product product = productRepository.findById(id).orElse(null);

            List<ProductMainImage> productMainImageList = productMainImageRepository.findByProduct(product);

            CartResponseDto cartResponseDto = entityToDto(cart, productMainImageList);
            cartResponseDtoList.add(cartResponseDto);
        }

        return cartResponseDtoList;

    }

    public CartResponseDto entityToDto(Cart cart, List<ProductMainImage> productMainImageList) {
        CartResponseDto cartResponseDto = CartResponseDto.builder()
                .id(cart.getId())
                .cartQuantity(cart.getCartQuantity())
                .cartPrice(cart.getCartPrice())
                .goods(cart.getGoods())
                .user(cart.getUser())
                .build();

        List<ProductMainImageDto> productMainImageDtoList = productMainImageList.stream().map(productMainImage -> {
            return ProductMainImageDto.builder()
                    .path(productMainImage.getPath())
                    .uuid(productMainImage.getUuid())
                    .imgName(productMainImage.getImgName())
                    .build();
        }).collect(Collectors.toList());

        cartResponseDto.setProductMainImageDtoList(productMainImageDtoList);

        return cartResponseDto;
    }

    public int sumPrice(List<CartResponseDto> cartResponseDtoList) {
        int totalPrice = 0;

        for(CartResponseDto cartResponseDto : cartResponseDtoList) {
            totalPrice += cartResponseDto.getCartPrice();
        }

        return totalPrice;
    }

    public void modifyOrdersQuantity(CartRequestDto cartRequestDto) {

        Long cartId = cartRequestDto.getId();
        Cart cart = cartRepository.findById(cartId).orElse(null);
        int productPrice = cart.getGoods().getProduct().getPrice();
        int cartQuantity = cartRequestDto.getCartQuantity();
        int cartPrice = productPrice * cartQuantity;

        cartRequestDto.setCartPrice(cartPrice);
        cartRequestDto.setGoods(cart.getGoods());
        cartRequestDto.setUser(cart.getUser());

        Cart saveCart = cartRequestDto.toEntity();

        cartRepository.save(saveCart);
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
