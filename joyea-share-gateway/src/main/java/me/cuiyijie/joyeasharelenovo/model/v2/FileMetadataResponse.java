package me.cuiyijie.joyeasharelenovo.model.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.cuiyijie.joyeasharelenovo.model.JoyeaUser;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class FileMetadataResponse {

//    @JsonProperty("prefix_neid")
//    private String prefixNeid;
//    @JsonProperty("acctid")
//    private Long acctid;
//    @JsonProperty("from_name")
//    private String fromName;
//    @JsonProperty("is_team")
//    private Boolean isTeam;

    @JsonProperty("content")
    private List<FileMetadataResponse> content;

    //    @JsonProperty("result")
//    private String result;
    @JsonProperty("path")
    private String path;
    //    @JsonProperty("nsid")
//    private Long nsid;
//    @JsonProperty("is_deleted")
//    private Boolean isDeleted;
//    @JsonProperty("content_size")
//    private Long contentSize;
    @JsonProperty("is_dir")
    private Boolean isDir;

//    @JsonProperty("owner_uid")
//    private Long ownerUid;
//    @JsonProperty("is_shared")
//    private Boolean isShared;

//    @JsonProperty("modified")
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ",timezone = "GMT+8")
//    private OffsetDateTime modified;
//    @JsonProperty("creator_uid")
//    private Long creatorUid;
//    @JsonProperty("from")
//    private String from;
//    @JsonProperty("topSort")
//    private Long topSort;
//    @JsonProperty("owner")
//    private String owner;

    @JsonProperty("neid")
    private String neid;

//    @JsonProperty("folder_dc_type")
//    private Long folderDcType;
//    @JsonProperty("creator")
//    private String creator;
//    @JsonProperty("utime")
//    private Long utime;

    @JsonProperty("mime_type")
    private String mimeType;
    @JsonProperty("file_name")
    private String fileName;
    //    @JsonProperty("authable")
//    private Boolean authable;
//    @JsonProperty("region_id")
//    private Long regionId;
//    @JsonProperty("total_size")
//    private Long totalSize;
//    @JsonProperty("path_type")
//    private String pathType;
//    @JsonProperty("access_mode")
//    private Long accessMode;
//    @JsonProperty("modifiedUTime")
//    private Long modifiedUTime;
//    @JsonProperty("delivery_code")
//    private String deliveryCode;
//    @JsonProperty("size")
//    private String size;
//    @JsonProperty("updator_uid")
//    private Long updatorUid;
    @JsonProperty("bytes")
    private Long bytes;
//    @JsonProperty("updator")
//    private String updator;
//    @JsonProperty("share_to_personal")
//    private Boolean shareToPersonal;
//    @JsonProperty("hash")
//    private String hash;
//    @JsonProperty("access_restriction")
//    private Long accessRestriction;
    @JsonProperty("desc")
    private String desc;
//    @JsonProperty("createUTime")
//    private Long createUTime;

    private List<FileExtraMetaResponse.ContentDTO.TagsDTO> tags;

    @JsonProperty("download_num")
    private Long downloadNum;
    @JsonProperty("ref_num")
    private Long refNum;

    private List<JoyeaUser> adminUser;
}
