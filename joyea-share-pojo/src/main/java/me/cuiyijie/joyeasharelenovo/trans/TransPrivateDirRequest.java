package me.cuiyijie.joyeasharelenovo.trans;

import lombok.Data;

import java.util.List;

@Data
public class TransPrivateDirRequest {

    private String neid;

    private String parentDirId;
    private String srcPath;

    private String dirId;
    private String newDirName;

    private List<String> admins;

}
