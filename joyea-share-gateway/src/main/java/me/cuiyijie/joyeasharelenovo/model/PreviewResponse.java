package me.cuiyijie.joyeasharelenovo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.cuiyijie.joyeasharelenovo.model.base.BaseResponse;

@Data
public class PreviewResponse  extends BaseResponse {

    @JsonProperty("previewUrl")
    private String previewUrl;

}
