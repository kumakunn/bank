package me.bank.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * Dictionary model.
 * 
 */

@SuppressWarnings("serial")
public class Dictionary extends Model<Dictionary> {
	public static final Dictionary dao = new Dictionary();

	/**
	 * 根据Key来获取所有的数据
	 * 
	 * @param key
	 * @return
	 */
	public List<Dictionary> getDictionaryByKey(String key) {

		return dao.find("select * from dictionary where `key` = ?", key);

	}

}
