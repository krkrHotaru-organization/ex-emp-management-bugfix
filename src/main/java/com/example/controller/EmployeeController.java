package com.example.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.InsertEmployeeForm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 入力された内容で従業員名をあいまい検索し、該当した従業員情報一覧を表示します.
	 * 検索ボックスに入力がない場合は、全従業員の一覧を表示します。
	 *
	 * @param searchWord 入力された名前
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@GetMapping("/showList")
	public String showList(String searchWord,Model model) {
		List<Employee> employeeList = new ArrayList<>();
		if (searchWord != null) {
			employeeList = employeeService.fuzzySearchByName(searchWord);
		} else {
			employeeList = employeeService.showList();
		}

		if (employeeList.size() == 0) {
			model.addAttribute("notFound", "１件もありませんでした。");
			employeeList = employeeService.showList();

		}
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@GetMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		if (employee.getImage().length() >= 100){
			model.addAttribute("isBase64",true);
		}
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@PostMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	@GetMapping("/toInsert")
	public String toInsert(InsertEmployeeForm form){
		return "employee/insert";
	}

	@PostMapping("/insert")
	public String register(@Validated InsertEmployeeForm form,
						   BindingResult result,
						   Model model){
		if (employeeService.isExistMailAddress(form.getMailAddress())){
			result.rejectValue("mailAddress","","このメールアドレスは既に登録されています");
		}
		if (result.hasErrors()){
			return toInsert(form);
		}
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);

		Date hireDate = java.sql.Date.valueOf(form.getHireDate());
		employee.setHireDate(hireDate);

		MultipartFile multipartFile = form.getImage();
		byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
			e.printStackTrace();
			return null;
        }
        employee.setImage(Base64.encodeBase64String(bytes));
		employeeService.insert(employee);
		return "redirect:/employee/showList";
	}

}
