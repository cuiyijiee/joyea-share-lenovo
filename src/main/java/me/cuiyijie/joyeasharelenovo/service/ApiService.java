package me.cuiyijie.joyeasharelenovo.service;

import me.cuiyijie.joyeasharelenovo.model.FileListResponse;
import me.cuiyijie.joyeasharelenovo.model.PreviewResponse;
import me.cuiyijie.joyeasharelenovo.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class ApiService {

    @Value("${lenovo.api.client.id}")
    private String clientId;
    @Value("${lenovo.api.client.secret}")
    private String clientSecret;
    @Value("${lenovo.api.slug}")
    private String userSlug;

    @Autowired
    private RestTemplate restTemplate;

    private final static String TOKEN_URL = "https://api.zbox.filez.com/v3/oauth/token";
    private final static String PREVIEW_URL = "https://api.zbox.filez.com/v3/api/preview/";
    private final static String FILE_LIST_URL = "https://api.zbox.filez.com/v3/api/file";

    private String accessToken;
    private Long expiresAt;

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
            expiresAt = System.currentTimeMillis() + tokenResponse.getExpiresIn();
            return accessToken;
        }
        return accessToken;
    }

    public String getFilePreviewUrl(String neid, String nsid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("bearer %s", getAccessToken()));
        String requestUrl = String.format("%s%s?nsid=%s", PREVIEW_URL, neid, nsid);
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

}
