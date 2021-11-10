package cn.ponyzhang.server.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ToString
public class UserLoginDto implements Serializable {
    @NotEmpty
    @NotNull
    private String userName;
    @NotNull
    @NotEmpty
    private String password;

    private Integer id;
}
