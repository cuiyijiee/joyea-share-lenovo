package me.cuiyijie.joyeasharelenovo.model.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cuiyijie.joyeasharelenovo.model.v3.base.BaseResponse;

import java.util.List;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/8 11:09
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class FileListResponse extends BaseResponse {

    private List<File> fileModelList;

}
