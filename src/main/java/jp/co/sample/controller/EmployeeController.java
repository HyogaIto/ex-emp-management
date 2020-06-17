package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
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
	
	/**	オブジェクトの参照を注入 */
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * @param model リクエストスコープ
	 * @return　従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList=employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
		
	}
}
