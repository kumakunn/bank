package me.bank.model;

import me.bank.kit.ParaKit;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Detail model.
 * 
 */

@SuppressWarnings("serial")
public class Detail extends Model<Detail> {
	public static final Detail dao = new Detail();

	public Page<Detail> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from detail order by id desc");
	}

	// 根据identity获取本人姓名
	public String getIdentity() {
		String identity = get("identity");
		if (!ParaKit.isEmpty(identity)) {
			User user = User.dao.findFirst(
					"select * from user where identity=?", identity);
			if (user != null) {
				return user.get("name");
			} else {
				return "--";
			}
		}
		return "--";

	}
}
