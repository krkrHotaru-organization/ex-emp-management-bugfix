package com.example.controller;

import java.util.ArrayList;
import java.util.List;

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

import static java.lang.Math.ceil;

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
	public String showList(String searchWord, Model model, Integer page) {

		List<Employee> employeeList = employeeService.findEmpsAccordingToPage(searchWord, 10,page);
		int allRowsCount = employeeService.countFSAllRow(searchWord);
		if (employeeList.isEmpty()){
			employeeList = employeeService.findEmpsAccordingToPage(null,10,page);
			allRowsCount = employeeService.countAllRow();
			if (searchWord != null){
				model.addAttribute("notFound","１件もありませんでした");
			}
		}

		int maxPage = (int)(ceil((double)allRowsCount/10));
		if (page != null && page > maxPage){
			model.addAttribute("notFound","存在しないページへの遷移が行われたため、初期画面を表示しました");
			return showList(null,model,null);
		}
		List<Integer> pages = new ArrayList<>();
		for (int i = 0; i < maxPage; i++) {
			pages.add(i+1);
		}
		model.addAttribute("pages",pages);
		model.addAttribute("searchWord",searchWord);
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

}
