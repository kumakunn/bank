package me.bank.validator;

import me.bank.model.User;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * SaveDepartmentValidator
 */
public class SaveUserValidator extends Validator {

	protected void validate(Controller controller) {

		// 表单中包含文件，所以先调用getFile方法，剩余的参数才可用
		UploadFile file = controller.getFile();
		if (file == null) {
			addError("imageMsg", "请选择身份证图片！");
		}

		validateRequiredString("user.name", "nameMsg", "请输入名称!");
		validateRequiredString("user.national", "nationalMsg", "请输入民族!");
		validateRequired("user.identity", "identityMsg", "请输入合法的身份证号!");
		validateString("user.birthplace", 6, 50, "birthplaceMsg", "请输入6-50字的合法的籍贯");
		validateRegex("user.birth", "\\d{4}-\\d{2}-\\d{2}", "birthMsg", "请输入合法的出生日期!");
		validateString("user.address", 6, 50, "addressMsg", "请输入6-50字的合法的地址");
		validateRegex("user.phone", "1[0-9]{10}", "phoneMsg", "请输入合法的手机号!");

	}

	protected void handleError(Controller controller) {

		controller.keepModel(User.class);
		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
