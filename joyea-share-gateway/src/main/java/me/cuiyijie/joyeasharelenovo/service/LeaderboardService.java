package me.cuiyijie.joyeasharelenovo.service;

import me.cuiyijie.joyeasharelenovo.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Double recordUpload(String uploadJoyeaId) {
        return redisTemplate.opsForZSet().incrementScore(Constants.KEY_UPLOAD_RANK, uploadJoyeaId, 1);
    }

    public Double recordSearch(String searchKey) {
        return redisTemplate.opsForZSet().incrementScore(Constants.KEY_SEARCH_RANK, searchKey, 1);
    }

    public Map<String, Double> getSearchTopList(int top) {
        Set<ZSetOperations.TypedTuple<String>> queryResult = redisTemplate.opsForZSet().reverseRangeWithScores(Constants.KEY_SEARCH_RANK, 0, top - 1);
        Assert.notNull(queryResult,"获取搜索热词失败");
        return queryResult.stream().collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    public void recordFileRef(String neid) {
        redisTemplate.opsForHash().increment(Constants.KEY_FILE_REF_RANK, neid, 1);
    }

    public Integer findFileRefNum(String neid) {
        return Integer.parseInt((String) Optional.ofNullable(redisTemplate.opsForHash().get(Constants.KEY_FILE_REF_RANK, neid)).orElse("0"));
    }

    public void recordFileDownload(String neid) {
        redisTemplate.opsForHash().increment(Constants.KEY_FILE_DOWNLOAD_RANK,neid,1);

    }

    public Integer findFileDownloadNum(String neid) {
        return Integer.parseInt((String) Optional.ofNullable(redisTemplate.opsForHash().get(Constants.KEY_FILE_DOWNLOAD_RANK, neid)).orElse("0"));
    }
}
