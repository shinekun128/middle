package cn.ponyzhang.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
