package me.bank.controller;

import java.util.ArrayList;
import java.util.List;

import me.bank.interceptor.CardInterceptor;
import me.bank.kit.ParaKit;
import me.bank.model.Card;
import me.bank.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * CardController
 * 
 * 银行卡管理
 * 
 */
@Before(CardInterceptor.class)
public class CardController extends Controller {

	public void index() {

		render("index.html");
	}

	public void search() {

		String identity = getPara("identity");

		setAttr("searchIdentity", identity);

		User user = User.dao.getUserByIdentity(identity);

		if (user != null) {

			setAttr("user", user);

			System.out.println("user = " + user);

			List<Card> cardList = new ArrayList<Card>();

			cardList.addAll(user.getCards());

			setAttr("cardList", cardList);

		} else {
			setAttr("searchIdentity", "");
		}

		render("index.html");
	}

	/**
	 * 跳转新增页面
	 * 
	 */
	public void add() {
		int userId = getParaToInt("userId");
		User user = User.dao.findById(userId);
		setAttr("user", user);
		if (user != null) {
			render("add.html");
		} else {
			render("index.html");
		}

	}

	/**
	 * 新增卡号
	 */
	public void save() {
		Card card = getModel(Card.class);
		String identity = card.get("identity");
		String cardCode = "";
		if (card != null) {
			int cardType = card.get("type");
			// 生成卡号
			cardCode = randomCard(cardType);
			card.set("uuid", cardCode);
		}
		card.save();
		redirect("search?identity=" + identity);
	}

	/**
	 * 判断是否有当前卡号如果有继续生成
	 * 
	 * @return
	 */
	public boolean isHaveCard(String uuid) {
		Card card = Card.dao.getCardByUuid(uuid);
		if (card != null) {
			return true;
		}
		return false;
	}

	/**
	 * 随机生成卡号
	 */
	public String randomCard(int type) {
		String card = "";
		/**
		 * 卡号生成规则是开头编码+城市编码+剩余随机数 622836 中国农业银行人民币贷记卡 香港旅游卡贷记卡金卡 622837
		 * 中国农业银行人民币贷记卡 香港旅游卡贷记卡普卡 62284 中国农业银行世纪通宝借记卡 95599 中国农业银行世纪通宝借记卡
		 * 借记卡是19位，贷记卡和准贷记卡是16位 佳木斯市代码为 8019
		 ***/
		// 借记卡头
		String[] header1 = new String[] { "62284", "95599" };
		// 货记卡头
		String[] header2 = new String[] { "622836", "622837" };
		// 城市编码
		String cityNum = "8019";
		int headerFlag = Math.random() > 0.5 ? 0 : 1;
		while (true) {
			card = "";
			if (type == 11) {
				// 借记卡
				card += header1[headerFlag] + cityNum;
				// 补填剩余10位数
				for (int i = 0; i < 10; i++) {
					String s = String.valueOf((int) (Math.random() * 10));
					card += s;
				}
				System.out.println(card.length() + "借记卡:" + card);
			} else if (type == 12) {
				// 货记卡
				card += header2[headerFlag] + cityNum;
				// 补天剩余6位数
				for (int i = 0; i < 6; i++) {
					String s = String.valueOf((int) (Math.random() * 10));
					card += s;
				}
				System.out.println(card.length() + "货记卡:" + card);
			}
			// 判断有没有本卡号，如果有继续自动生成
			if (!isHaveCard(card)) {
				break;
			}
		}
		return card;
	}

	/**
	 * 删除信息
	 */
	public void delete() {
		int cardId = ParaKit.paramToInt(getPara(0), -1);

		if (cardId > -1) {
			if (Card.dao.deleteById(cardId)) {
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}
}
