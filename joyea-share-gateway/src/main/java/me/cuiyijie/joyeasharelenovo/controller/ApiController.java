package me.cuiyijie.joyeasharelenovo.controller;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.model.RedirectPath;
import me.cuiyijie.joyeasharelenovo.service.ApiService;
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
    private ApiService apiService;

    @Autowired
    private RedirectPathService redirectPathService;

    @ResponseBody
    @RequestMapping("token")
    public String getToken() {
        return apiService.getAccessToken();
    }

    @ResponseBody
    @RequestMapping("preview")
    public String getFilePreviewUrl(@RequestParam(required = true) String neid,
                                    @RequestParam(required = false, defaultValue = "745477") String nsid) {
        return apiService.getFilePreviewUrl(neid, nsid);
    }

    @ResponseBody
    @RequestMapping("addRedirectPath")
    public RedirectPath addRedirectPath(@RequestParam(required = true) String path) {
        RedirectPath redirectPath = redirectPathService.addNewRedirectPath(path);
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
            return String.format("redirect:http://%s:%s/mobile/#redirect?path=%s",
                    serverName,
                    serverPort,
                    URLEncoder.encode(redirectPath.getPath()));
        } else {
            return String.format("redirect:http://%s:%s/#redirect?path=%s",
                    serverName,
                    serverPort,
                    URLEncoder.encode(redirectPath.getPath()));
        }
    }
}
