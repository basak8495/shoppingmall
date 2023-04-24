package com.project.shoppingmall.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("customerBoardList", customerBoardList);

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

        return "redirect:/support/customer_center";
    }

    @GetMapping("/customer_center_board_view")
    public String getCustomerCenterBoardView(Model model, @RequestParam(required = false) Long id) {

        CustomerBoardResponseDto customerBoardResponseDto = supportService.getBoard(id);
        model.addAttribute("customerBoardResponseDto", customerBoardResponseDto);

        List<CustomerCommentResponseDto> customerCommentResponseDtoList = supportService.getComment(id);
        model.addAttribute("customerCommentResponseDtoList", customerCommentResponseDtoList);

        return "support/customer_center_board_view";
    }

    @PostMapping("/customer_center_comment_form")
    @ResponseBody
    public String postCustomerCenterCommentForm(Authentication authentication, @RequestBody CustomerCommentRequestDto customerCommentRequestDto) {

        String username = authentication.getName();
        supportService.commentSave(customerCommentRequestDto, username);

        return "support/customer_center_comment_form";
    }

    @PostMapping("/customer_center_board_delete")
    public String deleteCustomerCenterBoard(@RequestParam(value="checkBoxArr[]", required = false) List<String> checkBoxArr) {

        for(int i=0; i<checkBoxArr.size(); i++) {
            Long id = Long.valueOf(checkBoxArr.get(i));
            supportService.deleteBoard(id);
        }

        return "support/customer_center";
    }


}
