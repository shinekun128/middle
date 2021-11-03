package cn.ponyzhang.server.mapper;

import cn.ponyzhang.server.entity.RedRobRecord;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class RedRobRecordMapperTest {
    @Autowired
    private RedRobRecordMapper redRobRecordMapper;

    @Test
    void deleteByPrimaryKey() {

    }

    @Test
    void insert() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void selectAll() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}