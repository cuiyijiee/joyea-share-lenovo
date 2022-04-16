package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.joyeasharelenovo.dao.SrcDirDao;
import me.cuiyijie.joyeasharelenovo.model.SrcDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 21:02
 */
@Service
public class SrcDirService {

    @Autowired
    private SrcDirDao srcDirDao;

    public SrcDirectory insertNewDir(SrcDirectory srcDirectory) {
        srcDirDao.insert(srcDirectory);
        return srcDirectory;
    }

    public void updateFileName(String id, String fileName) {
        SrcDirectory srcDirectory = new SrcDirectory();
        srcDirectory.setId(id);
        srcDirectory.setDirName(fileName);
        srcDirDao.updateById(srcDirectory);
    }

    public SrcDirectory findByNeid(String neid) {
        return srcDirDao.selectOne(new QueryWrapper<SrcDirectory>().eq("neid", neid));
    }

    public List<SrcDirectory> findChildById(String id) {
        return srcDirDao.selectList(new QueryWrapper<SrcDirectory>().eq("parent_dir_id", id));
    }

    public void deleteByIds(List<String> ids) {
        if (ids != null && ids.size() > 0) {
            srcDirDao.deleteBatchIds(ids);
        }
    }

}
