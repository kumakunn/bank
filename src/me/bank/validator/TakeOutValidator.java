package me.bank.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * TakeOutValidator
 */
public class TakeOutValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateDouble("money",1,999999999, "msg","输入的金额不合法！");
	}

	protected void handleError(Controller controller) {

		controller.setAttr("status", "error");
		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.renderJson();

	}
}
