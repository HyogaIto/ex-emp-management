package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員情報を取得する.
 * 
 * @author hyoga.ito
 *
 */
@Service
@Transactional
public class EmployeeService {
	/**	オブジェクトの参照をリポジトリに注入 */
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 全件取得を行う.
	 * 
	 * @return　検索結果のList
	 */
	public List<Employee> showList(){
		List<Employee> employeeList=employeeRepository.findAll();
		return employeeList;
	}
	
	/**
	 * 指定の従業員情報を取得する.
	 * 
	 * @param id　従業員ID
	 * @return　取得した従業員情報
	 */
	public Employee showDetail(Integer id) {
		Employee employee= employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新する.
	 * 
	 * @param employee 変更したい従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

}
