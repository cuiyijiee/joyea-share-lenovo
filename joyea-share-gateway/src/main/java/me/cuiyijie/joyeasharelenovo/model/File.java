package me.cuiyijie.joyeasharelenovo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/8 10:43
 */

@Data
public class File {

    private String accessMode;

    private String bookmark;
    /**
     * 收藏 id
     */
    private String bookmarkId;
    /**
     * 外链码
     */
    private String deliveryCode;
    /**
     * 文件备注
     */
    private String desc;
    /**
     * 是否为文件夹
     */
    private Boolean dir;
    /**
     * 是否团队文件夹
     */
    private Boolean isTeam;
    /**
     * 文件ID
     */
    private Long neid;
    /**
     * 命名空间ID
     */
    private Long nsid;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件类型：ent企业空间，self个人空间
     */
    private String pathType;
    /**
     * 文件版本
     */
    private String rev;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 是否支持预览
     */
    private Boolean supportPreview;

    private String creator;
    private Long creatorUid;

    private String updator;
    private Long updatorUid;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ",timezone = "GMT+8")
    private LocalDateTime modified;
}
