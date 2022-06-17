package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysSrc {

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;
    private String parentDirId;
    //联想网盘文件ID
    private String neid;
    @JsonProperty("mime_type")
    private String mimeType;
    @JsonProperty("file_name")
    private String fileName;
    private Long bytes;
    private String alias;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled;

}
