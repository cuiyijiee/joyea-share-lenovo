package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.joyeasharelenovo.dao.JoyeaUserDao;
import me.cuiyijie.joyeasharelenovo.model.JoyeaUser;
import me.cuiyijie.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoyeaUserService {

    @Autowired
    private JoyeaUserDao joyeaUserDao;

    public List<JoyeaUser> searchJoyeaUser(String userName) {
        QueryWrapper<JoyeaUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("joyea_name", userName);
        }
        return joyeaUserDao.selectList(queryWrapper);
    }
}
