package me.cuiyijie.joyeasharelenovo;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.dao.AlbumSrcDao;
import me.cuiyijie.joyeasharelenovo.dao.SysDirectoryDao;
import me.cuiyijie.joyeasharelenovo.model.JoyeaUser;
import me.cuiyijie.joyeasharelenovo.service.*;
import me.cuiyijie.joyeasharelenovo.xxljobhandler.SampleXxlJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
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
    @Autowired
    private SysDirectoryService sysDirectoryService;
    @Autowired
    private SysSrcService sysSrcService;
    @Autowired
    private SysDirectoryAdminService sysDirectoryAdminService;
    @Autowired
    SysDirectoryDao sysDirectoryDao;

    @Test
    void contextLoads() {

        sysDirectoryAdminService.updateDirectoryAdmins("123", Lists.newArrayList("584","585"));
        List<JoyeaUser> joyeaUsers = sysDirectoryAdminService.listDirectoryAdmin("123");
        System.out.println(joyeaUsers);

        //sysDirectoryDao.updateDirectoryPrefix("/测试数据1/","/测试目录/");
        //sysSrcService.newSrc("","/营销素材展示/STICK线/001、样品照片/002、保健品样品照片/001、保健品-颗粒剂/001、生合生物（益生菌固体饮料）/生合生物-益生菌固体饮料20161207.jpg");

        //sysDirectoryService.rename("1520816590010773505","新目录3");

        //sampleXxlJob.syncLenovoDir();

//        AlbumSrc albumSrc = albumSrcDao.selectById(1785);
//        System.out.println(albumSrc);
        //        FileMetadataResponse response = openApiV2Service.getFileMetadata("/营销素材展示/STICK线/001、样品照片/002、保健品样品照片/001、保健品-颗粒剂");
//        System.out.println(response);
//
//        List<String> neids = response.getContent().stream().map(FileMetadataResponse.ContentDTO::getNeid).collect(Collectors.toList());
//        FileExtraMetaResponse response1 = openApiV2Service.getFileExtraMetadata(neids);
//        System.out.println(response1);

//        SrcDirectory srcDirectory = srcDirService.newSelfDirectory("1520766474985955329", "测试子目录");
//        log.info("insert new dir: {}", srcDirectory);

//        List<SysDirectory> srcDirectories = sysDirService.listSelfDir("1520795902482866178");
//        log.info("get root dir: {}", srcDirectories);
////        List<SrcDirectory> childDirs = srcDirService.listSelfDir(srcDirectories.get(0).getId());
////        log.info("get child dir: {}", childDirs);
//        List<SysDirectory> childDirs = sysDirService.findParentDir(srcDirectories.get(0));
//        log.info("get parent dir: {}", childDirs);
    }
}
