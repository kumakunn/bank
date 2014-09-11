package me.bank.controller;

import java.util.List;

import me.bank.model.Admin;
import me.bank.model.Permission;
import me.bank.model.Role;
import me.bank.validator.LoginValidator;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

/**
 * IndexController
 */
public class IndexController extends Controller {

	public void index() {

		String userName = "";
		Admin admin = getSessionAttr("admin");
		if (admin != null) {
			userName = admin.get("account");
		}

		setAttr("userName", userName);
		setAttr("roleName", getSessionAttr("roleName"));
		setAttr("permissions", getSessionAttr("permissions"));

		render("index.html");
	}

	@ClearInterceptor(ClearLayer.ALL)
	public void login() {
		render("login.html");
	}

	@ClearInterceptor(ClearLayer.ALL)
	@Before(LoginValidator.class)
	public void handle() {

		// 处理登录
		String account = getPara("account");
		String password = getPara("password");

		getSession().removeAttribute("admin");
		getSession().removeAttribute("roleName");
		getSession().removeAttribute("permissions");

		Admin admin = Admin.dao.getByAccountAndPassword(account, password);

		Role role = null;
		List<Permission> permissions = null;

		if (admin != null) {

			role = admin.getRole();
			permissions = admin.getPermissions();

			// 登录时间
			long time = System.currentTimeMillis();
			admin.set("time", String.valueOf(time));
			admin.update();

			setSessionAttr("admin", admin);

			if (permissions != null && permissions.size() > 0) {

				for (Permission p : permissions) {
					p.initSubPermissions();
				}

			}

			setSessionAttr("roleName", role.getStr("name"));
			setSessionAttr("permissions", permissions);

			redirect("index.html");

		} else {

			setAttr("errorMsg", "用户名或密码错误!");

			keepPara("account");

			render("login.html");
		}

	}

	/**
	 * 注销登录
	 */
	public void logout() {
		getSession().removeAttribute("admin");
		getSession().removeAttribute("roleName");
		getSession().removeAttribute("permissions");
		redirect("login.html");
	}

}
