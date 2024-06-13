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
     * ページングの条件に沿って従業員情報を返します.
     *
     * @param searchWord 従業員氏名のカラムであいまい検索にかけたい文字列
     * @param rowNum     １ページ当たりに表示したい結果行数
     * @param page       ページ
     * @return 検索結果の従業員情報一覧
     */
    public List<Employee> findEmpsAccordingToPage(String searchWord, Integer rowNum, Integer page) {
        int offset = page == null ? 0 : (page - 1) * 10;
        return employeeRepository.findEmpsSearchByWordClipByLimitAndOffset(searchWord, rowNum, offset);
    }

    /**
     * 全従業員情報の行数を取得します.
     * 検索結果が0件だった場合は 0 を返します。
     *
     * @return 全従業員数
     */
    public int countAllRow() {
        Integer result = employeeRepository.countAllRow();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * あいまい検索結果の行数を取得します.
     * 検索結果が0件だった場合は 0 を返します。
     *
     * @param searchWord 文字列が入るとあいまい検索、nullが入ると全件検索で取得されます
     * @return 実行された検索結果の行数が返されます
     */
    public int countFSAllRow(String searchWord) {
        Integer result = employeeRepository.countFSRows(searchWord);
        if (result == null) {
            return 0;
        }
        return result;
    }
}
