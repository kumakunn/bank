package me.bank.interceptor;

import java.util.List;

import me.bank.model.Dictionary;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * CardInterceptor
 */
public class CardInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();
		ai.invoke();

		// 读取银行卡类型
		List<Dictionary> cardTypes = Dictionary.dao.getDictionaryByKey("cardType");

		System.out.println("cardTypes.size = " + cardTypes.size());

		controller.setAttr("cardTypes", cardTypes);

	}
}
