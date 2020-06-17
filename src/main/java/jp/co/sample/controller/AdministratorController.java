package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラ.
 * 
 * @author hyoga.ito
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	/** serviceへの参照が入る変数 */
	@Autowired
	private AdministratorService administratorService;
	
	/**	sessionスコープ用の変数 */
	@Autowired
	private HttpSession session;

	/**
	 * InsertAdministratorFormオブジェクトをリクエストスコープ に格納する.
	 * 
	 * @return insertAdministaratorフォームオブジェクト
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm = new InsertAdministratorForm();
		return insertAdministratorForm;
	}

	/**
	 * LoginFormオブジェクトパラメータをリクエストスコープ に格納する.
	 * 
	 * @return loginフォームオブジェクト
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}

	/**
	 * 管理者登録画面へフォワードする.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param insertAdministratorForm 管理者情報
	 * @return リダイレクト：ログイン画面
	 */

	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm insertAdministratorForm) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(insertAdministratorForm, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}

	/**
	 * ログイン画面へフォワードする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * ログイン判定.
	 * 
	 * @param loginForm フォームに入力された内容
	 * @param model　リクエストスコープ
	 * @return　従業員情報一覧ページ
	 */
	@RequestMapping("/login")
	public String login(LoginForm loginForm,Model model) {
		Administrator administrator=administratorService.login(loginForm.getMailAddress(), loginForm.getPassword());
		if(administrator==null) {
			model.addAttribute("error", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		session.setAttribute("administorName", administrator.getName());
		return "forward:/employee/showList";
	}

}
