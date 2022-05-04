package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.exception.SysRuntimeException;
import me.cuiyijie.joyeasharelenovo.dao.SysDirectoryDao;
import me.cuiyijie.joyeasharelenovo.dao.SysSrcDao;
import me.cuiyijie.joyeasharelenovo.model.SysSrc;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SysSrcService {

    @Autowired
    private SysSrcDao sysSrcDao;
    @Autowired
    private SysDirectoryDao sysDirectoryDao;
    @Autowired
    private OpenApiV2Service openApiV2Service;

    public void removeSrc(String parDirId, String neid) {
        sysSrcDao.delete(new QueryWrapper<SysSrc>().eq("parent_dir_id", parDirId).eq("neid", neid));
    }

    public SysSrc newSrc(String parentDirId, String srcFilePath) {
        FileMetadataResponse fileMetadataResponse = openApiV2Service.getFileMetadata(srcFilePath);
        if (fileMetadataResponse == null) {
            throw new SysRuntimeException("待添加文件不存在");
        }

        SysSrc existSrc = sysSrcDao.selectOne(new QueryWrapper<SysSrc>().eq("neid", fileMetadataResponse.getNeid()).eq("parent_dir_id", parentDirId));
        if (existSrc != null) {
            throw new SysRuntimeException("该文件夹下已经存在该文件！");
        }
        SysSrc sysSrc = new SysSrc();
        sysSrc.setParentDirId(parentDirId);
        sysSrc.setNeid(fileMetadataResponse.getNeid());
        sysSrc.setMimeType(fileMetadataResponse.getMimeType());
        sysSrc.setFileName(fileMetadataResponse.getFileName());
        sysSrc.setBytes(fileMetadataResponse.getBytes());
        sysSrc.setCreatedAt(LocalDateTime.now());
        sysSrcDao.insert(sysSrc);
        return sysSrc;
    }

}
