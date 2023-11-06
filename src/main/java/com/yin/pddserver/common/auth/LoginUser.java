package com.yin.pddserver.common.auth;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    private Date createDate = new Date();

    private String platform;

    private String id;

    private String username;

    private String tnId;

    private String secret;


    public LoginUser(String platform, String id, String username, String tnId, String secret) {
        this.createDate = new Date();
        this.platform = platform;
        this.id = id;
        this.username = username;
        this.tnId = tnId;
        this.secret = secret;
    }

    public String getUploadRedisKey() {
        return "importcvs:" + tnId + ":" + id + ":";
    }
}
