package me.bank.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * User model.
 * 
 */

@SuppressWarnings("serial")
public class User extends Model<User> {
	public static final User dao = new User();

	public Page<User> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from user");
	}

}
