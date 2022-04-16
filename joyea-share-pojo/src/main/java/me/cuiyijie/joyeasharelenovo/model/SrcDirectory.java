package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import me.cuiyijie.joyeasharelenovo.model.enums.DirectoryType;

import java.time.LocalDateTime;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 18:20
 */
@Data
public class SrcDirectory {

    @TableId(value = "ID", type = IdType.ID_WORKER_STR)
    private String id;

    //联想网盘文件ID
    private String neid;
    //目录名称
    private String dirName;
    //父文件夹ID
    private String parentDirId;
    //文件夹类型
    private DirectoryType dirType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean enabled;

}
