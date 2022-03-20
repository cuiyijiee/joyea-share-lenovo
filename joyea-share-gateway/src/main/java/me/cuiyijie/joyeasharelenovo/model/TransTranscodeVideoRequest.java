package me.cuiyijie.joyeasharelenovo.model;

import lombok.Data;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 16:00
 */
@Data
public class TransTranscodeVideoRequest {

    private long pageSize = 10;
    private long pageNum = 1;

    private String id;

    private String neid;
    private String nsid = "745477";

    private String fileName;
}
