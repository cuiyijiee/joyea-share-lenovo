package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import me.cuiyijie.exception.SysRuntimeException;
import me.cuiyijie.joyeasharelenovo.dao.SysDirectoryAdminDao;
import me.cuiyijie.joyeasharelenovo.dao.SysDirectoryDao;
import me.cuiyijie.joyeasharelenovo.dao.SysSrcDao;
import me.cuiyijie.joyeasharelenovo.model.JoyeaUser;
import me.cuiyijie.joyeasharelenovo.model.SysDirectory;
import me.cuiyijie.joyeasharelenovo.model.SysDirectoryAdmin;
import me.cuiyijie.joyeasharelenovo.model.SysSrc;
import me.cuiyijie.joyeasharelenovo.enums.DirectoryType;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import me.cuiyijie.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 21:02
 */
@Service
public class SysDirectoryService {

    @Autowired
    private SysDirectoryDao sysDirectoryDao;
    @Autowired
    private SysDirectoryAdminDao sysDirectoryAdminDao;
    @Autowired
    private SysSrcDao sysSrcDao;

    public SysDirectory insertNewDir(SysDirectory sysDirectory) {
        sysDirectoryDao.insert(sysDirectory);
        return sysDirectory;
    }

    public void updateFileName(String id, String fileName) {
        SysDirectory sysDirectory = new SysDirectory();
        sysDirectory.setId(id);
        sysDirectory.setDirName(fileName);
        sysDirectoryDao.updateById(sysDirectory);
    }

    public SysDirectory findByNeid(String neid) {
        return sysDirectoryDao.selectOne(new QueryWrapper<SysDirectory>().eq("neid", neid));
    }

    public List<SysDirectory> findChildById(String id) {
        return sysDirectoryDao.selectList(new QueryWrapper<SysDirectory>().eq("parent_dir_id", id));
    }

    public void deleteByIds(List<String> ids) {
        if (ids != null && ids.size() > 0) {
            sysDirectoryDao.deleteBatchIds(ids);
        }
    }

    public SysDirectory newSelfDirectory(String parentDirId, String dirName) {
        List<SysDirectory> existChildDirs = listSelfDir(parentDirId);
        existChildDirs = existChildDirs.stream().filter(srcDirectory -> srcDirectory.getDirName().equals(dirName)).collect(Collectors.toList());
        if (existChildDirs.size() > 0) {
            throw new SysRuntimeException("当前目录已经存在此目录【" + dirName + "】");
        }

        SysDirectory sysDirectory = new SysDirectory();
        sysDirectory.setDirName(dirName);
        sysDirectory.setParentDirId(StringUtils.defaultIfBlank(parentDirId, null));
        sysDirectory.setDirType(DirectoryType.SELF);

        if (StringUtils.isNotBlank(parentDirId) && !"0".equals(parentDirId)) {
            SysDirectory existChildDir = sysDirectoryDao.selectById(parentDirId);
            sysDirectory.setPath(findDirectoryPrefix(existChildDir) + dirName);
        } else {
            sysDirectory.setPath("/" + dirName);
        }
        sysDirectory.setCreatedAt(LocalDateTime.now());
        sysDirectory.setEnabled(true);
        return insertNewDir(sysDirectory);
    }

    public List<SysDirectory> listSelfDir(String parentDirId) {
        QueryWrapper<SysDirectory> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isBlank(parentDirId)) {
            queryWrapper.isNull("parent_dir_id");
        } else {
            queryWrapper.eq("parent_dir_id", parentDirId);
        }
        queryWrapper.eq("dir_type", DirectoryType.SELF);
        return sysDirectoryDao.selectList(queryWrapper);
    }

    public FileMetadataResponse getFileMetadata(String path) {
        FileMetadataResponse fileMetadataResponse = new FileMetadataResponse();
        fileMetadataResponse.setIsDir(true);
        SysDirectory selfSysDirectory = sysDirectoryDao.selectOne(new QueryWrapper<SysDirectory>().eq("path", path));
        if (selfSysDirectory == null) {
            throw new SysRuntimeException("查询目录不存在");
        }
        fileMetadataResponse.setNeid(selfSysDirectory.getId());
        fileMetadataResponse.setPath(selfSysDirectory.getPath());
        fileMetadataResponse.setFileName(selfSysDirectory.getDirName());

        //获取目录权限
        if(!"0".equals(selfSysDirectory.getId())){
            String rootPath = "/" + selfSysDirectory.getPath().split("/")[1];
            SysDirectory rootDir = sysDirectoryDao.selectOne(new QueryWrapper<SysDirectory>().eq("path", rootPath));
            List<JoyeaUser> adminUsers = sysDirectoryAdminDao.listDirectoryAddAdmin(rootDir.getId());
            fileMetadataResponse.setAdminUser(adminUsers);
        }else{
            fileMetadataResponse.setAdminUser(new ArrayList<>());
        }

        List<FileMetadataResponse> childFileMetas = new ArrayList<>();

        //查找子目录
        List<SysDirectory> childSrcDir = listSelfDir(selfSysDirectory.getId());
        for (int index = 0; index < childSrcDir.size(); index++) {
            SysDirectory sysDirectory = childSrcDir.get(index);
            FileMetadataResponse childFileMeta = new FileMetadataResponse();
            childFileMeta.setIsDir(true);
            childFileMeta.setNeid(sysDirectory.getId());
            childFileMeta.setFileName(sysDirectory.getDirName());
            childFileMeta.setPath(sysDirectory.getPath());

            //获取目录权限
            if("0".equals(selfSysDirectory.getId())){
                String rootPath = "/" + sysDirectory.getPath().split("/")[1];
                SysDirectory rootDir = sysDirectoryDao.selectOne(new QueryWrapper<SysDirectory>().eq("path", rootPath));
                List<JoyeaUser> adminUsers = sysDirectoryAdminDao.listDirectoryAddAdmin(rootDir.getId());
                childFileMeta.setAdminUser(adminUsers);
            }else{
                childFileMeta.setAdminUser(new ArrayList<>());
            }

            childFileMetas.add(childFileMeta);
        }
        //查找文件夹下文件
        List<SysSrc> childSysSrc = sysSrcDao.selectList(new QueryWrapper<SysSrc>().eq("parent_dir_id", selfSysDirectory.getId()));
        for (int index = 0; index < childSysSrc.size(); index++) {
            SysSrc sysSrc = childSysSrc.get(index);
            FileMetadataResponse childFileMeta = new FileMetadataResponse();
            childFileMeta.setPath(selfSysDirectory.getPath() + "/" + sysSrc.getFileName());
            childFileMeta.setIsDir(false);
            childFileMeta.setNeid(sysSrc.getNeid());
            childFileMeta.setMimeType(sysSrc.getMimeType());
            childFileMeta.setFileName(sysSrc.getFileName());
            childFileMeta.setBytes(sysSrc.getBytes());
            childFileMetas.add(childFileMeta);
        }
        fileMetadataResponse.setContent(childFileMetas);
        return fileMetadataResponse;
    }

    @Transactional
    public  void removeDirectory(String dirId) {
        SysDirectory existDirectory = sysDirectoryDao.selectById(dirId);
        if(existDirectory == null) {
            throw new SysRuntimeException("待删除的文件夹不存在！");
        }
        String toDeletePrefix = existDirectory.getPath() + "/";
        sysDirectoryDao.deleteById(dirId);
        sysDirectoryDao.delete(new QueryWrapper<SysDirectory>().likeLeft("path",toDeletePrefix));
    }

    @Transactional
    public SysDirectory rename(String dirId, String newFileName) {
        //查询当前文件夹信息
        SysDirectory curDirectory = sysDirectoryDao.selectById(dirId);
        if (curDirectory == null) {
            throw new SysRuntimeException("要修改的文件夹不存在！");
        }
        //查询父文件夹
//        SysDirectory sysDirectory = sysDirectoryDao.selectById(curDirectory.getParentDirId());
        //查询父级文件夹下面是否有重名文件夹
        List<SysDirectory> directoriesWithSameFather = sysDirectoryDao.selectList(new QueryWrapper<SysDirectory>().eq("parent_dir_id", curDirectory.getParentDirId()));
        List<SysDirectory> sameNameDir = directoriesWithSameFather.stream().filter(sysDirectory -> sysDirectory.getDirName().equals(newFileName)).collect(Collectors.toList());
        if (sameNameDir.size() > 0) {
            throw new SysRuntimeException("当前层级下已经存在此文件夹！");
        }
        //修改所有子文件夹的path
        //String oldFilePrefix = findDirectoryPrefix();
        String newPath = curDirectory.getPath().substring(0, curDirectory.getPath().length() - curDirectory.getDirName().length()) + newFileName;
        String oldFilePrefix = curDirectory.getPath() + "/";
        sysDirectoryDao.updateDirectoryPrefix(oldFilePrefix, newPath + "/");

        //修改当前文件夹名称
        SysDirectory newDir = new SysDirectory();
        newDir.setId(curDirectory.getId());
        newDir.setDirName(newFileName);
        newDir.setPath(newPath);
        sysDirectoryDao.updateById(newDir);
        return newDir;
    }

    public String findDirectoryPrefix(SysDirectory sysDirectory) {
        List<SysDirectory> parentDirs = findParentDir(sysDirectory);
        List<String> parentDirNames = parentDirs.stream().map(SysDirectory::getDirName).collect(Collectors.toList());
        parentDirNames = Lists.reverse(parentDirNames);
        return String.join("/", parentDirNames) + "/";
    }

    private List<SysDirectory> findParentDir(SysDirectory sysDirectory) {
        List<SysDirectory> result = new ArrayList<>();
        _findParentDir(sysDirectory, result);
        return result;
    }

    private void _findParentDir(SysDirectory sysDirectory, List<SysDirectory> existList) {
        existList.add(sysDirectory);
        if (StringUtils.isNotBlank(sysDirectory.getParentDirId())) {
            SysDirectory parentDir = sysDirectoryDao.selectById(sysDirectory.getParentDirId());
            _findParentDir(parentDir, existList);
        }
    }
}
