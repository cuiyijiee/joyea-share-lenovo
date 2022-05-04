package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.joyeasharelenovo.dao.SysDirectoryAdminDao;
import me.cuiyijie.joyeasharelenovo.model.JoyeaUser;
import me.cuiyijie.joyeasharelenovo.model.SysDirectoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysDirectoryAdminService {

    @Autowired
    private SysDirectoryAdminDao sysDirectoryAdminDao;

    public void updateDirectoryAdmins(String directoryId, List<String> joyeaUserIds) {
        //先删除
        sysDirectoryAdminDao.delete(new QueryWrapper<SysDirectoryAdmin>().eq("directory_id", directoryId));
        //再依次插入
        for (String joyeaUserId : joyeaUserIds) {
            SysDirectoryAdmin sysDirectoryAdmin = new SysDirectoryAdmin();
            sysDirectoryAdmin.setDirectoryId(directoryId);
            sysDirectoryAdmin.setJoyeaUserId(joyeaUserId);
            sysDirectoryAdmin.setCreatedAt(LocalDateTime.now());
            sysDirectoryAdmin.setEnabled(true);
            sysDirectoryAdminDao.insert(sysDirectoryAdmin);
        }
    }

    public List<JoyeaUser> listDirectoryAdmin(String directoryId) {
        return sysDirectoryAdminDao.listDirectoryAddAdmin(directoryId);
    }
}
