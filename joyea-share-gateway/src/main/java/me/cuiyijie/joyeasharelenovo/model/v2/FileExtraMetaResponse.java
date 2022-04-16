package me.cuiyijie.joyeasharelenovo.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FileExtraMetaResponse {

    @JsonProperty("content")
    private List<ContentDTO> content;

    @NoArgsConstructor
    @Data
    public static class ContentDTO {
        @JsonProperty("comment_num")
        private Integer commentNum;
        @JsonProperty("bookmark_id")
        private Integer bookmarkId;
        @JsonProperty("neid")
        private String neid;
        @JsonProperty("isSummary")
        private Boolean isSummary;
        @JsonProperty("tags")
        private List<TagsDTO> tags;

        @NoArgsConstructor
        @Data
        public static class TagsDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("id")
            private Integer id;
            @JsonProperty("type")
            private Integer type;
            @JsonProperty("originName")
            private String originName;
        }
    }
}
