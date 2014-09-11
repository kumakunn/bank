package me.bank.interceptor;

import java.util.List;

import me.bank.model.Admin;
import me.bank.model.Permission;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * SessionInterceptor
 */
public class SessionInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();

		Admin admin = controller.getSessionAttr("admin");
		String roleName = controller.getSessionAttr("roleName");
		List<Permission> permissions = controller.getSessionAttr("permissions");

		if (admin != null && roleName != null && permissions != null) {

			controller.setAttr("admin", admin);
			controller.setAttr("roleName", roleName);
			controller.setAttr("permissions", permissions);
			
			String actionKey = ai.getActionKey();
			
			controller.setAttr("current", actionKey);
			
			System.out.println("actionKey: " + actionKey);
			
			for(Permission permission: permissions) {
				
				if(actionKey.contains(permission.getStr("url"))) {
					
					controller.setAttr("title", permission.getStr("name"));
					System.out.println("title: " + permission.getStr("name"));
					
					break;
				}
				
			}
			
			ai.invoke();

		} else {

			controller.redirect("/admin/login");

		}

	}
}
