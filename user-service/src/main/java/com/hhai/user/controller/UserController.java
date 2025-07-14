package com.hhai.user.controller;


import com.hhai.common.utils.Result;
import com.hhai.user.domain.dto.LoginFormDTO;
import com.hhai.user.domain.vo.UserLoginVO;
import com.hhai.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @ApiOperation("用户登录接口")
    @PostMapping("login")
    public UserLoginVO login(@RequestBody @Validated LoginFormDTO loginFormDTO){
        return userService.login(loginFormDTO);
    }

    @ApiOperation("扣减余额")
    @PostMapping("userBalance")
    public Boolean userBalance(@RequestParam Long userId, @RequestParam BigDecimal balance){
        return userService.deductUserBalance(userId,balance);
    }

//
//    @ApiOperation("扣减余额")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pw", value = "支付密码"),
//            @ApiImplicitParam(name = "amount", value = "支付金额")
//    })
//    @PutMapping("/money/deduct")
//    public void deductMoney(@RequestParam("pw") String pw,@RequestParam("amount") Integer amount){
//        userService.deductMoney(pw, amount);
//    }
//    @PutMapping
//    public void test(@RequestParam("pw") String pw,@RequestParam("amount") Integer amount){
//        System.out.println("11111111111111111111111111111");
//    }
}

