package me.cuiyijie.joyeasharelenovo.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.model.SysDirectory;
import me.cuiyijie.joyeasharelenovo.service.SysDirectoryAdminService;
import me.cuiyijie.joyeasharelenovo.trans.TransPrivateDirRequest;
import me.cuiyijie.joyeasharelenovo.service.SysDirectoryService;
import me.cuiyijie.joyeasharelenovo.trans.TransBaseResponse;
import me.cuiyijie.joyeasharelenovo.service.SysSrcService;
import me.cuiyijie.util.CheckParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("apiv2/privateDir")
public class PrivateDirController {

    @Autowired
    private SysDirectoryService sysDirectoryService;
    @Autowired
    private SysDirectoryAdminService sysDirectoryAdminService;
    @Autowired
    private SysSrcService sysSrcService;

    @RequestMapping(path = "new", method = RequestMethod.POST)
    public TransBaseResponse newPrivateDir(@RequestBody SysDirectory sysDirectory) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("dirName:目录名称（dirName）");
        String errorMsg = CheckParamsUtil.checkAll(sysDirectory, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        return TransBaseResponse.success(sysDirectoryService.newSelfDirectory(sysDirectory.getParentDirId(), sysDirectory.getDirName()));
    }

    @RequestMapping(path = "rename", method = RequestMethod.POST)
    public TransBaseResponse renamePrivateDir(@RequestBody TransPrivateDirRequest request) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("dirId:目录id(dirId)", "newDirName:新文件夹名称(newDirName)");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        return TransBaseResponse.success(sysDirectoryService.rename(request.getDirId(), request.getNewDirName()));
    }

    @RequestMapping(path = "remove", method = RequestMethod.POST)
    public TransBaseResponse removePrivateDir(@RequestBody TransPrivateDirRequest transPrivateDirRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("dirId:目录id(dirId)");
        String errorMsg = CheckParamsUtil.checkAll(transPrivateDirRequest, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        sysDirectoryService.removeDirectory(transPrivateDirRequest.getDirId());
        return TransBaseResponse.success();
    }

    @RequestMapping(path = "newSrc", method = RequestMethod.POST)
    public TransBaseResponse newPrivateSrc(@RequestBody TransPrivateDirRequest request) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("srcPath:文件路径(srcPath)");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        return TransBaseResponse.success(sysSrcService.newSrc(request.getParentDirId(), request.getSrcPath()));
    }

    @RequestMapping(path = "batchNewSrc", method = RequestMethod.POST)
    public TransBaseResponse batchNewSrc(@RequestBody TransPrivateDirRequest request) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("srcPaths:文件路径列表(srcPaths)");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        sysSrcService.batchNewSrc(request.getParentDirId(), request.getSrcPaths());
        return TransBaseResponse.success();
    }

    @RequestMapping(path = "removeSrc", method = RequestMethod.POST)
    public TransBaseResponse removePrivateSrc(@RequestBody TransPrivateDirRequest request) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("parentDirId:父文件夹id(parentDirId)","neid:文件id(neid)");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        sysSrcService.removeSrc(request.getParentDirId(),request.getNeid());
        return TransBaseResponse.success();
    }

    @RequestMapping(path = "updateAdmin", method = RequestMethod.POST)
    public TransBaseResponse updateAdmin(@RequestBody TransPrivateDirRequest request) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("dirId:文件夹id(dirId)");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        sysDirectoryAdminService.updateDirectoryAdmins(request.getDirId(), request.getAdmins());
        return TransBaseResponse.success();
    }

    @RequestMapping(path = "listAdmin", method = RequestMethod.POST)
    public TransBaseResponse listAdmin(@RequestBody TransPrivateDirRequest request) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        List<String> paramsCheck = Lists.newArrayList("dirId:文件夹id(dirId)");
        String errorMsg = CheckParamsUtil.checkAll(request, paramsCheck, null, null);
        if (errorMsg != null) {
            log.error("参数检查错误：" + errorMsg);
            transBaseResponse.setCode("-1");
            transBaseResponse.setMsg(errorMsg);
            return transBaseResponse;
        }
        return TransBaseResponse.success(sysDirectoryAdminService.listDirectoryAdmin(request.getDirId()));
    }
}
