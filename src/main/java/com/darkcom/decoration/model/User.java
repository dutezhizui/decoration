package com.darkcom.decoration.model;

import java.util.Date;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.USER_ID
     *
     * @mbg.generated
     */
    private Long userId;

    private String account;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.USER_NAME
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.PASSWORD
     *
     * @mbg.generated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.PHONE
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.ID_IMG_URL
     *
     * @mbg.generated
     */
    private String idImgUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.HEAD_URL
     *
     * @mbg.generated
     */
    private String headUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.AGE
     *
     * @mbg.generated
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.USER_TYPE
     *
     * @mbg.generated
     */
    private String userType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.STATUS
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.EMAIL
     *
     * @mbg.generated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.NICKNAME
     *
     * @mbg.generated
     */
    private String nickname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.SEX
     *
     * @mbg.generated
     */
    private String sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TBL_USER.LAST_LOGIN_TIME
     *
     * @mbg.generated
     */
    private Date lastLoginTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.USER_ID
     *
     * @return the value of TBL_USER.USER_ID
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.USER_ID
     *
     * @param userId the value for TBL_USER.USER_ID
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.USER_NAME
     *
     * @return the value of TBL_USER.USER_NAME
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.USER_NAME
     *
     * @param userName the value for TBL_USER.USER_NAME
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.PASSWORD
     *
     * @return the value of TBL_USER.PASSWORD
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.PASSWORD
     *
     * @param password the value for TBL_USER.PASSWORD
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.PHONE
     *
     * @return the value of TBL_USER.PHONE
     * @mbg.generated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.PHONE
     *
     * @param phone the value for TBL_USER.PHONE
     * @mbg.generated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.ID_IMG_URL
     *
     * @return the value of TBL_USER.ID_IMG_URL
     * @mbg.generated
     */
    public String getIdImgUrl() {
        return idImgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.ID_IMG_URL
     *
     * @param idImgUrl the value for TBL_USER.ID_IMG_URL
     * @mbg.generated
     */
    public void setIdImgUrl(String idImgUrl) {
        this.idImgUrl = idImgUrl == null ? null : idImgUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.HEAD_URL
     *
     * @return the value of TBL_USER.HEAD_URL
     * @mbg.generated
     */
    public String getHeadUrl() {
        return headUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.HEAD_URL
     *
     * @param headUrl the value for TBL_USER.HEAD_URL
     * @mbg.generated
     */
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.AGE
     *
     * @return the value of TBL_USER.AGE
     * @mbg.generated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.AGE
     *
     * @param age the value for TBL_USER.AGE
     * @mbg.generated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.USER_TYPE
     *
     * @return the value of TBL_USER.USER_TYPE
     * @mbg.generated
     */
    public String getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.USER_TYPE
     *
     * @param userType the value for TBL_USER.USER_TYPE
     * @mbg.generated
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.CREATE_TIME
     *
     * @return the value of TBL_USER.CREATE_TIME
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.CREATE_TIME
     *
     * @param createTime the value for TBL_USER.CREATE_TIME
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.UPDATE_TIME
     *
     * @return the value of TBL_USER.UPDATE_TIME
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.UPDATE_TIME
     *
     * @param updateTime the value for TBL_USER.UPDATE_TIME
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.STATUS
     *
     * @return the value of TBL_USER.STATUS
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.STATUS
     *
     * @param status the value for TBL_USER.STATUS
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.EMAIL
     *
     * @return the value of TBL_USER.EMAIL
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.EMAIL
     *
     * @param email the value for TBL_USER.EMAIL
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.NICKNAME
     *
     * @return the value of TBL_USER.NICKNAME
     * @mbg.generated
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.NICKNAME
     *
     * @param nickname the value for TBL_USER.NICKNAME
     * @mbg.generated
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TBL_USER.LAST_LOGIN_TIME
     *
     * @return the value of TBL_USER.LAST_LOGIN_TIME
     * @mbg.generated
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TBL_USER.LAST_LOGIN_TIME
     *
     * @param lastLoginTime the value for TBL_USER.LAST_LOGIN_TIME
     * @mbg.generated
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}