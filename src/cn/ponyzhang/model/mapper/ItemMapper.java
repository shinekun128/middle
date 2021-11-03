package cn.ponyzhang.model.mapper;

import cn.ponyzhang.model.entity.Item;
import cn.ponyzhang.model.entity.ItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    long countByExample(ItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int deleteByExample(ItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int insert(Item record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int insertSelective(Item record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    List<Item> selectByExample(ItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    Item selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int updateByExampleSelective(@Param("record") Item record, @Param("example") ItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int updateByExample(@Param("record") Item record, @Param("example") ItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int updateByPrimaryKeySelective(Item record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Nov 02 19:35:03 CST 2021
     */
    int updateByPrimaryKey(Item record);
}