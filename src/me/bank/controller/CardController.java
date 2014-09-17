package me.bank.controller;

import java.util.ArrayList;
import java.util.List;

import me.bank.interceptor.CardInterceptor;
import me.bank.kit.CardKit;
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
	 * 新增卡号
	 */
	public void save() {
		Card card = getModel(Card.class);
		String identity = card.get("identity");
		String cardNumber = "";
		if (card != null) {
			int cardType = card.get("type");
			// 生成卡号
			cardNumber = randomCard(cardType);
			card.set("uuid", cardNumber);
		}
		card.save();
		redirect("search?identity=" + identity);
	}

	/**
	 * 判断是否有当前卡号如果有继续生成
	 * 
	 * @return
	 */
	public boolean isExistCardNumber(String uuid) {
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
		String card = null;
		while (true) {
			card = CardKit.generateCardNumber(type);
			
			// 判断有没有本卡号，如果有继续自动生成
			if (!isExistCardNumber(card)) {
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
