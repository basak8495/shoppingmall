package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerBoardRepository customerBoardRepository;

    public void boardSave(CustomerBoardRequestDto customerBoardRequestDto, String username) {

        User user = userRepository.findByUsername(username);

        customerBoardRequestDto.setUser(user);

        CustomerBoard customerBoard = customerBoardRequestDto.dtoToEntity();
        customerBoardRepository.save(customerBoard);
    }

    public CustomerBoardResonseDto getBoard(Long id) {

        CustomerBoard customerBoard = customerBoardRepository.findById(id).orElse(null);

        CustomerBoardResonseDto customerBoardResonseDto = new CustomerBoardResonseDto(customerBoard);

        return customerBoardResonseDto;
    }
}
