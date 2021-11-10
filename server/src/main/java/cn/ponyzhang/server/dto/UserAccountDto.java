package cn.ponyzhang.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

    @NotNull
    private Integer userId;
    @NotNull
    private double money;
}
