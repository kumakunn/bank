package me.bank.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Card model.
 * 
 */

@SuppressWarnings("serial")
public class Card extends Model<Card> {
	public static final Card dao = new Card();

	public Page<Card> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from card order by id desc");
	}

}
