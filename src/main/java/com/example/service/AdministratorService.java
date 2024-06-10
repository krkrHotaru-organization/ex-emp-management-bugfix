package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
		administratorRepository.insert(administrator);
		System.out.println(administrator.getPassword());
	}

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String password) {
		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
		if (passwordEncoder.matches(password, administrator.getPassword())) {
			return administrator;
		} else {
			return null;
		}
	}

	/**
	 * メールが管理者登録情報に存在するか検索.
	 *
	 * @param mailAddress 管理者情報として登録を試みているメールアドレス
	 * @return メールアドレスが存在した場合はtrueを返す
	 */
	public boolean isExistMail(String mailAddress){
		if (administratorRepository.findByMailAddress(mailAddress) != null){
			return true;
		}
		return false;
	}
}
