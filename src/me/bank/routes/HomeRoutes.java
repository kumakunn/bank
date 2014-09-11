package me.bank.routes;

import me.bank.controller.IndexController;

import com.jfinal.config.Routes;

public class HomeRoutes extends Routes {

	@Override
	public void config() {

		// 首页
		add("/", IndexController.class);

	}

}
