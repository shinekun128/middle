package cn.ponyzhang.server.mapper;

import cn.ponyzhang.server.entity.RedRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RedRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int insert(RedRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    RedRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    List<RedRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_record
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int updateByPrimaryKey(RedRecord record);

    /**
     * 根据红包唯一标识查询主键
     * @param redId 红包唯一标识
     * @return
     */
    int selectPrimaryKeyByRedId(String redId);
}