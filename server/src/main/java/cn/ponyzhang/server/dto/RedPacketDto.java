package cn.ponyzhang.server.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
public class RedPacketDto {

    private Integer userId;

    @NotNull(message = "红包数量不能为空")
    private Integer total;

    @NotNull(message = "红包金额不能为空")
    private Integer amount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
