package com.yin.pddserver.common.exceptions.handler;

import com.yin.pddserver.common.auth.service.IpLimitService;
import com.yin.pddserver.common.base.vo.out.BaseJson;
import com.yin.pddserver.common.exceptions.BaseException;
import com.yin.pddserver.common.exceptions.LoginException;
import com.yin.pddserver.common.exceptions.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 全局控制器异常处理机制
 *
 * @author yin.weilong
 * @date 2018.11.11
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @Autowired
    private IpLimitService ipLimitService;

    /**
     * 处理非受检异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public BaseJson handleThrowable(Throwable e, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(500);
        e.printStackTrace();
        return BaseJson.getError("系统繁忙，请稍后再试");
    }

    /**
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseJson handle(Exception e, HttpServletResponse response) {
        response.setStatus(500);
        e.printStackTrace();
        return BaseJson.getError(e.getMessage());
    }

    /**
     * spring validate异常捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        if (objectErrors.size() > 0) {
            return BaseJson.getError(objectErrors.get(0).getDefaultMessage());
        }
        return BaseJson.getError(e.getMessage());
    }

    /**
     * 项目异常信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public BaseJson handleBaseException(Exception e, HttpServletResponse response) {
        response.setStatus(500);
        e.printStackTrace();
        return BaseJson.getError(e.getMessage());
    }

    /**
     * 操作级别异常信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MessageException.class)
    @ResponseBody
    public BaseJson handleMessageException(Exception e) {
        return BaseJson.getError(e.getMessage());
    }

    /**
     * 登录异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public BaseJson handleLoginException(LoginException e) {
        ipLimitService.addTimes(e.getIp());
        return BaseJson.getError(e.getMessage());
    }


    /**
     * 主键冲突
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public BaseJson handleDuplicateKeyException(Exception e) {
        log.error("主键冲突：" + e.getMessage(),e);
        return BaseJson.getError("主键冲突：" + e.getMessage());
    }


    /**
     * 唯一索引重复
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public BaseJson handleDataIntegrityViolationException(Exception e) {
        e.printStackTrace();
        return BaseJson.getError("数据已经存在");
    }


}
