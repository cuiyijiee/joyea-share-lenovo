package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysDirectoryAdmin {

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    private String directoryId;
    private String joyeaUserId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled;

}
