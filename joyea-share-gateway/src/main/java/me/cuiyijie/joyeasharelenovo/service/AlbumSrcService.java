package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.joyeasharelenovo.dao.AlbumSrcDao;
import me.cuiyijie.joyeasharelenovo.model.AlbumSrc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumSrcService {

    @Autowired
    private AlbumSrcDao albumSrcDao;

    public Long countByNeid(String neid){
        return albumSrcDao.selectCount(new QueryWrapper<AlbumSrc>().eq("src_neid",neid)).longValue();
    }

}
