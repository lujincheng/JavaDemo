package com.example.demo.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 请求结果类
 *
 * @param <T> 数据体类型
 */
@Data
public class Result<T> implements Serializable {

    /**
	 * 消息
	 */
    private String message;

    /**
	 * 是否操作成功
	 */
    private boolean success;

    /**
	 * 返回的数据主体（返回的内容）
	 */
    private T data;
    
    /**
     * 设置结果为成功
     * 
     * @param msg 消息
     */
    public void setResultSuccess(String msg) {
        this.message = msg;
        this.success = true;
        this.data = null;
    }

    /**
     * 设置结果为成功
     * 
     * @param msg 消息
     * @param data 数据体
     */
    public void setResultSuccess(String msg, T data) {
        this.message = msg;
        this.success = true;
        this.data = data;
    }

    /**
     * 设置结果为失败
     * 
     * @param msg 消息
     */
    public void setResultFailed(String msg) {
        this.message = msg;
        this.success = false;
        this.data = null;
    }
}
