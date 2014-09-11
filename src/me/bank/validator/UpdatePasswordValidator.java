package me.bank.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * AdminValidator.
 */
public class UpdatePasswordValidator extends Validator {
	
	protected void validate(Controller controller) {
		
		validateRequiredString("oldPassword", "msg", "数据填写不完!");
		validateRequiredString("newPassword", "msg", "数据填写不完!");
		validateRequiredString("newPassword2", "msg", "数据填写不完!");
		validateEqualField("newPassword", "newPassword2", "msg", "两次输入的密码不一致!");
		
	}
	
	protected void handleError(Controller controller) {
		
		controller.setAttr("status", "error");
		controller.keepPara();
		controller.renderJson();
			
	}
}
