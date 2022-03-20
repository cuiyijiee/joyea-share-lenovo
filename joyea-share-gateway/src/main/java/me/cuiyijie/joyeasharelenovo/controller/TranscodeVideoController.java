package me.cuiyijie.joyeasharelenovo.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.model.TransTranscodeVideoRequest;
import me.cuiyijie.joyeasharelenovo.service.TranscodeVideoService;
import me.cuiyijie.trans.TransBasePageResponse;
import me.cuiyijie.trans.TransBaseResponse;
import me.cuiyijie.util.CheckParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: cuiyijie
 * @Date: 2022/3/20 15:42
 */
@Slf4j
@Controller()
@RequestMapping("apiv2/transcode")
public class TranscodeVideoController {

    @Autowired
    private TranscodeVideoService transcodeVideoService;

    @ResponseBody
    @RequestMapping(path = "insert", method = RequestMethod.POST)
    public TransBaseResponse insert(@RequestBody TransTranscodeVideoRequest request) {
        List<String> paramsCheck = Lists.newArrayList("neid:视频文件id（neid）");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            TransBaseResponse transBaseResponse = new TransBaseResponse();
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        return transcodeVideoService.insert(request.getNeid(), request.getNsid());
    }

    @ResponseBody
    @RequestMapping(path = "list", method = RequestMethod.POST)
    public TransBasePageResponse list(@RequestBody TransTranscodeVideoRequest request) {
        return transcodeVideoService.list(request.getFileName(), request.getPageSize(), request.getPageNum());
    }

    @ResponseBody
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public TransBaseResponse delete(@RequestBody TransTranscodeVideoRequest request) {
        List<String> paramsCheck = Lists.newArrayList("id:视频文件id（id）");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            TransBaseResponse transBaseResponse = new TransBaseResponse();
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        transcodeVideoService.delete(request.getId());
        return TransBaseResponse.success();
    }

    @RequestMapping(path = "preview", method = RequestMethod.GET)
    public String list(@RequestParam String id) {
        String previewUrl = transcodeVideoService.getPreviewUrl(id);
        return "redirect:" + previewUrl;
    }
}
