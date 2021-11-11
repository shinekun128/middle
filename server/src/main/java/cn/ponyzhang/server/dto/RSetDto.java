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
public class RSetDto implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Double score;

    public RSetDto(Integer id,String name,Double score){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public RSetDto(Integer id,String name,Integer age){
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
