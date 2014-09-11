package me.bank.controller;

import me.bank.kit.DateKit;
import me.bank.model.Admin;
import me.bank.validator.UpdatePasswordValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * MyInfoController
 * 
 */
public class MyInfoController extends Controller {

	public void index() {

		setAttr("title", "我的信息");

		Admin admin = getSessionAttr("admin");

		setAttr("admin", admin);

		String loginTime = admin.getStr("time");

		setAttr("time", DateKit.format("yyyy年MM月dd日 HH:mm", loginTime));

		render("index.html");
	}

	@Before(UpdatePasswordValidator.class)
	public void update() {

		// 密码的一致性以及常规验证在UpdatePasswordValidator中进行
		String oldPassword = getPara("oldPassword");
		String newPassword = getPara("newPassword");

		Admin admin = getSessionAttr("admin");

		if (!admin.get("password").equals(oldPassword)) {

			setAttr("status", "error");

			setAttr("msg", "原密码不正确!");

			keepPara();

			renderJson();

		} else {

			admin.set("password", newPassword);

			if (admin.update()) {

				setAttr("status", "success");
				setAttr("msg", "密码修改成功!");
				renderJson();
			}
		}

	}
}
