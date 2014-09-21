package me.bank.validator;

import me.bank.model.Card;
import me.bank.model.User;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * SaveMoneyValidator
 */
public class SaveMoneyValidator extends Validator {

	protected void validate(Controller controller) {
		validateDouble("money", 1, 999999999, "moneyMsg", "请输入合法的金额");

	}

	protected void handleError(Controller controller) {

		String uuid = controller.getPara("uuid");
		Card card = Card.dao.getCardByUuid(uuid);
		User user = User.dao.getUserByIdentity(card.get("identity").toString());
		controller.setAttr("user", user);
		controller.setAttr("card", card);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("save.html");

	}
}
