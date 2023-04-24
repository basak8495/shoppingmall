package com.project.shoppingmall.orders;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.account.UserRepository;
import com.project.shoppingmall.cart.CartRepository;
import com.project.shoppingmall.cart.CartResponseDto;
import com.project.shoppingmall.cart.CartService;
import com.project.shoppingmall.goods.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersGoodsRepository ordersGoodsRepository;

    @GetMapping("/form")
    public String getOrdersForm(Model model, Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        long num = new Date().getTime();
        model.addAttribute("num", num);

        List<CartResponseDto> cartResponseDtoList = cartService.getList(username);
        model.addAttribute("cartResponseDtoList", cartResponseDtoList);

        int totalPrice = cartService.sumPrice(cartResponseDtoList);
        model.addAttribute("totalPrice", totalPrice);

        return "orders/form";
    }

    @PostMapping("/form")
    public String postOrdersForm(@ModelAttribute OrdersRequestDto ordersRequestDto, Model model, Authentication authentication) {

        String username = authentication.getName();

        Long ordersId = ordersService.saveOrders(username, ordersRequestDto);
        ordersService.ordersGoodsSave(username, ordersId);

        return "redirect:/cart/list";
    }

    @GetMapping("/list")
    public String getOrdersList(Model model, Authentication authentication) {
        String username = authentication.getName();

        List<OrdersResponseDto> ordersResponseDtoList = ordersService.getOrdersList(username);
        model.addAttribute("ordersResponseDtoList", ordersResponseDtoList);

        List<OrdersGoodsResponseDto> ordersGoodsResponseDtoList = ordersService.getOrdersGoodsList(username);
        model.addAttribute("ordersGoodsResponseDtoList", ordersGoodsResponseDtoList);

        return "orders/list";
    }

    @GetMapping("/all_list")
    public String getOrdersAllList(Model model) {
        List<OrdersGoodsResponseDto> ordersGoodsResponseDtoList = ordersService.getOrdersGoodsAllList();
        model.addAttribute("ordersGoodsResponseDtoList", ordersGoodsResponseDtoList);

        return "orders/all_list";
    }

    @GetMapping("/modify/{ordersGoodsId}")
    public String getOrdersModify(Model model, @PathVariable("ordersGoodsId") Long ordersGooodsId) {
        OrdersGoods ordersGoods = ordersGoodsRepository.findById(ordersGooodsId).orElse(null);
        OrdersGoodsResponseDto ordersGoodsResponseDto = new OrdersGoodsResponseDto(ordersGoods);
        model.addAttribute("ordersGoods", ordersGoodsResponseDto);
        return "orders/modify";
    }

    @PutMapping("/modify")
    public String putOrdersModify(@ModelAttribute OrdersGoodsRequestDto ordersGoodsRequestDto) {
        ordersService.modifyOrdersGoods(ordersGoodsRequestDto);

        return "redirect:/orders/all_list";
    }
}