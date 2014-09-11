package me.bank.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * LoginValidator
 */
public class LoginValidator extends Validator {

	protected void validate(Controller controller) {

		validateRequiredString("account", "accountMsg", "账号不能为空!");
		validateRequiredString("password", "passwordMsg", "密码不能为空!");

	}

	protected void handleError(Controller controller) {
		controller.keepPara();
		controller.render("login.html");
	}
}
