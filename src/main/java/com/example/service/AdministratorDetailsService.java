package com.example.service;

import com.example.domain.Administrator;
import com.example.domain.AdministratorAuth;
import com.example.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ログインに成功したユーザに管理者権限を与えるサービスクラス.
 *
 * @author krkrHotaru
 */

public class AdministratorDetailsService implements UserDetailsService {

    /** DBから情報を得るためのリポジトリ */
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email=" + email);
        Administrator administrator = administratorRepository.findByMailAddress(email);
        if (administrator == null) {
            throw new UsernameNotFoundException("登録にないEmailです。");
        }

        Collection<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); //ROLE_ADMINと言う名前で認可が保存される

        return new AdministratorAuth(administrator,authorityList);
    }
}
