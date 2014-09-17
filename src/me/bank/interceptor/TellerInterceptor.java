package me.bank.interceptor;

import java.util.List;

import me.bank.model.Dictionary;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * TellerInterceptor
 */
public class TellerInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();
		ai.invoke();

		// 读取政治面貌
		List<Dictionary> features = Dictionary.dao.getDictionaryByKey("feature");

		System.out.println("features.size = " + features.size());

		controller.setAttr("features", features);

		// 读取学历
		List<Dictionary> educations = Dictionary.dao.getDictionaryByKey("education");

		System.out.println("educations.size = " + educations.size());

		controller.setAttr("educations", educations);

	}
}
