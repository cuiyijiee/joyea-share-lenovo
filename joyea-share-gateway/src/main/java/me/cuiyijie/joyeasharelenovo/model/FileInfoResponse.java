package me.cuiyijie.joyeasharelenovo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.cuiyijie.joyeasharelenovo.model.base.BaseResponse;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 15:20
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class FileInfoResponse extends BaseResponse {

    private Object bookmarkId;
    private String creator;
    private Long creatorUid;
    private String deliveryCode;
    private String desc;
    private Boolean dir;
    private Object isBookmark;
    private Object isTeam;
    private String modified;
    private Long neid;
    private Long nsid;
    private String path;
    private String pathType;
    private String rev;
    private String size;
    private Object supportPreview;
    private String updator;
    private Long updatorUid;
}
