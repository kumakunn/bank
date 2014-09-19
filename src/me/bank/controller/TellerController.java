package me.bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import me.bank.interceptor.TellerInterceptor;
import me.bank.config.Constants;
import me.bank.kit.Cn2Spell;
import me.bank.kit.DateKit;
import me.bank.kit.UUID;
import me.bank.kit.UploadKit;
import me.bank.model.Admin;
import me.bank.model.Teller;
import me.bank.validator.SaveTellerValidator;
import me.bank.kit.ParaKit;
/**
 * CardController
 * 
 * 明细管理
 * 
 */
@Before(TellerInterceptor.class)
public class TellerController extends Controller {

	public void index() {
		
		// 判断当前是否是搜索的数据进行的分页
		// 如果是搜索的数据，则跳转至search方法处理
		if (!ParaKit.isEmpty(getPara("s"))) {

			search();

			return;
		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}
		// 读取所有的柜员
		Page<Teller> tellerList = Teller.dao.paginate(page, Constants.PAGE_SIZE);
		setAttr("tellerList", tellerList);
		
		setAttr("searchUuid", "");
		setAttr("searchName", "");
		setAttr("searchSex", -1);
		setAttr("searchPage", Constants.NOT_SEARCH_PAGE);
		
		render("index.html");
	}

	
	
	
	/**
	 * 搜索
	 */
	public void search() {
		if (ParaKit.isEmpty(getPara("s"))) {

			// 说明当前请求是搜索数据的post请求，并非搜索的分页请求
			// 在这里执行搜索操作，并将结果保存到缓存中

			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("uuid", getPara("uuid"));
			queryParams.put("name", getPara("name"));
			queryParams.put("sex", getPara("sex"));

			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from teller where id > 0");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			String uuid = queryParams.get("uuid");

			if (!ParaKit.isEmpty(uuid)) {
				sb.append(" and uuid like ?");
				params.add("%" + uuid + "%");
			}

			String name = queryParams.get("name");

			if (!ParaKit.isEmpty(name)) {
				sb.append(" and name like ?");
				params.add("%" + name + "%");
			}

			int sex = Integer.valueOf(queryParams.get("sex").toString());

			if (sex > -1) {
				sb.append(" and sex like ?");
				params.add("%" + sex + "%");
			}

			setAttr("searchUuid", uuid);
			setAttr("searchName", name);
			setAttr("searchSex", sex);

			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		// 柜员列表
		Page<Teller> tellerList = Teller.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("tellerList", tellerList);

		render("index.html");

	}

	
	public void add() {
		render("add.html");
	}
	
	/**
	 * 添加/修改柜员信息处理方法
	 */
	@Before(SaveTellerValidator.class)
	public void save() {

		UploadFile file = getFile("teller.image", Constants.ATTACHMENT_TEMP_PATH,
				Constants.MAX_FILE_SIZE);

		// 保存文件并获取保存在数据库中的路径
		String savePath = UploadKit.saveAvatarImage(file.getFile());

		Teller teller = getModel(Teller.class);

		System.out.println("savePath: " + savePath);

		// 设置头像路径
		teller.set("image", savePath);

		// 设置权限为柜员
		teller.set("rid", 3);

		// 排序位置
		if (teller.get("sort") == null || teller.get("sort").equals("")) {
			teller.set("sort", 1);
		}
		if (null == teller.getInt("id")) {
			// 设置注册时间
			teller.set("time", DateKit.getDateTime());
			teller.set("uuid", UUID.randomUUID());
			teller.save();
			
			
			//新增柜员登陆账号密码
				//账号为用户的姓名的拼音密码身份证后六位
			String name = teller.get("name");
			Cn2Spell cs = Cn2Spell.getInstance();
			name = cs.getSpelling(name);
			Admin admin = new Admin();
			admin.set("account", name);
			String password = teller.get("identity").toString();
			if(password.length()==15){
				password = password.substring(9,15);
			}else{
				password = password.substring(12,18);
			}
			admin.set("password", password);
			//角色代码
			admin.set("rid", 3);
			//柜员id
			admin.set("tid", teller.get("id"));
			admin.save();
		
		} else {
			teller.update();
		}
		System.out.println("柜员信息保存成功");
		redirect("index.html");
	}

	
	/**
	 * 跳转编辑页面
	 * 
	 */
	public void edit() {
		int tellerId = getParaToInt(0);
		setAttr("teller", Teller.dao.findById(tellerId));
		render("add.html");
	}
	
	/**
	 * 删除信息
	 */
	public void delete() {
		int tellerId = ParaKit.paramToInt(getPara(0), -1);

		if (tellerId > -1) {
			if (Teller.dao.deleteById(tellerId)) {
				renderJson("msg", "删除成功！");
			}
		} else {
			renderJson("msg", "删除失败！");
		}

	}
}
