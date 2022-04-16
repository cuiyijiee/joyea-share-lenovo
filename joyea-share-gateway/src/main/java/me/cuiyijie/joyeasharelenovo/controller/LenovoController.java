package me.cuiyijie.joyeasharelenovo.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.config.Constants;
import me.cuiyijie.joyeasharelenovo.model.DirWord;
import me.cuiyijie.joyeasharelenovo.model.v2.FtsSearchResultResponse;
import me.cuiyijie.trans.TransFileMetadataRequest;
import me.cuiyijie.joyeasharelenovo.model.v2.FileExtraMetaResponse;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import me.cuiyijie.joyeasharelenovo.service.AlbumSrcService;
import me.cuiyijie.joyeasharelenovo.service.DirWordService;
import me.cuiyijie.joyeasharelenovo.service.LeaderboardService;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV2Service;
import me.cuiyijie.trans.TransBaseResponse;
import me.cuiyijie.trans.TransFtsSearchRequest;
import me.cuiyijie.util.CheckParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("apiv2/lenovo")
public class LenovoController {

    @Autowired
    private OpenApiV2Service openApiV2Service;
    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private AlbumSrcService albumSrcService;
    @Autowired
    private DirWordService dirWordService;

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


            for (int index = 0; index < fileExtraMetaResponse.getContent().size(); index++) {
                //获取被下载次数
                FileMetadataResponse fileMetadata = fileMetadataResponse.getContent().get(index);
                fileMetadata.setDownloadNum(leaderboardService.findFileDownloadNum(fileMetadata.getNeid()));

                //获取被索引次数
                fileMetadata.setRefNum(albumSrcService.countByNeid(fileMetadata.getNeid()));
            }

            //添加小白板
            List<DirWord> dirWords = dirWordService.findByNeid(fileMetadataResponse.getNeid());
            for (DirWord dirWord : dirWords) {
                FileMetadataResponse dirWordMeta = new FileMetadataResponse();
                dirWordMeta.setNeid(dirWord.getWordId());
                dirWordMeta.setPath(dirWord.getWordName());
                dirWordMeta.setMimeType("word");
                fileMetadataResponse.getContent().add(dirWordMeta);
            }

            //对目录进行排序，对文件名称进行默认排序
            fileMetadataResponse.getContent().sort(Comparator.comparing(FileMetadataResponse::getFileName));
        }
        return TransBaseResponse.success(fileMetadataResponse);
    }

    @RequestMapping(value = "ftsSearch", method = RequestMethod.POST)
    public TransBaseResponse ftsSearch(@RequestBody TransFtsSearchRequest request) {
        List<String> paramsCheck = Lists.newArrayList("searchKey:搜索关键词（searchKey）", "offset:页码（offset）");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            TransBaseResponse transBaseResponse = new TransBaseResponse();
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }

        //记录到搜索排行榜
        leaderboardService.recordSearch(request.getSearchKey());
        //开始请求联想网盘接口
        FtsSearchResultResponse resultResponse = openApiV2Service.ftsSearch(request.getSearchKey(), request.getOffset());
        if (resultResponse.getContent() != null) {
            //过滤上传临时文件夹
            resultResponse.setContent(resultResponse.getContent()
                    .stream()
                    .filter(contentDTO -> !contentDTO.getPath().contains(Constants.UPLOAD_TEMP_DIR))
                    .collect(Collectors.toList()));

            for (int index = 0; index < resultResponse.getContent().size(); index++) {
                FtsSearchResultResponse.ContentDTO contentDTO = resultResponse.getContent().get(index);
                //获取被下载次数
                contentDTO.setDownloadNum(leaderboardService.findFileDownloadNum(contentDTO.getNeid()));
                //获取被索引次数
                contentDTO.setRefNum(albumSrcService.countByNeid(contentDTO.getNeid()));
            }
        }
        return TransBaseResponse.success(resultResponse);
    }
}
