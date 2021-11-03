package cn.ponyzhang.server.mapper;

import cn.ponyzhang.server.entity.RedDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RedDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_detail
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_detail
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int insert(RedDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_detail
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    RedDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_detail
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    List<RedDetail> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table red_detail
     *
     * @mbg.generated Wed Nov 03 10:15:08 CST 2021
     */
    int updateByPrimaryKey(RedDetail record);
}