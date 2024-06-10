package com.example.form;

import java.time.LocalDate;

/**
 * 従業員登録情報の入力を受け取るフォーム.
 *
 * @author krkrHotaru
 */

public class InsertEmployeeForm {
    /** 従業員名 */
    private String name;
    /** 写真 */
    private String image;
    /** 性別 */
    private String gender;
    /** 入社日 */
    private String hireDate;
    /** メールアドレス */
    private String mailAddress;
    /** 郵便番号 */
    private String zipCode;
    /** 住所 */
    private String address;
    /** 電話番号 */
    private String telephone;
    /** 給与 */
    private Integer salary;
    /** 特徴 */
    private String characteristics;
    /** 扶養人数 */
    private Integer dependentsCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public Integer getDependentsCount() {
        return dependentsCount;
    }

    public void setDependentsCount(Integer dependentsCount) {
        this.dependentsCount = dependentsCount;
    }

    @Override
    public String toString() {
        return "InsertEmployeeForm{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                ", hireDate=" + hireDate +
                ", mailAddress='" + mailAddress + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", salary=" + salary +
                ", characteristics='" + characteristics + '\'' +
                ", dependentsCount=" + dependentsCount +
                '}';
    }
}
