package me.cuiyijie.joyeasharelenovo.xxljobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.config.Constants;
import me.cuiyijie.joyeasharelenovo.enums.TranscodeVideoStatus;
import me.cuiyijie.joyeasharelenovo.model.SysDirectory;
import me.cuiyijie.joyeasharelenovo.model.TranscodeVideo;
import me.cuiyijie.joyeasharelenovo.enums.DirectoryType;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV2Service;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV3Service;
import me.cuiyijie.joyeasharelenovo.service.SysDirectoryService;
import me.cuiyijie.joyeasharelenovo.service.TranscodeVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、任务开发：在Spring Bean实例中，开发Job方法；
 * 2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 * 4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Slf4j
@Component
public class SampleXxlJob {

    @Autowired
    private OpenApiV3Service openApiV3Service;
    @Autowired
    private TranscodeVideoService transcodeVideoService;
    @Autowired
    private OpenApiV2Service openApiV2Service;
    @Autowired
    private SysDirectoryService sysDirectoryService;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("refreshAllVideoJob")
    public void refreshAllVideoJob() {
        XxlJobHelper.log("1.start update transcode task;");
        List<TranscodeVideo> allVideo = transcodeVideoService.getAllTranscodeVideo(TranscodeVideoStatus.LOADED);
        XxlJobHelper.log("2.got transcode video count: " + allVideo.size());
        for (int index = 0; index < allVideo.size(); index++) {
            TranscodeVideo transcodeVideo = allVideo.get(index);
            XxlJobHelper.log("3.start " + index + " [" + transcodeVideo.getId() + "]：" + transcodeVideo.getFileName());
            try {
                String previewUrl = openApiV3Service.getFilePreviewUrl(transcodeVideo.getNeid(), Constants.DEFAULT_PATH_NSID,false);
                transcodeVideo.setTransVideoUrl(previewUrl);
                transcodeVideoService.updateTranscodeUrl(transcodeVideo.getId(), previewUrl);
                XxlJobHelper.log("success: " + previewUrl);
            } catch (Exception exception) {
                log.error("update transcode video exist error: ", exception);
                XxlJobHelper.log("【error】 with:" + exception.getMessage());
            }
        }
    }

    @XxlJob("refreshNewVideoJob")
    public void refreshNewVideoJob() {
        XxlJobHelper.log("1.start update transcode task;");
        List<TranscodeVideo> allVideo = transcodeVideoService.getAllTranscodeVideo(TranscodeVideoStatus.NEW);
        XxlJobHelper.log("2.got transcode video count: " + allVideo.size());
        for (int index = 0; index < allVideo.size(); index++) {
            TranscodeVideo transcodeVideo = allVideo.get(index);
            XxlJobHelper.log("3.start " + index + " [" + transcodeVideo.getId() + "]：" + transcodeVideo.getFileName());
            try {
                String previewUrl = openApiV3Service.getFilePreviewUrl(transcodeVideo.getNeid(), Constants.DEFAULT_PATH_NSID,false);
                transcodeVideo.setTransVideoUrl(previewUrl);
                transcodeVideoService.updateTranscodeUrl(transcodeVideo.getId(), previewUrl);
                XxlJobHelper.log("success: " + previewUrl);
            } catch (Exception exception) {
                log.error("update transcode video exist error: ", exception);
                XxlJobHelper.log("【error】 with:" + exception.getMessage());
            }
        }
    }

    @XxlJob("syncLenovoDir")
    public void syncLenovoDir() {
        syncDir("/营销素材展示", null);
    }

    public void syncDir(String initPath, SysDirectory parentDir) {
        FileMetadataResponse fileMetadataResponse = openApiV2Service.getFileMetadata(initPath);
        if (fileMetadataResponse != null) {
            //遍历完成之后删除不存在的文件夹
            SysDirectory existDirectory = sysDirectoryService.findByNeid(fileMetadataResponse.getNeid());
            //如果不存在则新增
            if (existDirectory == null) {
                SysDirectory sysDirectory = new SysDirectory();
                sysDirectory.setDirName(fileMetadataResponse.getFileName());
                sysDirectory.setDirType(DirectoryType.LENOVO);
                sysDirectory.setEnabled(true);
                sysDirectory.setCreatedAt(LocalDateTime.now());
                if (parentDir != null) {
                    sysDirectory.setParentDirId(parentDir.getId());
                } else {
                    sysDirectory.setParentDirId(null);
                }
                sysDirectory.setNeid(fileMetadataResponse.getNeid());
                existDirectory = sysDirectoryService.insertNewDir(sysDirectory);
            } else {
                //更新文件夹名称
                if (existDirectory.getDirName().equals(fileMetadataResponse.getFileName())) {
                    sysDirectoryService.updateFileName(existDirectory.getId(), fileMetadataResponse.getFileName());
                }
            }

            if (fileMetadataResponse.getContent() != null) {
                //本地存储的文件夹
                List<SysDirectory> existChildDirs = sysDirectoryService.findChildById(existDirectory.getId());
                //过滤掉网盘不存在的文件夹进行删除
                List<String> onlineChildNeid = fileMetadataResponse.getContent().stream()
                        .map(FileMetadataResponse::getNeid)
                        .collect(Collectors.toList());
                List<String> onlineNotExistChildDirId = existChildDirs.stream()
                        .filter(srcDirectory -> !onlineChildNeid.contains(srcDirectory.getNeid()))
                        .map(SysDirectory::getId)
                        .collect(Collectors.toList());
                log.info("当前文件夹:{},要删除的文件夹ids: {}",fileMetadataResponse.getFileName(),onlineNotExistChildDirId);
                if (onlineNotExistChildDirId.size() > 0) {
                    sysDirectoryService.deleteByIds(onlineNotExistChildDirId);
                }

                for (int index = 0; index < fileMetadataResponse.getContent().size(); index++) {
                    FileMetadataResponse metadataResponse = fileMetadataResponse.getContent().get(index);
                    if (metadataResponse.getIsDir()) {
                        System.out.println("获取到目录：" + metadataResponse.getPath());
                        syncDir(metadataResponse.getPath(), existDirectory);
                    }
                }
            }
        }
    }
}
