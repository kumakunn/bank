package me.bank.config;

import me.bank.interceptor.SessionInterceptor;
import me.bank.model.Access;
import me.bank.model.Admin;
import me.bank.model.Card;
import me.bank.model.Detail;
import me.bank.model.Dictionary;
import me.bank.model.Permission;
import me.bank.model.Role;
import me.bank.model.RolePermission;
import me.bank.model.Teller;
import me.bank.model.User;
import me.bank.routes.AdminRoutes;
import me.bank.routes.HomeRoutes;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.FakeStaticHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class CoreConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载必要配置

		loadPropertyFile("db_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));

		me.setBaseViewPath("WEB-INF/templates");
		
		me.setError404View("/error/404.html");
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add(new AdminRoutes());
		me.add(new HomeRoutes());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {

		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
				getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);

		me.add(arp);

		arp.setContainerFactory(new CaseInsensitiveContainerFactory());

		// 映射模型
		arp.addMapping("admin", Admin.class);
		arp.addMapping("permission", Permission.class);
		arp.addMapping("role", Role.class);
		arp.addMapping("role_permission", "roleId", RolePermission.class);
		arp.addMapping("dictionary", Dictionary.class);
		arp.addMapping("access", Access.class);
		arp.addMapping("user", User.class);
		arp.addMapping("card", Card.class);
		arp.addMapping("detail", Detail.class);
		arp.addMapping("teller", Teller.class);

	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {

		me.add(new SessionInterceptor());

	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new FakeStaticHandler());
		me.add(new ContextPathHandler());
	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 8080, "/", 5);
	}
}
