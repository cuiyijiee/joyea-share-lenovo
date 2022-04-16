package me.cuiyijie.joyeasharelenovo.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginResponse {

    @JsonProperty("delivery_support")
    private Boolean deliverySupport;
    @JsonProperty("role")
    private String role;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("is_oversea")
    private Boolean isOversea;
    @JsonProperty("profile_color")
    private String profileColor;
    @JsonProperty("used")
    private Integer used;
    @JsonProperty("mtime")
    private Long mtime;
    @JsonProperty("second_auth_type")
    private Integer secondAuthType;
    @JsonProperty("need_auth")
    private Boolean needAuth;
    @JsonProperty("uid")
    private Integer uid;
    @JsonProperty("mobile_chk")
    private Boolean mobileChk;
    @JsonProperty("is_overseaAccelerate")
    private Boolean isOverseaaccelerate;
    @JsonProperty("quota")
    private Long quota;
    @JsonProperty("from_domain_account")
    private Boolean fromDomainAccount;
    @JsonProperty("ctime")
    private String ctime;
    @JsonProperty("second_auth")
    private Boolean secondAuth;
    @JsonProperty("X-LENOVO-SESS-ID")
    private String xlenovosessid;
    @JsonProperty("email")
    private String email;
    @JsonProperty("passwordexpire")
    private Long passwordexpire;
    @JsonProperty("user_slug")
    private String userSlug;
    @JsonProperty("email_chk")
    private Boolean emailChk;
    @JsonProperty("firstlogin")
    private Integer firstlogin;
    @JsonProperty("is_private_dc")
    private Boolean isPrivateDc;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("password_changeable")
    private Boolean passwordChangeable;
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("delay")
    private Boolean delay;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("preview_support")
    private Boolean previewSupport;
    @JsonProperty("disable_metadata_cache")
    private Boolean disableMetadataCache;
    @JsonProperty("location")
    private String location;
    @JsonProperty("docs_online_edit")
    private String docsOnlineEdit;
    @JsonProperty("status")
    private Integer status;
}
