package me.cuiyijie.joyeasharelenovo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cuiyijie.joyeasharelenovo.model.base.BaseResponse;

import java.util.List;

/**
 * @Author: yjcui3
 * @Date: 2022/3/8 11:09
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class FileListResponse extends BaseResponse {

    private List<File> fileModelList;

}
