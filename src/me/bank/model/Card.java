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
	 * 根据身份证获取该用户所办理的所有卡的信息
	 * 
	 * @param identity
	 * @return
	 */
	public List<Card> getCardsByIdentity(String identity) {
		return dao.find("select * from card where `identity` = ?", identity);
	}

	public Page<Card> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from card order by id desc");
	}

}
