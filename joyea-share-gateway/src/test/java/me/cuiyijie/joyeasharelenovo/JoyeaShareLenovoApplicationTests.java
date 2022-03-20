package me.cuiyijie.joyeasharelenovo;

import me.cuiyijie.joyeasharelenovo.model.FileListResponse;
import me.cuiyijie.joyeasharelenovo.service.ApiService;
import me.cuiyijie.joyeasharelenovo.service.TranscodeVideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JoyeaShareLenovoApplicationTests {

    @Autowired
    private ApiService apiService;

    @Autowired
    private TranscodeVideoService transcodeVideoService;

    @Test
    void contextLoads() {

        //transcodeVideoService.insert("1129077471", "745477");
    }


}
