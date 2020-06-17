package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;


/**
 * 管理者登録画面を表示する処理を行う.
 * 
 * @author hyoga.ito
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	/** serviceへの参照が入る変数  */
	@Autowired
	private AdministratorService administratorService;
	
	/**
	 * リクエストパラメータをリクエストスコープ に格納する.
	 * 
	 * @return フォームオブジェクト
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm=new InsertAdministratorForm();
		return insertAdministratorForm;
	}
	
	/**
	 * 管理者登録画面へフォワードする.
	 * 
	 * @return　管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

}
