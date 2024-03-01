package com.doubao.backend.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -65018483595438581L;

    private String userAccount;

    private String userPassword;
}
