package cn.ponyzhang.server.config.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhoneUser implements Serializable {
    private String phoneNum;
    private long money;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneUser phoneUser = (PhoneUser) o;
        return phoneNum.equals(phoneUser.phoneNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNum);
    }
}
