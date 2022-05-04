package me.cuiyijie.joyeasharelenovo.controller;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.trans.TransBaseResponse;
import me.cuiyijie.joyeasharelenovo.trans.TransJoyeaUserRequest;
import me.cuiyijie.joyeasharelenovo.service.JoyeaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("apiv2/joyeaUser")
public class JoyeaUserController {

    @Autowired
    private JoyeaUserService joyeaUserService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public TransBaseResponse list(@RequestBody TransJoyeaUserRequest request) {
        return TransBaseResponse.success(joyeaUserService.searchJoyeaUser(request.getJoyeaName()));
    }
}
