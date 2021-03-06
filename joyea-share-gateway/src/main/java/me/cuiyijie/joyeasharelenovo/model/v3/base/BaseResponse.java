package me.cuiyijie.joyeasharelenovo.model.v3.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/8 11:06
 */
@Data
public class BaseResponse {

    @JsonProperty("errcode")
    private String errorCode;

    @JsonProperty("errmsg")
    private String errorMsg;

}
