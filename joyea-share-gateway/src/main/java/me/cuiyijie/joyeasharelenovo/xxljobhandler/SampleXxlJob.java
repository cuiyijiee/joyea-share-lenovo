package me.cuiyijie.joyeasharelenovo.xxljobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.config.Constants;
import me.cuiyijie.joyeasharelenovo.model.TranscodeVideo;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV3Service;
import me.cuiyijie.joyeasharelenovo.service.TranscodeVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("refreshAllVideoJob")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("1.start update transcode task;");
        List<TranscodeVideo> allVideo = transcodeVideoService.getAllTranscodeVideo();
        XxlJobHelper.log("2.got transcode video count: " + allVideo.size());
        for (int index = 0; index < allVideo.size(); index++) {
            TranscodeVideo transcodeVideo = allVideo.get(index);
            XxlJobHelper.log("3.start " + index + " [" + transcodeVideo.getId() + "]：" + transcodeVideo.getFileName());
            try {
                String previewUrl = openApiV3Service.getFilePreviewUrl(transcodeVideo.getNeid(), Constants.DEFAULT_PATH_NSID);
                transcodeVideo.setTransVideoUrl(previewUrl);
                transcodeVideoService.updateTranscodeUrl(transcodeVideo.getId(), previewUrl);
                XxlJobHelper.log("success: " + previewUrl);
            } catch (Exception exception) {
                log.error("update transcode video exist error: ", exception);
                XxlJobHelper.log("【error】 with:" + exception.getMessage());
            }
        }
    }
}
