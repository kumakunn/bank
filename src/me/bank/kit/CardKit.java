package me.bank.kit;

public class CardKit {

	/**
	 * 随机生成卡号
	 */
	public static String generateCardNumber(int type) {
		String card = "";
		
		/**
		 * 卡号生成规则是开头编码+城市编码+剩余随机数 622836 中国农业银行人民币贷记卡 香港旅游卡贷记卡金卡 622837
		 * 中国农业银行人民币贷记卡 香港旅游卡贷记卡普卡 62284 中国农业银行世纪通宝借记卡 95599 中国农业银行世纪通宝借记卡
		 * 借记卡是19位，贷记卡和准贷记卡是16位 佳木斯市代码为 8019
		 ***/
		
		// 借记卡头
		String[] header1 = new String[] { "62284", "95599" };
		// 货记卡头
		String[] header2 = new String[] { "622836", "622837" };
		// 城市编码
		String cityNum = "8019";
		
		int headerFlag = Math.random() > 0.5 ? 0 : 1;
		
		if (type == 11) {
			// 借记卡
			card += header1[headerFlag] + cityNum;
			// 补填剩余10位数
			for (int i = 0; i < 10; i++) {
				String s = String.valueOf((int) (Math.random() * 10));
				card += s;
			}

			System.out.println(card.length() + "借记卡:" + card);

		} else if (type == 12) {
			// 货记卡
			card += header2[headerFlag] + cityNum;
			// 补天剩余6位数
			for (int i = 0; i < 6; i++) {
				String s = String.valueOf((int) (Math.random() * 10));
				card += s;
			}

			System.out.println(card.length() + "货记卡:" + card);
		}
			
		
		return card;
	}

}
