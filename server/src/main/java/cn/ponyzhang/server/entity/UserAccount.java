package cn.ponyzhang.server.entity;

import java.math.BigDecimal;

public class UserAccount {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_account.id
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_account.user_id
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_account.amount
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    private BigDecimal amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_account.version
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    private Integer version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_account.is_active
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    private Byte isActive;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_account.id
     *
     * @return the value of user_account.id
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_account.id
     *
     * @param id the value for user_account.id
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_account.user_id
     *
     * @return the value of user_account.user_id
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_account.user_id
     *
     * @param userId the value for user_account.user_id
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_account.amount
     *
     * @return the value of user_account.amount
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_account.amount
     *
     * @param amount the value for user_account.amount
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_account.version
     *
     * @return the value of user_account.version
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_account.version
     *
     * @param version the value for user_account.version
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_account.is_active
     *
     * @return the value of user_account.is_active
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public Byte getIsActive() {
        return isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_account.is_active
     *
     * @param isActive the value for user_account.is_active
     *
     * @mbg.generated Mon Nov 08 21:14:13 CST 2021
     */
    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }
}