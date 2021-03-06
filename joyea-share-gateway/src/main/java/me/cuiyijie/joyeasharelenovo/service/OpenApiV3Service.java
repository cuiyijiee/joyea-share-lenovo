package me.cuiyijie.joyeasharelenovo.service;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.joyeasharelenovo.model.v3.FileInfoResponse;
import me.cuiyijie.joyeasharelenovo.model.v3.FileListResponse;
import me.cuiyijie.joyeasharelenovo.model.v3.PreviewResponse;
import me.cuiyijie.joyeasharelenovo.model.v3.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;


@Slf4j
@Service
public class OpenApiV3Service {

    @Value("${lenovo.api.v3.client.id}")
    private String clientId;
    @Value("${lenovo.api.v3.client.secret}")
    private String clientSecret;
    @Value("${lenovo.api.v3.slug}")
    private String userSlug;

    @Autowired
    private RestTemplate restTemplate;

    private final static String TOKEN_URL = "https://api.zbox.filez.com/v3/oauth/token";
    private final static String PREVIEW_URL = "https://api.zbox.filez.com/v3/api/preview/";
    private final static String FILE_LIST_URL = "https://api.zbox.filez.com/v3/api/file";
    private final static String FILE_PREVIEW_URL = "https://api.zbox.filez.com/v3/api/preview/{neid}?nsid={nsid}&thumbtail={thumbtail}&width={width}&height={height}";

    private String accessToken;
    private Long expiresAt;

    @Synchronized
    public String getAccessToken() {
        if (accessToken == null || expiresAt == null || System.currentTimeMillis() > expiresAt) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", String.format("Basic %s", new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()))));
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "client_with_su");
            map.add("scope", "all");
            map.add("slug", userSlug);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(map, headers);
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(TOKEN_URL, param, TokenResponse.class);
            TokenResponse tokenResponse = response.getBody();
            accessToken = tokenResponse.getAccessToken();
            log.info("?????????????????????v3??????access???{}", accessToken);
            expiresAt = System.currentTimeMillis() + tokenResponse.getExpiresIn() * 1000;
            return accessToken;
        }
        return accessToken;
    }

    public String getFilePreviewUrl(String neid, String nsid, Boolean thumbtail) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", getAccessToken()));
        String requestUrl = String.format("%s%s?nsid=%s&thumbtail=%s&width=300&height=300", PREVIEW_URL, neid, nsid, thumbtail);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<PreviewResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, PreviewResponse.class);
        PreviewResponse previewResponse = response.getBody();
        return previewResponse.getPreviewUrl();
    }

    public FileListResponse getFileList(String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", getAccessToken()));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("path", path);
        map.add("path_type", "ent");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(map, headers);
        ResponseEntity<FileListResponse> response = restTemplate.postForEntity(FILE_LIST_URL, param, FileListResponse.class);
        return response.getBody();
    }

    public FileInfoResponse getFileInfo(String neid, String nsid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", getAccessToken()));
        String requestUrl = String.format("%s/%s?nsid=%s", FILE_LIST_URL, neid, nsid);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<FileInfoResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, FileInfoResponse.class);
        return response.getBody();
    }
}
