package me.bank.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Teller model.
 * 
 */

@SuppressWarnings("serial")
public class Teller extends Model<Teller> {
	public static final Teller dao = new Teller();

	/**
	 * 分页处理
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Teller> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from teller order by id desc");
	}

	public Teller getTellerByIdentity(String identity) {
		return dao
				.findFirst("select * from teller where identity =?", identity);
	}

	public Teller getTellerByName(String name) {
		return dao.findFirst("select * from teller where name =?", name);
	}

	// 获取政治面貌
	public String getFeature() {
		int id = get("feature");
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

	// 获取学历
	public String getEducation() {
		int id = get("education");
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

	// 获取性别
	public String getSex() {
		int id = get("sex");
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
