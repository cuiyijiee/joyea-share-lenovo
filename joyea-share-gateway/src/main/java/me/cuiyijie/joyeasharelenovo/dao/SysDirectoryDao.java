package me.cuiyijie.joyeasharelenovo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cuiyijie.joyeasharelenovo.model.SysDirectory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 21:02
 */
@Repository
public interface SysDirectoryDao extends BaseMapper<SysDirectory> {

    @Update("UPDATE sys_directory SET path = REPLACE(path,#{oldPrefix},#{newPrefix}) WHERE path  LIKE concat(#{oldPrefix}, '%') ")
    Integer updateDirectoryPrefix(@Param("oldPrefix") String oldPrefix, @Param("newPrefix") String newPrefix);
}
