package me.cuiyijie.joyeasharelenovo.controller;

import me.cuiyijie.joyeasharelenovo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping("token")
    public String getToken() {
        return apiService.getAccessToken();
    }

    @RequestMapping("preview")
    public String getFilePreviewUrl(@RequestParam(required = true) String neid,
                                    @RequestParam(required = false, defaultValue = "745477") String nsid) {
        return apiService.getFilePreviewUrl(neid,nsid);
    }
}
