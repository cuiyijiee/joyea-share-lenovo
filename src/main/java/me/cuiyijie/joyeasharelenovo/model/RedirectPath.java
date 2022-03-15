package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedirectPath {


    @TableId(value = "ID", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("path")
    private String path;

    @TableField("created_at")
    private LocalDateTime createdAt;

}
