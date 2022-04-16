package me.cuiyijie.joyeasharelenovo;

import me.cuiyijie.joyeasharelenovo.dao.AlbumSrcDao;
import me.cuiyijie.joyeasharelenovo.model.AlbumSrc;
import me.cuiyijie.joyeasharelenovo.model.v2.FileExtraMetaResponse;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import me.cuiyijie.joyeasharelenovo.service.LeaderboardService;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV2Service;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV3Service;
import me.cuiyijie.joyeasharelenovo.service.TranscodeVideoService;
import me.cuiyijie.joyeasharelenovo.xxljobhandler.SampleXxlJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class JoyeaShareLenovoApplicationTests {

    @Autowired
    private OpenApiV2Service openApiV2Service;

    @Autowired
    private OpenApiV3Service openApiV3Service;

    @Autowired
    private TranscodeVideoService transcodeVideoService;

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private AlbumSrcDao albumSrcDao;
    @Autowired
    private SampleXxlJob sampleXxlJob;

    @Test
    void contextLoads() {

        sampleXxlJob.syncLenovoDir();


//        AlbumSrc albumSrc = albumSrcDao.selectById(1785);
//        System.out.println(albumSrc);
        //        FileMetadataResponse response = openApiV2Service.getFileMetadata("/营销素材展示/STICK线/001、样品照片/002、保健品样品照片/001、保健品-颗粒剂");
//        System.out.println(response);
//
//        List<String> neids = response.getContent().stream().map(FileMetadataResponse.ContentDTO::getNeid).collect(Collectors.toList());
//        FileExtraMetaResponse response1 = openApiV2Service.getFileExtraMetadata(neids);
//        System.out.println(response1);
    }
}
