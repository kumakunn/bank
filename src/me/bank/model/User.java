package me.bank.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * User model.
 * 
 */

@SuppressWarnings("serial")
public class User extends Model<User> {
	public static final User dao = new User();

	private List<Card> cards;

	/**
	 * 根据身份证获取用户信息
	 * 
	 * @param identity
	 * @return
	 */
	public User getUserByIdentity(String identity) {

		return dao.findFirst("select * from user where `identity` = ?", identity);

	}

	/**
	 * 根据身份证获取该用户所办理的所有卡的信息
	 * 
	 * @param identity
	 * @return
	 */
	public List<Card> getCards() {
		if (cards == null) {
			cards = Card.dao.find("select * from card where `identity` = ? order by createTime desc", get("identity"));
		}
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Page<User> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from user");
	}

}
