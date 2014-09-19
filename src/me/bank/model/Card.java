package me.bank.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Card model.
 * 
 */

@SuppressWarnings("serial")
public class Card extends Model<Card> {
	public static final Card dao = new Card();

	/**
	 * 根据银行卡号获取银行卡信息
	 * 
	 * @param uuid
	 * @return
	 */
	public Card getCardByUuid(String uuid) {
		return dao.findFirst("select * from card where uuid = ?", uuid);
	}

	/**
	 * 根据银行卡号获取银行卡信息（借记卡）默认传值type=11
	 * 
	 */
	public Card getCardByUuidJJ(int type, String uuid) {
		return dao.findFirst("select * from card where type=? and  uuid = ?",
				type, uuid);
	}

	/**
	 * 根据身份证获取该用户所办理的所有卡的信息
	 * 
	 * @param identity
	 * @return
	 */
	public List<Card> getCardsByIdentity(String identity) {
		return dao.find("select * from card where `identity` = ?", identity);
	}

	public Page<Card> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from card order by id desc");
	}

	/***
	 * 获取待审核信用卡信息
	 * 
	 * @param flag
	 *            代表了关系符号
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Card> paginateForStatus(String flag, int pageNumber,
			int pageSize, int status) {
		String sql = "from card where status = 0 and type ";
		sql = sql + flag;
		sql = sql + " ? order by id desc";
		return paginate(pageNumber, pageSize, "select *", sql, status);
	}

	// 获取卡的类别
	public String getType() {
		int id = get("type");
		if (id != 0) {
			Dictionary dic = Dictionary.dao.findById(id);
			if (dic != null) {
				return dic.get("name");
			} else {
				return "--";
			}
		}
		return "--";

	}

}
