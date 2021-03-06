package cn.ponyzhang.server.mapper;

import cn.ponyzhang.server.entity.UserAccountRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAccountRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_account_record
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_account_record
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    int insert(UserAccountRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_account_record
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    UserAccountRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_account_record
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    List<UserAccountRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_account_record
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    int updateByPrimaryKey(UserAccountRecord record);
}