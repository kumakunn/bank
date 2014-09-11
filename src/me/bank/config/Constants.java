package me.bank.config;

public class Constants {

	// 每页数据条数
	public static final int PAGE_SIZE = 15;

	// 搜索的结果分页
	public static final int SEARCH_PAGE = 1;

	// 非搜索的结果分页
	public static final int NOT_SEARCH_PAGE = 0;

	// 存放在session中的搜索条件
	public static final String SEARCH_SESSION_KEY = "search";

	// 上传文件的最大容量
	public static final int MAX_FILE_SIZE = 10 * 1024 * 1024;

	// 上传文件的保存目录
	public static final String ATTACHMENT_UPLOAD_PATH = "/upload/";

	// 上传文件的临时路径
	public static final String ATTACHMENT_TEMP_PATH = ATTACHMENT_UPLOAD_PATH;

	// 头像文件的保存路径
	public static final String ATTACHMENT_AVATAR_PATH = ATTACHMENT_UPLOAD_PATH + "avatar/";

	// 图片文件保存路径（用UEditor上传的图片）
	public static final String ATTACHMENT_IMAGE_PATH = ATTACHMENT_UPLOAD_PATH + "images/";

}
