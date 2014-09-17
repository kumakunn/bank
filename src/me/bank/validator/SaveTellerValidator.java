package me.bank.validator;

import me.bank.model.Teller;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * SaveTellerValidator
 */
public class SaveTellerValidator extends Validator {

	protected void validate(Controller controller) {

		// 表单中包含文件，所以先调用getFile方法，剩余的参数才可用
		UploadFile file = controller.getFile();
		if (file == null) {
			addError("imageMsg", "请选择柜员的照片！");
		}

		// 是否填写了柜员
		validateRequiredString("teller.name", "nameMsg", "请输入姓名!");
		validateRequiredString("teller.national", "nationalMsg", "请输入所属!");

		validateRequired("teller.identity", "identityMsg", "请输入合法的身份证号!");
		validateRegex("teller.birth", "\\d{4}-\\d{2}-\\d{2}", "birthMsg", "请输入合法的出生日期!");
		validateString("teller.birthplace", 6, 50, "birthplaceMsg", "请输入6-50字的合法的籍贯");
		validateString("teller.desc", 10, 500, "descMsg", "请输入20-500之间的描述");
		validateString("teller.address", 6, 50, "addressMsg", "请输入6-50字的合法的地址");
		validateRegex("teller.phone", "1[0-9]{10}", "phoneMsg", "请输入合法的手机号!");
		validateEmail("teller.email", "emailMsg", "请输入合法的email!");

	}

	protected void handleError(Controller controller) {

		controller.keepModel(Teller.class);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("add.html");

	}
}
