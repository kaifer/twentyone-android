package com.kaifer.fasttwentyone.api.models.req;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class AuthenticateForm {
    private String accessToken;
    private String deviceUid;

    public AuthenticateForm() {

    }

    public AuthenticateForm(String accessToken, String deviceUid) {
        this.accessToken = accessToken;
        this.deviceUid = deviceUid;
    }
}
