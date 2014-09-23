package me.bank.validator;

import me.bank.model.CreditInfo;
import me.bank.model.User;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * SaveCardValidator
 */
public class SaveCardValidator extends Validator {

	protected void validate(Controller controller) {
		int cardType = controller.getParaToInt("card.type");
		if (cardType != 11) {
			// 信用卡需要验证
			validateInteger("credit.liveTime", 1, 100, "liveTimeMsg", "必须为数字");
			validateDouble("credit.income", 1, 999999999, "incomeMsg", "年收入错误");
			validateString("credit.company", 3, 30, "companyMsg", "填写长度应在3-30之间");
			validateInteger("credit.experience", 1, 30, "experienceMsg", "现单位工作年度错误1-30之间");
			validateString("credit.career", 1, 10, "careerMsg", "职业描述在1-10个字");
			validateString("credit.careerType", 1, 10, "careerTypeMsg", "行业种类在1-10个字");
			validateString("credit.title", 1, 10, "titleMsg", "职称在1-10个字");
			validateString("credit.position", 1, 10, "positionMsg", "职务描述在1-10个字");
		} else {

		}

	}

	protected void handleError(Controller controller) {
		
		System.out.println("SaveCardValidator->handleError");
		
		String identity = controller.getPara("card.identity");
		User user = User.dao.getUserByIdentity(identity);
		
		controller.setAttr("user", user);
		controller.keepModel(CreditInfo.class);
		controller.setAttr("selectCardType", controller.getParaToInt("card.type"));
		controller.setAttr("selectMarryType", controller.getParaToInt("credit.marry"));
		controller.setAttr("selectHome", controller.getParaToInt("credit.hosing"));

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("index.html");

	}
}
