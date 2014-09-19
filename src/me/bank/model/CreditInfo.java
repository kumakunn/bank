package me.bank.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Detail model.
 * 
 */

@SuppressWarnings("serial")
public class CreditInfo extends Model<CreditInfo> {
	public static final CreditInfo dao = new CreditInfo();

	public Page<CreditInfo> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from credit_info order by id desc");
	}

	public CreditInfo getCreditInfoByUserId(int userId) {
		return CreditInfo.dao.findFirst(
				"select * from credit_info where uid = ?", userId);
	}

	public CreditInfo getCreditInfoByCardId(int cid) {
		return CreditInfo.dao.findFirst(
				"select * from credit_info where cid = ?", cid);
	}

}
