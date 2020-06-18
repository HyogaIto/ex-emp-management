package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を検索するコントローラ
 * 
 * @author hyoga.ito
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	/** オブジェクトの参照を注入 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 扶養人数更新時のフォームをリクエストスコープに格納する.
	 * 
	 * @return フォームオブジェクト
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpEmployeeForm() {
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		return updateEmployeeForm;
	}

	/**
	 * 従業員一覧を出力する.
	 * 
	 * @param model リクエストスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";

	}

	/**
	 * idから従業員情報を検索する.
	 * 
	 * @param id 従業員ID
	 * @param model　リクエストスコープ
	 * @return　従業員の詳細ページ
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		int idInt = Integer.parseInt(id);
		Employee employee= employeeService.showDetail(idInt);
		model.addAttribute("employee", employee);
		return "employee/detail";
		
	}
	
	/**
	 * 扶養人数を更新するメソッド.
	 * 
	 * @param form 入力されたフォーム
	 * @return　リダイレクト：従業員一覧画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee=employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
		
	}

}
