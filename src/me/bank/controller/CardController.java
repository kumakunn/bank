package me.bank.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.bank.interceptor.CardInterceptor;
import me.bank.kit.CardKit;
import me.bank.kit.DateKit;
import me.bank.kit.ParaKit;
import me.bank.model.Card;
import me.bank.model.CreditInfo;
import me.bank.model.User;
import me.bank.validator.SaveCardValidator;

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
		//默认数据
	    setAttr("selectMarryType", -1);
		setAttr("selectCardType", 11);
		setAttr("selectHome", -1);
		if (user != null) {
			render("add.html");
		} else {
			render("index.html");
		}

	}

	/**
	 * 新增卡号
	 */
	@Before(SaveCardValidator.class)
	public void save() {
		Card card = getModel(Card.class);
		String identity = card.get("identity");
		String cardCode = "";
		if (card != null) {
			int cardType = card.get("type");
			// 生成卡号
			cardCode = randomCard(cardType);
			
			//审核需要普卡不需要审核  1代表通过审核  0代表未通过审核
			if(cardType == 11){
				card.set("status", 1);
			}else{
				//信誉卡审核状态
				card.set("status", 0);
			}
			
			card.set("uuid", cardCode);
			//如果设置密码为空，默认取款密码为卡号后6位数
			String getmp =card.get("password");
			if(ParaKit.isEmpty(getmp)){
				String getmoneyp = "";
				if(cardCode.length()==19){
					getmoneyp = cardCode.substring(13,19);
				}else{
					getmoneyp = cardCode.substring(10,16);
				}
				card.set("password", getmoneyp);
			}
			card.set("createTime", DateKit.getDateTime());
			card.save();
			
			/****
			 * 信誉卡信息填写
			 */
			if(cardType!=11){
				CreditInfo creditInfo = getModel(CreditInfo.class);
				creditInfo.set("uid", getPara("userid"));
				creditInfo.set("cid", card.get("id"));
				creditInfo.set("marry",getPara("credit.marry"));
				creditInfo.set("hosing",getPara("credit.hosing"));
				creditInfo.set("liveTime",getPara("credit.liveTime"));
				creditInfo.set("income",getPara("credit.income"));
				creditInfo.set("company",getPara("credit.company"));
				creditInfo.set("experience",getPara("credit.experience"));
				creditInfo.set("career",getPara("credit.career"));
				creditInfo.set("careerType",getPara("credit.careerType"));
				creditInfo.set("title",getPara("credit.title"));
				creditInfo.set("position",getPara("credit.position"));
				creditInfo.save();
			}
		}
		
		redirect("search?identity=" + identity);
	}

	/**
	 * 判断是否有当前卡号如果有继续生成
	 * 
	 * @return
	 */
	public boolean existCardNumber(String uuid) {
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
		while (true) {
			card = CardKit.generateCardNumber(type);
			
			// 判断有没有本卡号，如果有继续自动生成
			if (!existCardNumber(card)) {
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
				CreditInfo ad = CreditInfo.dao.getCreditInfoByCardId(cardId);
				if(ad!=null){
					CreditInfo.dao.deleteById(ad.get("id"));
				}
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}
	/**
	 * 解锁本卡
	 */
	public void unlock() {
		int cardId = ParaKit.paramToInt(getPara(0), -1);
		Card card = Card.dao.findById(cardId);
		String nowDateStr  = DateKit.getDateTime();
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(card.get("lockTime").toString());
			date2 = new SimpleDateFormat("yyyy-MM-d H:m:s").parse(nowDateStr);
			long l = date2.getTime()-date1.getTime();
			long hours = l/(60*60*1000);
			if(hours>=24){
				card.set("errorNum",0);
				if(card.update()){
					renderJson("msg","解锁成功！");
				}else{
					renderJson("msg","解锁失败！");
				}
			}else{
				renderJson("msg","解锁失败！请满一天后在进行解锁。距离一天还差"+(24-hours)+"小时");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
