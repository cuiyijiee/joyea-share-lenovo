package me.cuiyijie.joyeasharelenovo.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.cuiyijie.joyeasharelenovo.model.v3.base.BaseResponse;

@Data
public class PreviewResponse  extends BaseResponse {

    @JsonProperty("previewUrl")
    private String previewUrl;

}
