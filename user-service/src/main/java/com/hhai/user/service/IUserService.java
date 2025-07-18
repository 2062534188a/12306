package com.hhai.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhai.user.domain.dto.LoginFormDTO;
import com.hhai.user.domain.po.User;
import com.hhai.user.domain.vo.UserLoginVO;

import java.math.BigDecimal;

public interface IUserService extends IService<User> {
     UserLoginVO login(LoginFormDTO loginFormDTO);

     Boolean deductUserBalance(Long userId, BigDecimal balance);
}
