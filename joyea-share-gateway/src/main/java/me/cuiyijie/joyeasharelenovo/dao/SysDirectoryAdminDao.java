package me.cuiyijie.joyeasharelenovo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cuiyijie.joyeasharelenovo.model.JoyeaUser;
import me.cuiyijie.joyeasharelenovo.model.SysDirectoryAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDirectoryAdminDao extends BaseMapper<SysDirectoryAdmin> {

    List<JoyeaUser> listDirectoryAddAdmin(@Param("directoryId") String directoryId);
}
