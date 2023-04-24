package com.project.shoppingmall.orders;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.account.UserRepository;
import com.project.shoppingmall.cart.Cart;
import com.project.shoppingmall.cart.CartRepository;
import com.project.shoppingmall.cart.CartResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersGoodsRepository ordersGoodsRepository;

    public Long saveOrders(String username, OrdersRequestDto ordersRequestDto) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String date = sdf.format(c1.getTime());

        User user = userRepository.findByUsername(username);

        ordersRequestDto.setDate(date);
        ordersRequestDto.setUser(user);

        Orders orders = ordersRequestDto.dtoToEntity();

        ordersRepository.save(orders);

        return orders.getId();
    }

    public void ordersGoodsSave(String username, Long ordersId) {

        User user = userRepository.findByUsername(username);
        Orders orders = ordersRepository.findById(ordersId).orElse(null);

        List<Cart> cartList = cartRepository.findByUser(user);

        List<CartResponseDto> cartResponseDtoList = new ArrayList<>();

        for(Cart cart : cartList) {

            CartResponseDto cartResponseDto = entityToDto(cart);
            cartResponseDtoList.add(cartResponseDto);
        }

        for(CartResponseDto cartResponseDto : cartResponseDtoList) {

            OrdersGoods ordersGoods = dtoToEntity(cartResponseDto, orders);
            ordersGoodsRepository.save(ordersGoods);
            cartRepository.deleteById(cartResponseDto.getId());
        }

    }

    public CartResponseDto entityToDto(Cart cart) {
        CartResponseDto cartResponseDto = CartResponseDto.builder()
                .id(cart.getId())
                .cartQuantity(cart.getCartQuantity())
                .cartPrice(cart.getCartPrice())
                .goods(cart.getGoods())
                .user(cart.getUser())
                .build();

        return cartResponseDto;
    }

    public OrdersGoods dtoToEntity(CartResponseDto cartResponseDto, Orders orders) {
        OrdersGoods ordersGoods = OrdersGoods.builder()
                .ordersQuantity(cartResponseDto.getCartQuantity())
                .ordersPrice(cartResponseDto.getCartPrice())
                .state("주문대기")
                .claim("-")
                .goods(cartResponseDto.getGoods())
                .orders(orders)
                .build();

        return ordersGoods;
    }

    public List<OrdersResponseDto> getOrdersList(String username) {
        User user = userRepository.findByUsername(username);

        List<Orders> ordersList = ordersRepository.findByUser(user);

        List<OrdersResponseDto> ordersResponseDtoList = new ArrayList<>();

        for(Orders orders : ordersList) {
            OrdersResponseDto ordersResponseDto = new OrdersResponseDto(orders);
            ordersResponseDtoList.add(ordersResponseDto);
        }

        return ordersResponseDtoList;
    }

    public List<OrdersGoodsResponseDto> getOrdersGoodsList(String username) {
        User user = userRepository.findByUsername(username);

        List<Orders> ordersList = ordersRepository.findByUser(user);

        List<OrdersGoodsResponseDto> ordersGoodsResponseDtoList = new ArrayList<>();

        for(Orders orders : ordersList) {
            List<OrdersGoods> ordersGoodsList = ordersGoodsRepository.findByOrders(orders);
            for(OrdersGoods ordersGoods : ordersGoodsList) {
                OrdersGoodsResponseDto ordersGoodsResponseDto = new OrdersGoodsResponseDto(ordersGoods);
                ordersGoodsResponseDtoList.add(ordersGoodsResponseDto);
            }
        }
        return ordersGoodsResponseDtoList;
    }

    public List<OrdersGoodsResponseDto> getOrdersGoodsAllList() {
        List<OrdersGoods> ordersGoodsList = ordersGoodsRepository.findAll();

        List<OrdersGoodsResponseDto> ordersGoodsResponseDtoList = new ArrayList<>();

        for(OrdersGoods ordersGoods : ordersGoodsList) {
            OrdersGoodsResponseDto ordersGoodsResponseDto = new OrdersGoodsResponseDto(ordersGoods);
            ordersGoodsResponseDtoList.add(ordersGoodsResponseDto);
        }
        return ordersGoodsResponseDtoList;
    }

    public void modifyOrdersGoods(OrdersGoodsRequestDto ordersGoodsRequestDto) {
        OrdersGoods ordersGoods = ordersGoodsRequestDto.dtoToEntity();
        ordersGoodsRepository.save(ordersGoods);
    }

}
