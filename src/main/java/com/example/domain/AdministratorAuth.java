package com.example.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 管理者情報を権限情報とセットで扱うためのドメイン.
 *
 * @author krkrHotaru
 */

public class AdministratorAuth extends User {

    private Administrator administrator;

    public AdministratorAuth(Administrator administrator, Collection<GrantedAuthority> authorityList) {
        super(administrator.getMailAddress(), administrator.getPassword(), authorityList);
        this.administrator = administrator;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public String toString() {
        return "AdministratorAuth{" +
                "administrator=" + administrator +
                '}';
    }
}
