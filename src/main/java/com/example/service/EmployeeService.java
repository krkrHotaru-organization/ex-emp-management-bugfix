package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	/**
	 * 従業員情報の名前であいまい検索を行い、指定行・指定開始位置で情報を取得します.
	 *
	 * @param searchWord 入力された名前
	 * @param rowNum ほしい行数
	 * @param page 従業員情報取得開始位置
	 * @return 検索結果の従業員情報一覧
	 */
	public List<Employee> fuzzySearchByName(String searchWord,Integer rowNum,Integer page){
		int offset = page == null ? 0 : (page - 1) * 10;
		return employeeRepository.findByNameFuzzy(searchWord,rowNum,offset);
	}

	/**
	 * 全従業員情報の行数を取得します.
	 * 検索結果が0件だった場合は int = 0 を返します。
	 *
	 * @return 全従業員数
	 */
	public int countAllRow(){
		Integer result = employeeRepository.countAllRow();
		if (result == null){
			return 0;
		}
		return result;
	}

	/**
	 * あいまい検索結果の全行数を取得します.
	 * 検索結果が0件だった場合は int = 0 を返します。
	 *
	 * @param searchWord あいまい検索したい単語
	 * @return あいまい検索結果の全行数
	 */
	public int countFSAllRow(String searchWord){
		Integer result = employeeRepository.countFSRows(searchWord);
		if (result == null){
			return 0;
		}
		return result;
	}
}
