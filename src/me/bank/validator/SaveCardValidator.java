package me.bank.validator;

import me.bank.model.CreditInfo;
import me.bank.model.User;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * GetMoneyValidator
 */
public class SaveCardValidator extends Validator {

	protected void validate(Controller controller) {
		int cardType = controller.getParaToInt("card.type");
		if (cardType != 11) {
			// 信用卡需要验证
			validateInteger("credit.nowLiveTime", 1, 100, "nowLiveTimeMsg", "必须为数字");
			validateInteger("credit.companyTime", 1, 100, "companyTimeMsg", "现单位工作年度错误1-100之间");
			validateDouble("credit.personalIncome", 1, 999999999, "personalIncomeMsg", "年收入错误");
			validateString("credit.company", 5, 30, "companyMsg", "填写长度应在5-30之间");
			validateString("credit.occupation", 1, 10, "occupationMsg", "职业描述在1-10个字");
			validateString("credit.occupationType", 1, 10, "occupationTypeMsg", "行业种类在1-10个字");
			validateString("credit.title", 1, 10, "titleMsg", "职称在1-10个字");
			validateString("credit.position", 1, 10, "positionMsg", "职务描述在1-10个字");
		} else {

		}

	}

	protected void handleError(Controller controller) {
		String identity = controller.getPara("card.identity");
		User user = User.dao.getUserByIdentity(identity);
		controller.setAttr("user", user);
		controller.keepModel(CreditInfo.class);
		controller.setAttr("selectCardType", controller.getParaToInt("card.type"));
		controller.setAttr("selectMarryType", controller.getParaToInt("credit.marry"));
		controller.setAttr("selectHome", controller.getParaToInt("credit.hosing"));

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
