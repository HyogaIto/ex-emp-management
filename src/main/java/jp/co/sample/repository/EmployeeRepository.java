package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 *　employeesテーブルを操作するリポジトリ.
 *
 * @author hyoga.ito
 */
@Repository
public class EmployeeRepository {
	/**
	 * Employeeオブジェクトを生成するRowMapper.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER=(rs,i)->{
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
	

	/** sqlを実行するための変数.　 */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** テーブル名（employees）　を格納する変数. */
	private final String TABLE_NAME="employees";
	
	/**
	 * 従業員一覧を入社日の降順で取得する.
	 * 
	 * @return 従業員一覧が格納されたList　従業員が存在しない場合はサイズ0件で返る
	 */
	public List<Employee> findAll(){
		String sql ="select id,name,image,gender,hire_date,mail_address,zip_code,"
				+ "address,telephone,salary,characteristics,dependents_count"
				+ " from "+TABLE_NAME+" order by hire_date desc;";
		List<Employee> employeeList=template.query(sql, EMPLOYEE_ROW_MAPPER);
		

		return employeeList;
		
	}
	
	/**
	 * 主キーから従業員情報を取得する.
	 * 
	 * @param id 主キー
	 * @return 取得した従業員情報　存在しない場合は例外が発生する。
	 */
	public Employee load(Integer id) {
		String sql ="select  id,name,image,gender,hire_date,mail_address,zip_code," 
				+"address,telephone,salary,characteristics,dependents_count"
				+" from "+TABLE_NAME+" where id=:id;";
		SqlParameterSource param= new MapSqlParameterSource().addValue("id", id);
		Employee employee=template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
		
	}
	
	/**
	 * IDの一致する従業員情報を変更する.
	 * 
	 * @param employee　変更内容の入った従業員情報
	 */
	public void update(Employee employee) {
		SqlParameterSource param=new BeanPropertySqlParameterSource(employee);
		String sql ="update "+TABLE_NAME+" set name=:name,image=:image,"
				+ "gender=:gender,hire_date=:hireDate,mail_address,"
				+ "zip_code=:zipCode,address=:address,telephone=:telephone,"
				+ "salary=:salary,characteristics=:characteristics,"
				+ "dependents_count=:dependentsCount "
				+ "where id=:id;";
		template.update(sql, param);
	}
	

}
