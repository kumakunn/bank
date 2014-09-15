package me.bank.routes;

import me.bank.controller.AccessController;
import me.bank.controller.CardController;
import me.bank.controller.DetailController;
import me.bank.controller.IndexController;
import me.bank.controller.MyInfoController;
import me.bank.controller.TellerController;
import me.bank.controller.UserController;

import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {

	@Override
	public void config() {

		// 后台首页
		add("/admin", IndexController.class);

		// 个人信息
		add("/admin/myinfo", MyInfoController.class);

		// 存取款管理
		add("/admin/access", AccessController.class);

		// 银行卡管理
		add("/admin/card", CardController.class);

		// 收支明细管理
		add("/admin/detail", DetailController.class);

		// 用户信息管理
		add("/admin/user", UserController.class);

		// 柜员信息管理
		add("admin/teller", TellerController.class);

	}

}
