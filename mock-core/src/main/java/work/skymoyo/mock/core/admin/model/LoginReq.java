package work.skymoyo.mock.core.admin.model;

import lombok.Data;

@Data
public class LoginReq {

    private String username;
    private String password;
    private String code;
    private String uuid;

}
