package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import me.cuiyijie.joyeasharelenovo.enums.TranscodeVideoStatus;

import java.time.LocalDateTime;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 14:41
 */
@Data
public class TranscodeVideo {

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    private String neid;

    @TableField(value = "file_name")
    private String fileName;

    @TableField(value = "trans_video_url")
    private String transVideoUrl;

    @TableField(value = "enabled")
    private boolean enabled;

    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

    @TableField(value = "last_use_at")
    private LocalDateTime lastUseAt;

    @TableField(value = "status")
    private TranscodeVideoStatus status;

}
