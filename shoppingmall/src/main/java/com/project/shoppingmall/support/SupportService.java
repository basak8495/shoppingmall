package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerBoardRepository customerBoardRepository;

    @Autowired
    private CustomerCommentRepository customerCommentRepository;

    public void boardSave(CustomerBoardRequestDto customerBoardRequestDto, String username) {

        User user = userRepository.findByUsername(username);

        customerBoardRequestDto.setDeleteYn("Y");
        customerBoardRequestDto.setUser(user);

        CustomerBoard customerBoard = customerBoardRequestDto.dtoToEntity();
        customerBoardRepository.save(customerBoard);
    }

    public CustomerBoardResponseDto getBoard(Long id) {

        CustomerBoard customerBoard = customerBoardRepository.findById(id).orElse(null);

        CustomerBoardResponseDto customerBoardResponseDto = new CustomerBoardResponseDto(customerBoard);

        return customerBoardResponseDto;
    }

    public List<CustomerCommentResponseDto> getComment(Long id) {

        CustomerBoard customerBoard = customerBoardRepository.findById(id).orElse(null);

        List<CustomerComment> customerCommentList = customerCommentRepository.findByCustomerBoard(customerBoard);

        List<CustomerCommentResponseDto> customerCommentResponseDtoList = new ArrayList<>();

        for(CustomerComment customerComment : customerCommentList) {
            CustomerCommentResponseDto customerCommentResponseDto = new CustomerCommentResponseDto(customerComment);
            customerCommentResponseDtoList.add(customerCommentResponseDto);
        }

        return customerCommentResponseDtoList;
    }

    public void commentSave(CustomerCommentRequestDto customerCommentRequestDto, String username) {

        Long id = customerCommentRequestDto.getCustomerBoardId();
        CustomerBoard customerBoard = customerBoardRepository.findById(id).orElse(null);
        User user = userRepository.findByUsername(username);

        customerCommentRequestDto.setCustomerBoard(customerBoard);
        customerCommentRequestDto.setUser(user);

        CustomerComment customerComment = customerCommentRequestDto.dtoToEntity();

        customerCommentRepository.save(customerComment);
    }

    public void deleteBoard(Long id) {
        CustomerBoard customerBoard = customerBoardRepository.findById(id).orElse(null);

        customerBoard.delete("Y");
        customerBoardRepository.save(customerBoard);
    }
}
