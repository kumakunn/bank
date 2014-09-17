package me.bank.validator;

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
public class GetMoneyValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateString("password", 1, 30, "passwordMsg", "请输入密码");
		validateDouble("money",1,999999999, "moneyMsg","请输入合法的金额");
		int userid = Integer.valueOf(controller.getPara("userid"));
		String password = controller.getPara("password");
		User user = User.dao.findById(userid);
		int errorNum = Integer.valueOf(user.get("errorNum").toString());
		if(errorNum>3){
			addError("lockMsg", "你的账户已经被锁不允许取款，请联系管理员解锁!");
		}
		//取款密码
		String userPass = user.get("getmoneyp").toString();
		if(!userPass.equals(password)){
			addError("passwordMsg","取款密码错误!");
			errorNum++;
			user.set("errorNum", errorNum);
			user.update();
		}
		
	}

	protected void handleError(Controller controller) {
		
		String uuid = controller.getPara("uuid");
		Card card = Card.dao.getCardByUuidJJ(11,uuid);
		User user = User.dao.getUserByIdentity(card.get("identity").toString());
		
		Access access = Access.dao.getAccessByUuid(uuid);
		controller.setAttr("user", user);
		controller.setAttr("card",card);
		controller.setAttr("access", access);

		String actionKey = getActionKey();
		System.out.println("actionKey: " + actionKey);
		controller.render("get.html");

	}
}
