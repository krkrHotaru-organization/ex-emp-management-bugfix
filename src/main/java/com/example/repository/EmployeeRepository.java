package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 *
 * @author igamasayuki
 */
@Repository
public class EmployeeRepository {

    /**
     * Employeeオブジェクトを生成するローマッパー.
     */
    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setImage(rs.getString("image"));
        employee.setGender(rs.getString("gender"));
        employee.setHireDate(rs.getDate("hire_date"));
        employee.setMailAddress(rs.getString("mail_address"));
        employee.setZipCode(rs.getString("zip_code"));
        employee.setAddress(rs.getString("address"));
        employee.setTelephone(rs.getString("telephone"));
        employee.setSalary(rs.getInt("salary"));
        employee.setCharacteristics(rs.getString("characteristics"));
        employee.setDependentsCount(rs.getInt("dependents_count"));
        return employee;
    };

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 主キーから従業員情報を取得します.
     *
     * @param id 検索したい従業員ID
     * @return 検索された従業員情報
     * @throws org.springframework.dao.DataAccessException 従業員が存在しない場合は例外を発生します
     */
    public Employee load(Integer id) {
        String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

        return development;
    }

    /**
     * 従業員情報を変更します.
     */
    public void update(Employee employee) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

        String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
        template.update(updateSql, param);
    }

    /**
     * 全従業員情報を入社日順で取得します.
     *
     * @return 全従業員情報
     */
    public List<Employee> findAll() {
        return findByNameFuzzy(null, null, null);
    }

    /**
     * 従業員一覧情報を入社日順で取得します.
     *
     * @param searchWord 引数に入力があった場合は、曖昧検索が行われます。
     * @param limit      持ってきたい従業員情報の行数
     * @param offset     最初に持ってきたい従業員情報の開始位置
     * @return 引数にあった開始位置・行数の従業員情報のリスト
     */
    public List<Employee> findByNameFuzzy(String searchWord, Integer limit, Integer offset) {
        String sql = """
                SELECT
                	id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count
                FROM employees
                WHERE name LIKE :searchWord
                ORDER BY hire_date ASC
                """;
        if (limit == null){
            sql += "LIMIT ALL OFFSET :offset;";
        }else{
            sql += "LIMIT :limit OFFSET :offset;";
        }
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("searchWord", "%" + searchWord + "%")
                .addValue("limit", limit)
                .addValue("offset", offset);
        return template.query(sql, param, EMPLOYEE_ROW_MAPPER);
    }

    /**
     * 全従業員情報の行数を取得します.
     *
     * @return 全従業員数
     */
    public Integer countAllRow() {
        return countFSRows(null);
    }

    /**
     * 引数と指定した単語で曖昧検索を行った場合の検索結果全行の行数を取得します.
     *
     * @param searchWord 従業員氏名のカラムであいまい検索を行う単語
     * @return 検索結果の行数
     */
    public Integer countFSRows(String searchWord) {
        String sql = """
                SELECT count(*) FROM employees
                WHERE name LIKE :searchWord;
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("searchWord", "%" + searchWord + "%");
        return template.queryForObject(sql, param, Integer.class);
    }
}
