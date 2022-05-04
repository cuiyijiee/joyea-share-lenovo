package me.cuiyijie.joyeasharelenovo.trans;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.cuiyijie.joyeasharelenovo.enums.DirectoryType;

@NoArgsConstructor
@Data
public class TransFileMetadataRequest {
    private String path;
    private DirectoryType directoryType;
}
