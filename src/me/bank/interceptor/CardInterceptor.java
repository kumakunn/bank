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
		/****** 信用卡所需 *****/
		// 读取婚姻状况
		List<Dictionary> marryTypes = Dictionary.dao.getDictionaryByKey("marryType");

		System.out.println("marryTypes.size = " + marryTypes.size());

		controller.setAttr("marryTypes", marryTypes);

		// 读取婚姻状况
		List<Dictionary> homes = Dictionary.dao.getDictionaryByKey("home");

		System.out.println("homes.size = " + homes.size());

		controller.setAttr("homes", homes);

	}
}
