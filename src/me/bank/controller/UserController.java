package me.bank.controller;

import java.util.ArrayList;
import java.util.List;

import me.bank.config.Constants;
import me.bank.kit.DateKit;
import me.bank.kit.ParaKit;
import me.bank.kit.UploadKit;
import me.bank.model.Card;
import me.bank.model.User;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

/**
 * UserController
 * 
 * 用户信息管理
 * 
 */
public class UserController extends Controller {

	public void index() {

		render("index.html");
	}

	public void search() {

		String identity = getPara("identity");
		String uuid = getPara("card");

		setAttr("searchCard", uuid);
		setAttr("searchIdentity", identity);

		User user = User.dao.getUserByIdentity(identity);
		setAttr("user", user);
		
		System.out.println("user = " + user);

		List<Card> cardList = new ArrayList<Card>();

		cardList.addAll(user.getCards());

		setAttr("cardList", cardList);

		render("index.html");

	}

	public void add() {
		render("add.html");
	}

	public void save() {

		UploadFile file = getFile("user.image", Constants.ATTACHMENT_TEMP_PATH,
				Constants.MAX_FILE_SIZE);

		// 保存文件并获取保存在数据库中的路径
		String savePath = UploadKit.saveAvatarImage(file.getFile());

		User user = getModel(User.class);

		System.out.println("savePath: " + savePath);

		// 设置头像路径
		user.set("image", savePath);

		if (user.getInt("id") == null) {

			// 设置注册/修改时间
			user.set("time", DateKit.getDateTime());

			user.save();
		} else {
			user.update();
		}

		redirect("search?identity=" + user.getStr("identity"));

	}

}
