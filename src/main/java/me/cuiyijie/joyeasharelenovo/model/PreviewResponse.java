package me.cuiyijie.joyeasharelenovo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PreviewResponse {


    @JsonProperty("errcode")
    private Integer errorCode;

    @JsonProperty("errmsg")
    private String errorMsg;

    @JsonProperty("previewUrl")
    private String previewUrl;
}
