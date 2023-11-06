package com.yin.pddserver.common.exceptions;

import com.yin.pddserver.common.store.ThreadStore;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录异常信息
 *
 * @author yin
 */
@Getter
@Setter
public class LoginException extends BaseException {

    private String ip;

    private String message;

    public LoginException() {
    }

    public LoginException(String message) {
        this.message = message;
        this.ip = ThreadStore.getIp();
    }

    public LoginException(String ip, String message) {
        this.message = message;
        this.ip = ip;
    }

}
