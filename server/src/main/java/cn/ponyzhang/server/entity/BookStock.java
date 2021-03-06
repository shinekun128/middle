package cn.ponyzhang.server.entity;

import java.util.Date;

public class BookStock {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_stock.id
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_stock.book_no
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    private String bookNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_stock.stock
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    private Integer stock;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_stock.is_active
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    private Byte isActive;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_stock.create_time
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_stock.id
     *
     * @return the value of book_stock.id
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_stock.id
     *
     * @param id the value for book_stock.id
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_stock.book_no
     *
     * @return the value of book_stock.book_no
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public String getBookNo() {
        return bookNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_stock.book_no
     *
     * @param bookNo the value for book_stock.book_no
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public void setBookNo(String bookNo) {
        this.bookNo = bookNo == null ? null : bookNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_stock.stock
     *
     * @return the value of book_stock.stock
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_stock.stock
     *
     * @param stock the value for book_stock.stock
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_stock.is_active
     *
     * @return the value of book_stock.is_active
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public Byte getIsActive() {
        return isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_stock.is_active
     *
     * @param isActive the value for book_stock.is_active
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_stock.create_time
     *
     * @return the value of book_stock.create_time
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_stock.create_time
     *
     * @param createTime the value for book_stock.create_time
     *
     * @mbg.generated Wed Nov 10 15:20:22 CST 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}