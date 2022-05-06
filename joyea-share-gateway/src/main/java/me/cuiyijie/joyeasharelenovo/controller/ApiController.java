package me.cuiyijie.joyeasharelenovo.controller;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.config.Constants;
import me.cuiyijie.joyeasharelenovo.enums.DirectoryType;
import me.cuiyijie.joyeasharelenovo.model.RedirectPath;
import me.cuiyijie.joyeasharelenovo.model.v3.PreviewResponse;
import me.cuiyijie.joyeasharelenovo.service.OpenApiV3Service;
import me.cuiyijie.joyeasharelenovo.service.RedirectPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequestMapping("apiv2")
public class ApiController {

    @Autowired
    private OpenApiV3Service openApiV3Service;

    @Autowired
    private RedirectPathService redirectPathService;

    @ResponseBody
    @RequestMapping("token")
    public String getToken() {
        return openApiV3Service.getAccessToken();
    }

    @ResponseBody
    @RequestMapping("preview")
    public String getFilePreviewUrl(@RequestParam(required = true) String neid,
                                    @RequestParam(required = false, defaultValue = "false") boolean thumbtail) {
        return openApiV3Service.getFilePreviewUrl(neid, Constants.DEFAULT_PATH_NSID, thumbtail);
    }

    @RequestMapping("imagePreview")
    public String imagePreview(@RequestParam(required = true) String neid,
                               @RequestParam(required = false, defaultValue = "false") boolean thumbtail) {
        String previewResponse = openApiV3Service.getFilePreviewUrl(neid, Constants.DEFAULT_PATH_NSID, thumbtail);
        return String.format("redirect:%s", previewResponse);
    }

    @ResponseBody
    @RequestMapping("addRedirectPath")
    public RedirectPath addRedirectPath(@RequestParam(required = true) String path,
                                        @RequestParam(required = false, defaultValue = "LENOVO") String type) {
        RedirectPath redirectPath = redirectPathService.addNewRedirectPath(path, DirectoryType.valueOf(type));
        return redirectPath;
    }

    @RequestMapping("redirectPath")
    public String redirectPath(HttpServletRequest request, Device device, @RequestParam(required = true) Long id) {

        String serverName = request.getServerName();
        //String serverName = "192.168.1.35";
        int serverPort = request.getServerPort();
        //int serverPort = 8083;

        RedirectPath redirectPath = redirectPathService.queryPathById(id);
        if (device.isMobile()) {
            return String.format("redirect:http://%s:%s/mobile/#redirect?path=%s&type=%s",
                    serverName,
                    serverPort,
                    URLEncoder.encode(redirectPath.getPath()),
                    URLEncoder.encode(redirectPath.getDirectoryType().toString())
            );
        } else {
            return String.format("redirect:http://%s:%s/#redirect?path=%s&type=%s",
                    serverName,
                    serverPort,
                    URLEncoder.encode(redirectPath.getPath()),
                    URLEncoder.encode(redirectPath.getDirectoryType().toString())
            );
        }
    }
}
