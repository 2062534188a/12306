package com.hhai.common.utils;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 通用返回结果封装
 * @param <T> 数据类型
 */
@Getter
@Data
public class Result<T> implements Serializable {
    // ------------ Getter ------------
    private boolean success; // 是否成功
    private String code;    // 状态码（如 "SUCCESS"、"NO_TICKET"）
    private String message; // 提示信息
    private T data;         // 返回数据

    // 私有构造方法（推荐使用静态工厂方法）
    private Result(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ------------ 静态工厂方法 ------------
    public static <T> Result<T> success(T data) {
        return new Result<>(true, "SUCCESS", "操作成功", data);
    }
    public static <T> Result<T> success(String message,T data) {
        return new Result<>(true, "SUCCESS", message, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(false, code, message, null);
    }

    // ------------ 链式调用支持 ------------
    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> code(String code) {
        this.code = code;
        return this;
    }


}