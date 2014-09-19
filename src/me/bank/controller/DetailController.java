package me.bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bank.config.Constants;
import me.bank.kit.ParaKit;
import me.bank.model.Detail;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * DetailController
 * 
 * 明细管理
 * 
 */
public class DetailController extends Controller {

	public void index() {

		render("index.html");
	}

	/**
	 * 检索
	 */
	public void search() {

		String uuid = getPara("uuid");
		String identity = getPara("identity");

		if (ParaKit.isEmpty(uuid) && ParaKit.isEmpty(identity)) {

			setAttr("nothing", true);
			render("index.html");
			return;

		}

		if (ParaKit.isEmpty(getPara("s"))) {

			// 说明当前请求是搜索数据的post请求，并非搜索的分页请求
			// 在这里执行搜索操作，并将结果保存到缓存中

			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("uuid", uuid);
			queryParams.put("identity", identity);
			setSessionAttr(Constants.SEARCH_SESSION_KEY, queryParams);

		}

		int page = ParaKit.paramToInt(getPara("p"), 1);

		if (page < 1) {
			page = 1;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("from detail where id > 0");

		HashMap<String, String> queryParams = getSessionAttr(Constants.SEARCH_SESSION_KEY);
		List<Object> params = new ArrayList<Object>();

		if (queryParams != null) {

			uuid = queryParams.get("uuid");

			if (!ParaKit.isEmpty(uuid)) {
				sb.append(" and uuid = ?");
				params.add(uuid);
			}

			identity = queryParams.get("identity");

			if (!ParaKit.isEmpty(identity)) {
				sb.append(" and identity = ?");
				params.add(identity);
			}

			setAttr("searchUuid", uuid);
			setAttr("searchIdentity", identity);
			setAttr("searchPage", Constants.SEARCH_PAGE);

		}

		sb.append(" order by time desc");

		// 明细列表
		Page<Detail> detailList = Detail.dao.paginate(page, Constants.PAGE_SIZE, "select *",
				sb.toString(), params.toArray());

		setAttr("detailList", detailList);

		render("index.html");
	}

}
