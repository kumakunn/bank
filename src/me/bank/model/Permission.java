package me.bank.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * Permission model.
 * 
 */

@SuppressWarnings("serial")
public class Permission extends Model<Permission> {
	public static final Permission dao = new Permission();

	private List<Permission> subPermissions;
	
	/**
	 * 获取子权�?
	 * 
	 * @return
	 */
	public List<Permission> initSubPermissions() {

		if(subPermissions == null) {
			
			String sql = "select * from permission where pid = ? order by id asc";
			setSubPermissions(Permission.dao.find(sql, get("id"))); 
			
		}

		return subPermissions;
	}

	public List<Permission> getSubPermissions() {
		return subPermissions;
	}

	public void setSubPermissions(List<Permission> subPermissions) {
		this.subPermissions = subPermissions;
	}

}
