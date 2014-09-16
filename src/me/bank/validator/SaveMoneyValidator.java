package me.bank.validator;

import me.bank.kit.ParaKit;
import me.bank.model.Access;
import me.bank.model.Card;
import me.bank.model.Detail;
import me.bank.model.Teller;
import me.bank.model.User;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * GetMoneyValidator
 */
public class SaveMoneyValidator extends Validator {

	protected void validate(Controller controller) {
		validateDouble("money",1,999999999, "moneyMsg","请输入合法的金额");
		
	}

	protected void handleError(Controller controller) {
		
		String uuid = controller.getPara("uuid");
		Card card = Card.dao.getCardByUuidJJ(11,uuid);
		User user = User.dao.getUserByIdentity(card.get("identity").toString());
		Access access = Access.dao.getAccessByUuid(uuid);
		controller.setAttr("user", user);
		controller.setAttr("card",card);
		controller.setAttr("access", access);
		controller.setAttr("moneyMsg", "请输入合法的金额");
		
		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("save.html");

	}
}
