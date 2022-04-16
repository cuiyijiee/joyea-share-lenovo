package me.cuiyijie.joyeasharelenovo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlbumSrc {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    private String srcNeid;
    private String albumId;
    private String srcPath;
    private String srcHash;
    private String srcRev;
    private String srcType;
    private String srcSize;
    private String srcDesc;
    private String srcFileName;
    private Long srcBytes;
    private LocalDateTime createdAt;

}
