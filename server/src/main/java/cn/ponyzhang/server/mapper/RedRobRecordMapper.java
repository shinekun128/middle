package cn.ponyzhang.server.mapper;

import cn.ponyzhang.server.entity.RedRobRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RedRobRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_rob_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_rob_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int insert(RedRobRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_rob_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    RedRobRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_rob_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    List<RedRobRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_rob_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int updateByPrimaryKey(RedRobRecord record);
}