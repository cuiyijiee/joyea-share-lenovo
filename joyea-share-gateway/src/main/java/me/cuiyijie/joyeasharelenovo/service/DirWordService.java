package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.joyeasharelenovo.dao.DirWordDao;
import me.cuiyijie.joyeasharelenovo.model.DirWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 16:53
 */
@Service
public class DirWordService {

    @Autowired
    private DirWordDao dirWordDao;


    public List<DirWord> findByNeid(String neid) {
        return dirWordDao.selectList(new QueryWrapper<DirWord>().eq("neid",neid));
    }


}
