package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.joyeasharelenovo.dao.RedirectPathDao;
import me.cuiyijie.joyeasharelenovo.enums.DirectoryType;
import me.cuiyijie.joyeasharelenovo.model.RedirectPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RedirectPathService {

    @Autowired
    private RedirectPathDao redirectPathDao;

    public RedirectPath addNewRedirectPath(String path, DirectoryType type) {
        RedirectPath existRedirectPath = redirectPathDao.selectOne(new QueryWrapper<RedirectPath>()
                .eq("path", path)
                .eq("directory_type", type)
        );
        if (existRedirectPath != null) {
            return existRedirectPath;
        } else {
            RedirectPath redirectPath = new RedirectPath();
            redirectPath.setPath(path);
            redirectPath.setDirectoryType(type);
            redirectPath.setCreatedAt(LocalDateTime.now());
            redirectPathDao.insert(redirectPath);
            return redirectPath;
        }
    }


    public RedirectPath queryPathById(Long id) {
        return redirectPathDao.selectById(id);
    }

}
