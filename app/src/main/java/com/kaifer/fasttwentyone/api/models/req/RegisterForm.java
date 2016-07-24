package com.kaifer.fasttwentyone.api.models.req;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class RegisterForm {
    private String registerToken;

    public RegisterForm() {

    }

    public RegisterForm(String registerToken) {
        this.registerToken = registerToken;
    }
}
