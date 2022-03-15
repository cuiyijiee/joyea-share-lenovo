package me.cuiyijie.joyeasharelenovo.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: yjcui3
 * @Date: 2022/3/8 11:06
 */
@Data
public class BaseResponse {

    @JsonProperty("errcode")
    private String errorCode;

    @JsonProperty("errmsg")
    private String errorMsg;

}
