package me.bank.model;

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

}
