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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String postOrdersForm(@ModelAttribute OrdersDto ordersDto, Model model, Authentication authentication) {

        String username = authentication.getName();

        Long ordersId = ordersService.ordersSave(username, ordersDto);
        ordersService.ordersGoodsSave(username, ordersId);

        return "redirect:/cart/list";
    }
}