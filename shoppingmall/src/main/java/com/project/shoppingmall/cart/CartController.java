package com.project.shoppingmall.cart;

import com.project.shoppingmall.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String postCartAdd(@ModelAttribute CartRequestDto cartRequestDto, Authentication authentication) {

        String username = authentication.getName();

        cartService.save(username, cartRequestDto);

        Long productId = cartRequestDto.getGoods().getProduct().getId();

        return "redirect:/product/detail?id="+productId;
    }

    @PostMapping("/add2")
    public String postCartAdd2(@ModelAttribute CartRequestDto cartRequestDto, Authentication authentication) {

        String username = authentication.getName();

        cartService.save(username, cartRequestDto);

        return "redirect:/cart/list";
    }

    @GetMapping("/list")
    public String getCartList(Model model, Authentication authentication) {

        String username = authentication.getName();

        List<CartResponseDto> cartResponseDtoList = cartService.getList(username);
        model.addAttribute("cartResponseDtoList", cartResponseDtoList);

        int totalPrice = cartService.sumPrice(cartResponseDtoList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/list";
    }

    @PutMapping("/modifyOrdersQuantity")
    @ResponseBody
    public void putCartModifyOrdersQuantity(@RequestBody CartRequestDto cartRequestDto) {
        cartService.modifyOrdersQuantity(cartRequestDto);
    }

    @DeleteMapping("/deleteCart")
    @ResponseBody
    public void deleteCart(@RequestParam("cartId") Long cartId) {
        cartService.deleteCart(cartId);
    }
}
