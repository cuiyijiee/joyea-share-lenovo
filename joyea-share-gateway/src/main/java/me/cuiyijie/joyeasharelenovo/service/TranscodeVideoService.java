package me.cuiyijie.joyeasharelenovo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.cuiyijie.joyeasharelenovo.dao.TranscodeVideoDao;
import me.cuiyijie.joyeasharelenovo.model.v3.FileInfoResponse;
import me.cuiyijie.joyeasharelenovo.model.TranscodeVideo;
import me.cuiyijie.trans.TransBasePageResponse;
import me.cuiyijie.trans.TransBaseResponse;
import me.cuiyijie.util.FileUtil;
import me.cuiyijie.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 15:01
 */
@Service
public class TranscodeVideoService {

    @Autowired
    private TranscodeVideoDao transcodeVideoDao;

    @Autowired
    private OpenApiV3Service openApiV3Service;

    public TranscodeVideo findByNeid(String neid) {
        return transcodeVideoDao.selectOne(new QueryWrapper<TranscodeVideo>().eq("neid", neid));
    }


    public TransBaseResponse insert(String videoNeid, String nsid) {
        TranscodeVideo existedTranscodeVideo = findByNeid(videoNeid);
        if (existedTranscodeVideo != null) {
            return TransBaseResponse.failed("该视频文件已经添加到转码列表中！");
        }
        FileInfoResponse fileInfoResponse = openApiV3Service.getFileInfo(videoNeid, nsid);
        if (fileInfoResponse != null || "0".equals(fileInfoResponse.getErrorCode())) {
            String fileName = FileUtil.getFileNameByPath(fileInfoResponse.getPath());
            TranscodeVideo transcodeVideo = new TranscodeVideo();
            transcodeVideo.setNeid(videoNeid);
            transcodeVideo.setFileName(fileName);
            transcodeVideo.setCreatedAt(LocalDateTime.now());
            transcodeVideo.setEnabled(true);
            transcodeVideoDao.insert(transcodeVideo);
            return TransBaseResponse.success();
        } else {
            return TransBaseResponse.failed("未知错误");
        }
    }

    public TransBasePageResponse list(String fileName, long pageSize, long pageNum) {
        QueryWrapper<TranscodeVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (StringUtils.isNoneBlank(fileName)) {
            queryWrapper.eq("file_name", fileName);
        }
        Page<TranscodeVideo> result = (Page<TranscodeVideo>) transcodeVideoDao.selectPage(new Page<>(pageNum, pageSize),
                queryWrapper);
        return new TransBasePageResponse(result);
    }

    public List<TranscodeVideo> getAllTranscodeVideo() {
        return transcodeVideoDao.selectList(null);
    }

    public void updateTranscodeUrl(String id, String previewUrl) {
        TranscodeVideo transcodeVideo = new TranscodeVideo();
        transcodeVideo.setTransVideoUrl(previewUrl);
        transcodeVideo.setUpdatedAt(LocalDateTime.now());
        transcodeVideoDao.update(transcodeVideo, new QueryWrapper<TranscodeVideo>().eq("id", id));
    }

    public String getPreviewUrl(String id) {

        //記錄使用時間
        transcodeVideoDao.addView(id, LocalDateTime.now());

        return transcodeVideoDao.selectById(id).getTransVideoUrl();
    }

    public void delete(String id) {
        transcodeVideoDao.deleteById(id);
    }
}
