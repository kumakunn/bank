package me.bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bank.config.Constants;
import me.bank.interceptor.CardInterceptor;
import me.bank.kit.ParaKit;
import me.bank.model.Card;
import me.bank.model.CreditInfo;
import me.bank.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * CardController
 * 
 * 银行卡管理
 * 
 */
@Before(CardInterceptor.class)
public class VerifyController extends Controller {

	public void index() {
		// 判断当前是否是搜索的数据进行的分页
		// 如果是搜索的数据，则跳转至search方法处理
		if (!ParaKit.isEmpty(getPara("s"))) {

			search();

			return;
		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}
		// 读取所有的柜员
		Page<Card> cardList = Card.dao.paginateForStatus("<>", page, Constants.PAGE_SIZE, 11);
		setAttr("cardList", cardList);

		setAttr("searchUuid", "");
		setAttr("searchIdentity", "");
		setAttr("searchPage", Constants.NOT_SEARCH_PAGE);

		render("index.html");
	}

	/**
	 * 搜索
	 */
	public void search() {
		if (ParaKit.isEmpty(getPara("s"))) {

			// 说明当前请求是搜索数据的post请求，并非搜索的分页请求
			// 在这里执行搜索操作，并将结果保存到缓存中

			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("uuid", getPara("uuid"));
			queryParams.put("identity", getPara("identity"));
			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from card where status =0 and type <> 11");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			String uuid = queryParams.get("uuid");

			if (!ParaKit.isEmpty(uuid)) {
				sb.append(" and uuid = ?");
				params.add(uuid);
			}

			String identity = queryParams.get("identity");

			if (!ParaKit.isEmpty(identity)) {
				sb.append(" and identity = ?");
				params.add(identity);
			}

			setAttr("searchUuid", uuid);
			setAttr("searchIdentity", identity);
			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		// 信用卡列表
		Page<Card> cardList = Card.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("cardList", cardList);

		render("index.html");
	}

	public void goCheckCard() {
		String cardId = getPara("cardId");
		Card card = Card.dao.findById(cardId);
		String identity = card.get("identity");
		User user = User.dao.getUserByIdentity(identity);
		CreditInfo ad = CreditInfo.dao.getCreditInfoByUserId(Integer.valueOf(user.get("id")
				.toString()));

		setAttr("user", user);
		setAttr("applicationdata", ad);// 信用填写信息
		setAttr("card", card);
		render("check.html");
	}

	public void checkCard() {
		int id = getParaToInt("cardId");
		Card card = Card.dao.findById(id);
		int result = getParaToInt("result");
		System.out.println(result);
		if (card != null) {
			card.set("status", result);
			card.update();
			renderJson("msg", "审核成功！");
		} else {
			renderJson("msg", "审核失败！");
		}

	}
}
