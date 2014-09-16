package me.bank.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Access model.
 * 
 */

@SuppressWarnings("serial")
public class Access extends Model<Access> {
	public static final Access dao = new Access();
	/**
	 * 根据银行卡号获取银行卡信息（借记卡）默认传值type=11
	 * 
	 */
	public Access getAccessByUuid(String uuid) {
		return dao.findFirst("select * from access where uuid = ?",uuid);
	}

}
