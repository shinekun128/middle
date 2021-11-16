package cn.ponyzhang.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PraiseRankDto implements Serializable {
    private Integer blogId;
    private Integer total;
}
