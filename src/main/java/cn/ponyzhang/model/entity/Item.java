package cn.ponyzhang.model.entity;

import java.util.Date;

public class Item {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item.id
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item.code
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item.name
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item.create_time
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item.id
     *
     * @return the value of item.id
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item.id
     *
     * @param id the value for item.id
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item.code
     *
     * @return the value of item.code
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item.code
     *
     * @param code the value for item.code
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item.name
     *
     * @return the value of item.name
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item.name
     *
     * @param name the value for item.name
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item.create_time
     *
     * @return the value of item.create_time
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item.create_time
     *
     * @param createTime the value for item.create_time
     *
     * @mbg.generated Tue Nov 02 19:31:04 CST 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}