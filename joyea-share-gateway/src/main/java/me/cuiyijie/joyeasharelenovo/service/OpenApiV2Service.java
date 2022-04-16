package me.cuiyijie.joyeasharelenovo.service;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.config.Constants;
import me.cuiyijie.joyeasharelenovo.model.v2.FileExtraMetaResponse;
import me.cuiyijie.joyeasharelenovo.model.v2.FileMetadataResponse;
import me.cuiyijie.joyeasharelenovo.model.v2.LoginResponse;
import me.cuiyijie.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenApiV2Service {

    @Value("${lenovo.api.v2.client.user}")
    private String userName;
    @Value("${lenovo.api.v2.client.pwd}")
    private String userPwd;

    private static final String X_LEVOVO_SESS_ID = "X-LENOVO-SESS-ID";

    private static final String URL_LOGIN = "https://box.lenovo.com/v2/user/login";
    private static final String URL_FILE_METADATA = "https://box.lenovo.com/v2/metadata/root{path}?path_type={path_type}&X-LENOVO-SESS-ID={X-LENOVO-SESS-ID}";
    private static final String URL_FILE_EXTRA_METADATA = "https://console.box.lenovo.com/v2/extra_meta?neids={neids}&X-LENOVO-SESS-ID={X-LENOVO-SESS-ID}";

    @Autowired
    private RestTemplate restTemplate;

    private String accessToken;
    private Long expireAt;

    public String getAccessToken() {
        if (accessToken == null || System.currentTimeMillis() > expireAt) {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("user_slug", "email:" + userName);
            map.add("password", userPwd);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(map, headers);
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(URL_LOGIN, param, LoginResponse.class);
            LoginResponse tokenResponse = response.getBody();
            accessToken = tokenResponse.getXlenovosessid();
            log.info("刷新到联想网盘v2接口access token：{}",accessToken);
            //expireAt = tokenResponse.getPasswordexpire();
            expireAt = System.currentTimeMillis() + 30 * 60 * 1000;
            return accessToken;
        }
        return accessToken;
    }

    public FileMetadataResponse getFileMetadata(String path) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("path", StringUtils.makeUrl(path));
        uriVariables.put("path_type", Constants.DEFAULT_PATH_TYPE);
        uriVariables.put(X_LEVOVO_SESS_ID, getAccessToken());
        ResponseEntity<FileMetadataResponse> response = restTemplate.getForEntity(URL_FILE_METADATA, FileMetadataResponse.class,uriVariables);
        return response.getBody();
    }

    public FileExtraMetaResponse getFileExtraMetadata(List<String> neids) {
        String neidsParams = String.format("[%s]",String.join(",",neids));
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("neids", neidsParams);
        uriVariables.put(X_LEVOVO_SESS_ID, getAccessToken());
        ResponseEntity<FileExtraMetaResponse> response = restTemplate.getForEntity(URL_FILE_EXTRA_METADATA, FileExtraMetaResponse.class,uriVariables);
        return response.getBody();
    }
}
