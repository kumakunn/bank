package me.bank.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import me.bank.kit.DateKit;
import me.bank.kit.ParaKit;
import me.bank.model.Access;
import me.bank.model.Admin;
import me.bank.model.Card;
import me.bank.model.Detail;
import me.bank.model.User;
import me.bank.validator.GetMoneyValidator;
import me.bank.validator.SaveMoneyValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * AccessController
 * 
 * 存取款明细管理
 * 
 */
public class AccessController extends Controller {

	// 跳转取款
	public void get() {
		render("get.html");
	}

	// 跳转存款
	public void save() {
		render("save.html");
	}

	public void search() {
		String s = getPara("s");

		// 卡号
		String uuid = getPara("uuid");
		setAttr("searchUuid", uuid);

		// 关联取款结果result
		String result = getPara("result");

		if (!ParaKit.isEmpty(result)) {

			// String money = getPara("money");
			// String checkMoney = getPara("checkMoney");

			/**
			 * 1取款失败 2取款成功 3存款失败 4存款成功
			 */
			if (result.equals("1")) {
				result = "取款失败！";
			} else if (result.equals("2")) {
				result = "取款成功！";
			} else if (result.equals("3")) {
				result = "存款失败！";
			} else {
				result = "存款成功！";
			}
			setAttr("result", result);
		}

		// 获取借记卡的信息
		Card card = Card.dao.getCardByUuidJJ(11, uuid);

		// 取款
		if (card != null) {
			User user = User.dao.getUserByIdentity(card.get("identity").toString());

			// 获取卡号对应的access对象如果没有创建一个
			Access access = Access.dao.getAccessByUuid(uuid);
			if (access == null) {
				access = new Access();
				access.set("uuid", uuid);
				access.set("amount", 0.000);
				access.set("time", DateKit.getDateTime());
				HttpSession session = getSession();
				Admin admin = (Admin) session.getAttribute("admin");
				int tid = admin.get("tid");
				access.set("tid", tid);
				access.save();
			}

			setAttr("user", user);
			setAttr("card", card);
			setAttr("access", access);

		} else {
			// setAttr("searchUuid", "");
		}

		if (s.equals("get")) {
			render("get.html");
		} else {
			// 存款
			render("save.html");
		}
	}

	/**
	 * 取款
	 */
	@Before(GetMoneyValidator.class)
	public void getMoney() {
		// 取款结果
		String result = "";
		String uuid = getPara("uuid");
		double money = Double.valueOf(getPara("money").toString());
		int userid = Integer.valueOf(getPara("userid"));
		Access access = Access.dao.getAccessByUuid(uuid);
		Detail detail = new Detail();
		User user = User.dao.findById(userid);

		BigDecimal amount = access.get("amount");
		// 支出后卡上余额
		double checkMoney = amount.subtract(BigDecimal.valueOf(money)).doubleValue();
		if (checkMoney < 0) {
			setAttr("toBig", "超出剩余金额，请改写您的金额！");
			// renderJson("msg","取款失败！超出余额");

			result = "1";
		} else {
			access.set("amount", BigDecimal.valueOf(checkMoney));
			access.set("time", DateKit.getDateTime());
			HttpSession session = getSession();
			Admin admin = (Admin) session.getAttribute("admin");
			int tid = admin.get("tid");
			access.set("tid", tid);
			access.update();
			Card card = Card.dao.getCardByUuid(uuid);
			// 交易明细记录
			detail.set("identity", user.get("identity"));
			detail.set("uuid", card.get("uuid"));
			detail.set("time", DateKit.getDateTime());
			detail.set("type", 0);// 代表支出
			detail.set("amount", BigDecimal.valueOf(money));
			detail.set("balance", BigDecimal.valueOf(checkMoney));
			detail.save();
			// render("get.html");
			// 取款成功提示
			result = "2";
		}

		redirect("search?uuid=" + uuid + "&s=get" + "&result=" + result + "&money=" + money
				+ "&checkMoney=" + checkMoney);
	}

	/**
	 * 存款
	 */
	@Before(SaveMoneyValidator.class)
	public void saveMoney() {
		// 存款结果
		String result = "";
		String uuid = getPara("uuid");
		double money = Double.valueOf(getPara("money").toString());
		int userid = Integer.valueOf(getPara("userid"));
		Access access = Access.dao.getAccessByUuid(uuid);
		Detail detail = new Detail();
		User user = User.dao.findById(userid);

		BigDecimal amount = access.get("amount");
		// 存款后卡上余额
		double checkMoney = amount.add(BigDecimal.valueOf(money)).doubleValue();
		try {
			access.set("amount", BigDecimal.valueOf(checkMoney));
			access.set("time", DateKit.getDateTime());
			HttpSession session = getSession();
			Admin admin = (Admin) session.getAttribute("admin");
			int tid = admin.get("tid");
			access.set("tid", tid);
			access.update();

			// Card card = Card.dao.getCardByUuid(uuid);

			// 交易明细记录
			detail.set("identity", user.get("identity"));
			detail.set("uuid", uuid);
			detail.set("time", DateKit.getDateTime());
			detail.set("type", 1);// 代表支出
			detail.set("amount", BigDecimal.valueOf(money));
			detail.set("balance", BigDecimal.valueOf(checkMoney));
			detail.save();
			result = "4";
		} catch (Exception e) {
			result = "3";
		}

		redirect("search?uuid=" + uuid + "&s=save" + "&result=" + result + "&money=" + money
				+ "&checkMoney=" + checkMoney);
		// renderJson("msg","存款成功！您存入了"+money+",当前余额为"+checkMoney);
	}
}
