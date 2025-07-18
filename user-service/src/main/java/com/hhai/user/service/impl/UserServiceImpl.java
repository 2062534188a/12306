package com.hhai.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hhai.common.exception.BadRequestException;
import com.hhai.common.exception.ForbiddenException;
import com.hhai.user.config.JwtProperties;
import com.hhai.user.domain.dto.LoginFormDTO;
import com.hhai.user.domain.po.User;
import com.hhai.user.domain.vo.UserLoginVO;
import com.hhai.user.enums.UserStatus;
import com.hhai.user.mapper.UserMapper;
import com.hhai.user.service.IUserService;
import com.hhai.user.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 虎哥
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {
        log.info("测试服务=========");
        // 1.数据校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        // 2.根据用户名或手机号查询
        User user = lambdaQuery().eq(User::getUsername, username).one();
        Assert.notNull(user, "用户名错误");
        // 3.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException("用户被冻结");
        }
        // 4.校验密码
        log.info("加密后的密码{}",passwordEncoder.encode(password));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }
        // 5.生成TOKEN
        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        // 6.封装VO返回
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setToken(token);
        return vo;
    }

    @Override
    public Boolean deductUserBalance(Long userId, BigDecimal balance) {
        return update(null,
                new LambdaUpdateWrapper<User>()
                        .eq(User::getId, userId)
                        .ge(User::getBalance, balance)  // 余额 >= 扣减金额
                        .setSql("balance = balance - " + balance) // 直接扣减
        );
    }

//    @Override
//    public void deductMoney(String pw, Integer totalFee) {
//        log.info("开始扣款");
//        // 1.校验密码
//        User user = getById(UserContext.getUser());
//        if(user == null || !passwordEncoder.matches(pw, user.getPassword())){
//            // 密码错误
//            throw new BizIllegalException("用户密码错误");
//        }
//
//        // 2.尝试扣款
//        try {
//            baseMapper.updateMoney(UserContext.getUser(), totalFee);
//        } catch (Exception e) {
//            throw new RuntimeException("扣款失败，可能是余额不足！", e);
//        }
//        log.info("扣款成功");
//    }
}
