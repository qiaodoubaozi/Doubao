package com.doubao.backend.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3401764855845570567L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}
