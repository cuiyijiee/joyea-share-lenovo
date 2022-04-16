package me.cuiyijie.joyeasharelenovo.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.model.TransFileMetadataRequest;
import me.cuiyijie.joyeasharelenovo.model.v2.FileExtraMetaResponse;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import me.cuiyijie.joyeasharelenovo.service.LeaderboardService;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV2Service;
import me.cuiyijie.trans.TransBaseResponse;
import me.cuiyijie.util.CheckParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("apiv2/lenovo")
public class LenovoController {

    @Autowired
    private OpenApiV2Service openApiV2Service;
    @Autowired
    private LeaderboardService leaderboardService;

    @RequestMapping(value = "fileMetadata", method = RequestMethod.POST)
    public TransBaseResponse getFileMetadata(@RequestBody TransFileMetadataRequest request) {
        List<String> paramsCheck = Lists.newArrayList("path:文件路径（path）");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            TransBaseResponse transBaseResponse = new TransBaseResponse();
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        FileMetadataResponse fileMetadataResponse = openApiV2Service.getFileMetadata(request.getPath());
        if (fileMetadataResponse.getContent() != null) {
            //获取每一个文件tag
            List<String> neids = fileMetadataResponse.getContent().stream().map(FileMetadataResponse::getNeid).collect(Collectors.toList());
            FileExtraMetaResponse fileExtraMetaResponse = openApiV2Service.getFileExtraMetadata(neids);
            Map<String, List<FileExtraMetaResponse.ContentDTO.TagsDTO>> tagMap = fileExtraMetaResponse.getContent()
                    .stream()
                    .collect(Collectors.toMap(FileExtraMetaResponse.ContentDTO::getNeid, FileExtraMetaResponse.ContentDTO::getTags));
            for (int index = 0; index < fileMetadataResponse.getContent().size(); index++) {
                FileMetadataResponse fileMetadata = fileMetadataResponse.getContent().get(index);
                fileMetadata.setTags(tagMap.get(fileMetadata.getNeid()));
            }

            //获取被下载次数
            for (int index = 0; index < fileExtraMetaResponse.getContent().size(); index++) {
                FileMetadataResponse fileMetadata = fileMetadataResponse.getContent().get(index);
                fileMetadata.setDownloadNum(leaderboardService.findFileDownloadNum(fileMetadata.getNeid()));
            }
            //获取被索引次数
        }
        return TransBaseResponse.success(fileMetadataResponse);
    }
}
