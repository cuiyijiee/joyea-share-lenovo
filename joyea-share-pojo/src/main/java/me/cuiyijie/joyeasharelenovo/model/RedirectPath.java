package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import me.cuiyijie.joyeasharelenovo.enums.DirectoryType;
import org.apache.tomcat.jni.Directory;

import java.time.LocalDateTime;

@Data
public class RedirectPath {


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("path")
    private String path;

    @TableField("directory_type")
    private DirectoryType directoryType;

    @TableField("created_at")
    private LocalDateTime createdAt;

}
