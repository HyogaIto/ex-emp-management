package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;


/**
 * @author hyoga.ito
 *administatorsテーブルを操作するリポジトリ.
 */
@Repository
public class AdministratorRepository {
	
	/**
	 * Administratorオブジェクトを生成するRowMapper.
	 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER=(rs,i)->{
		Administrator administrator =new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mailAddress"));
		administrator.setPassword(rs.getString("password"));
		return administrator;	
	};
	
	/** sqlを実行するための変数.　 */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** テーブル名（administators）を格納する変数.　 */
	private final String TABLE_NAME="administrators";
	
	/**
	 * @param administrator
	 * 管理者情報を挿入するメソッド.
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param=new BeanPropertySqlParameterSource(administrator);
		
		String sql="insert into "+TABLE_NAME+"(name,mail_address,password) "
				+ "values(:name,:mailAddress,:password);";
		template.update(sql, param);
	}
	
	/**
	 *メールアドレスとパスワードから管理者情報を取得するメソッド.
	 * @param mailAddress　管理者メールアドレス
	 * @param password　管理者パスワード
	 * @return　管理者が存在する場合はadministratorオブジェクトを、しない場合はnullを返す
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress,String password) {
		String sql ="select (id,name,mail_address,password) from "+TABLE_NAME+" "
				+ "where mail_address=:mailAddress and password=:password";
		SqlParameterSource param=new MapSqlParameterSource().addValue("mailAddress",mailAddress).addValue("password", password);
		
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		
		if(administratorList.size()==0) {
			return null;
		}
		return administratorList.get(0);
	}

}
