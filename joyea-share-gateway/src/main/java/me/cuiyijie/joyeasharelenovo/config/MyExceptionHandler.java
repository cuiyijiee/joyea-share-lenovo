package me.cuiyijie.joyeasharelenovo.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 15:10
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 全局异常处理，反正异常返回统一格式的map
     *
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)//指定拦截的异常
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
        log.error("全局捕捉到异常：", exception);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "0");
        map.put("msg", String.format("发生未知错误：%s", exception.getMessage()));
        return map;
    }
}