package me.cuiyijie.joyeasharelenovo.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 22:30
 */
@NoArgsConstructor
@Data
public class FtsSearchResultResponse {

    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("next_offset")
    private Long nextOffset;
    @JsonProperty("content")
    private List<ContentDTO> content;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("status")
    private Long status;

    @NoArgsConstructor
    @Data
    public static class ContentDTO {
        @JsonProperty("prefix_neid")
        private String prefixNeid;
        @JsonProperty("fileindex")
        private Long fileindex;
        @JsonProperty("acctid")
        private Long acctid;
        @JsonProperty("desc")
        private String desc;
        @JsonProperty("from_name")
        private String fromName;
        @JsonProperty("result")
        private String result;
        @JsonProperty("path")
        private String path;
        @JsonProperty("nsid")
        private Long nsid;
        @JsonProperty("is_deleted")
        private Boolean isDeleted;
        @JsonProperty("has_docs_lock")
        private Boolean hasDocsLock;
        @JsonProperty("is_dir")
        private Boolean isDir;
        @JsonProperty("owner_uid")
        private Long ownerUid;
        @JsonProperty("is_shared")
        private Boolean isShared;
        @JsonProperty("modified")
        private String modified;
        @JsonProperty("creator_uid")
        private Long creatorUid;
        @JsonProperty("from")
        private String from;
        @JsonProperty("topSort")
        private Long topSort;
        @JsonProperty("owner")
        private String owner;
        @JsonProperty("protection_status")
        private String protectionStatus;
        @JsonProperty("neid")
        private String neid;
        @JsonProperty("folder_dc_type")
        private Long folderDcType;
        @JsonProperty("creator")
        private String creator;
        @JsonProperty("rev")
        private String rev;
        @JsonProperty("utime")
        private Long utime;
        @JsonProperty("authable")
        private Boolean authable;
        @JsonProperty("region_id")
        private Long regionId;
        @JsonProperty("support_preview")
        private String supportPreview;
        @JsonProperty("path_type")
        private String pathType;
        @JsonProperty("thumb_exist")
        private Boolean thumbExist;
        @JsonProperty("access_mode")
        private Long accessMode;
        @JsonProperty("modifiedUTime")
        private Long modifiedUTime;
        @JsonProperty("lock_uid")
        private Long lockUid;
        @JsonProperty("rev_index")
        private Long revIndex;
        @JsonProperty("delivery_code")
        private String deliveryCode;
        @JsonProperty("size")
        private String size;
        @JsonProperty("updator_uid")
        private Long updatorUid;
        @JsonProperty("mime_type")
        private String mimeType;
        @JsonProperty("bytes")
        private Long bytes;
        @JsonProperty("updator")
        private String updator;
        @JsonProperty("hash")
        private String hash;
        @JsonProperty("access_restriction")
        private Long accessRestriction;
        @JsonProperty("filename")
        private String filename;
        @JsonProperty("download_num")
        private Long downloadNum;
        @JsonProperty("ref_num")
        private Long refNum;
        private String tags;
    }
}
