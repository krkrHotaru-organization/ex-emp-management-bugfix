package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * 従業員登録情報の入力を受け取るフォーム.
 *
 * @author krkrHotaru
 */

public class InsertEmployeeForm {
    /** 従業員名 */
    @NotBlank(message = "名前は必須入力です")
    private String name;
    /** 写真 */
    private MultipartFile image;
    /** 性別 */
    @NotBlank(message = "性別を選択してください")
    private String gender;
    /** 入社日 */
    @NotBlank(message = "入社日は必須入力です")
    private String hireDate;
    /** メールアドレス */
    @NotBlank(message = "メールアドレスは必須入力です")
    @Email(message = "メールアドレスの形式で入力してください")
    private String mailAddress;
    /** 郵便番号 */
    private String zipCode;
    /** 住所 */
    private String address;
    /** 電話番号 */
    private String telephone;
    /** 給与 */
    @NotNull(message = "給与は必須入力です")
    private Integer salary;
    /** 特徴 */
    private String characteristics;
    /** 扶養人数 */
    @NotNull(message = "扶養人数を入力してください")
    private Integer dependentsCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
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
