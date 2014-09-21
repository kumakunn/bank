package me.bank.controller;

import java.math.BigDecimal;

import me.bank.kit.DateKit;
import me.bank.kit.ParaKit;
import me.bank.kit.UUID;
import me.bank.model.Admin;
import me.bank.model.Card;
import me.bank.model.Detail;
import me.bank.model.User;
import me.bank.validator.SaveMoneyValidator;
import me.bank.validator.TakeOutValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * AccessController
 * 
 * 存取款明细管理
 * 
 */
public class AccessController extends Controller {

	private static final int SUCCESS = 0;
	private static final int ERROR_PASSWORD_ONCE = 1;
	private static final int ERROR_PASSWORD_TWICE = 2;
	private static final int CARD_LOCKED = 3;
	private static final int BALANCE_NOT_ENOUGH = 4;

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

		// 获取借记卡的信息
		Card card = Card.dao.getCardByUuid(uuid);

		if (card != null) {

			User user = User.dao.getUserByIdentity(card.get("identity").toString());

			setAttr("user", user);
			setAttr("card", card);

		}

		// 取钱
		if (s.equals("get")) {

			String token = UUID.randomUUID();
			getSession().setAttribute(token, uuid);
			setAttr("token", token);

			render("get.html");
		} else {
			// 存款
			render("save.html");
		}
	}

	/**
	 * 密码验证通过后，进入取钱的环节
	 */
	@Before(Tx.class)
	public void inputPwd() {

		String token = getPara("token");
		String password = getPara("password");

		String uuid = getSessionAttr(token);

		System.out.println("inputPwd -> token = " + token);
		System.out.println("inputPwd -> uuid = " + uuid);

		if (ParaKit.isEmpty(uuid)) {
			setAttr("status", "");
			renderJson();
			return;
		}

		Card card = Card.dao.getCardByUuid(uuid);

		if (card == null || card.getInt("status") == 2) {

			setSessionAttr(token + "_status", CARD_LOCKED);

			setAttr("status", "");
			renderJson();
			return;

		}

		if (!ParaKit.isEmpty(password) && password.equals(card.get("password"))) {

			String moneyStr = getSessionAttr(token + "_money");
			double money = Double.parseDouble(moneyStr);

			// 密码输入正确
			BigDecimal balance = card.get("balance");

			System.out.println("before balance: " + balance.doubleValue());

			// 存款后卡上余额
			balance = balance.subtract(BigDecimal.valueOf(money));

			if (balance.doubleValue() < 0) {
				setSessionAttr(token + "_status", BALANCE_NOT_ENOUGH);
				renderJson();
				return;
			}

			System.out.println("after balance: " + balance.doubleValue());

			card.set("balance", balance.doubleValue());
			card.update();

			// 交易明细记录
			Detail detail = new Detail();
			detail.set("identity", card.get("identity"));
			detail.set("uuid", uuid);
			detail.set("time", DateKit.getDateTime());

			// 0: 支出, 1: 存入
			detail.set("type", 1);
			detail.set("amount", BigDecimal.valueOf(money));
			detail.set("balance", balance.doubleValue());

			Admin admin = getSessionAttr("admin");

			if (admin.getInt("rid") == 3) {
				int tid = admin.get("tid");
				detail.set("tid", tid);
			}

			if (detail.save()) {
				setSessionAttr(token + "_status", SUCCESS);
			}

		} else {

			Integer errorCount = getSessionAttr(token + "_status");

			System.out.println("errorCount = " + errorCount);

			int count = 0;
			if (errorCount == null) {
				setSessionAttr(token + "_status", ERROR_PASSWORD_ONCE);
			} else {
				count = errorCount;
				count++;
			}

			System.out.println("getSessionAttr = " + getSessionAttr(token + "_status"));

			if (count == 2) {
				setSessionAttr(token + "_status", ERROR_PASSWORD_TWICE);
			} else if (count == 3) {
				setSessionAttr(token + "_status", CARD_LOCKED);

				// 锁卡
				card.set("status", 2);
				card.update();

			}

		}

		setAttr("status", "");
		renderJson();

	}

	/**
	 * 检测用户是否已经输入了密码
	 */
	public void waitPwd() {

		String token = getPara("token");
		String uuid = getSessionAttr(token);

		System.out.println("waitPwd -> token = " + token);
		System.out.println("waitPwd -> status = " + getSessionAttr(token + "_status"));

		if (!ParaKit.isEmpty(uuid)) {
			setAttr("status", getSessionAttr(token + "_status"));
		}

		renderJson();

	}

	/**
	 * 显示输入密码的页面
	 */
	public void showPwdPage() {

		String token = getPara("token");

		setAttr("token", token);

		render("pwd.html");

	}

	/**
	 * 取钱
	 */
	@Before(TakeOutValidator.class)
	public void takeOut() {

		String token = getPara("token");
		String uuid = getPara("uuid");
		String money = getPara("money");

		if (!ParaKit.isEmpty(uuid) && uuid.equals(getSessionAttr(token))) {
			getSession().setAttribute(uuid + "_money", money);
			setAttr("status", "success");
			renderJson();
			return;
		}

		renderNull();

	}

	/**
	 * 存款
	 */
	@Before({ SaveMoneyValidator.class, Tx.class })
	public void saveMoney() {

		String uuid = getPara("uuid");

		setAttr("searchUuid", uuid);

		double money = Double.valueOf(getPara("money").toString());

		System.out.println("money: " + money);

		int uid = Integer.valueOf(getPara("uid"));

		Card card = Card.dao.getCardByUuid(uuid);

		User user = User.dao.findById(uid);

		BigDecimal balance = card.get("balance");

		System.out.println("before balance: " + balance.doubleValue());

		// 存款后卡上余额
		balance = balance.add(BigDecimal.valueOf(money));
		System.out.println("after balance: " + balance.doubleValue());

		card.set("balance", balance.doubleValue());
		card.update();

		// 交易明细记录
		Detail detail = new Detail();
		detail.set("identity", user.get("identity"));
		detail.set("uuid", uuid);
		detail.set("time", DateKit.getDateTime());

		// 0: 支出, 1: 存入
		detail.set("type", 1);
		detail.set("amount", BigDecimal.valueOf(money));
		detail.set("balance", balance.doubleValue());

		Admin admin = getSessionAttr("admin");

		if (admin.getInt("rid") == 3) {
			int tid = admin.get("tid");
			detail.set("tid", tid);
		}

		boolean result = detail.save();

		setAttr("user", user);
		setAttr("card", card);

		setAttr("detail", detail);
		setAttr("result", result);

		render("save.html");

	}
}
