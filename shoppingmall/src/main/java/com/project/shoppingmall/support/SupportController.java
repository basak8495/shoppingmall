package com.project.shoppingmall.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private CustomerBoardRepository customerBoardRepository;

    @Autowired
    private SupportService supportService;

    @GetMapping("/customer_center")
    public String getCustomerCenter(Model model) {

        List<CustomerBoard> customerBoardList = customerBoardRepository.findAll();
        model.addAttribute("customerBoard", customerBoardList);

        return "support/customer_center";
    }

    @GetMapping("/customer_center_board_form")
    public String getCustomerCenterBoardForm(CustomerBoardRequestDto customerBoardRequestDto) {

        return "support/customer_center_board_form";
    }

    @PostMapping("/customer_center_board_form")
    public String postCustomerCenterBoardForm(Authentication authentication, CustomerBoardRequestDto customerBoardRequestDto) {

        String username = authentication.getName();

        supportService.boardSave(customerBoardRequestDto, username);

        return "support/customer_center";
    }

    @GetMapping("/customer_center_board_view")
    public String getCustomerCenterBoardView(Model model, @RequestParam(required = false) Long id) {

        CustomerBoardResonseDto customerBoardResonseDto = supportService.getBoard(id);
        model.addAttribute("customerBoardResonseDto", customerBoardResonseDto);

        return "support/customer_center_board_view";
    }
}
