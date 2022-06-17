package me.cuiyijie.joyeasharelenovo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cuiyijie.joyeasharelenovo.model.TranscodeVideo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 14:51
 */
@Repository
public interface TranscodeVideoDao extends BaseMapper<TranscodeVideo> {

    @Update("UPDATE transcode_video set use_count = use_count + 1, last_use_at = #{now} WHERE id = #{id}")
    void addView(@Param("id") String id, @Param("now") LocalDateTime time);

}
