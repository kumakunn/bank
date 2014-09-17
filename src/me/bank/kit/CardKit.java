package me.bank.kit;

public class CardKit {

	/**
	 * ������ɿ���
	 */
	public static String generateCardNumber(int type) {
		String card = "";
		
		/**
		 * �������ɹ����ǿ�ͷ����+���б���+ʣ������� 622836 �й�ũҵ��������Ҵ��ǿ� ������ο����ǿ��� 622837
		 * �й�ũҵ��������Ҵ��ǿ� ������ο����ǿ��տ� 62284 �й�ũҵ��������ͨ����ǿ� 95599 �й�ũҵ��������ͨ����ǿ�
		 * ��ǿ���19λ�����ǿ���׼���ǿ���16λ ��ľ˹�д���Ϊ 8019
		 ***/
		
		// ��ǿ�ͷ
		String[] header1 = new String[] { "62284", "95599" };
		// ���ǿ�ͷ
		String[] header2 = new String[] { "622836", "622837" };
		// ���б���
		String cityNum = "8019";
		
		int headerFlag = Math.random() > 0.5 ? 0 : 1;
		
		if (type == 11) {
			// ��ǿ�
			card += header1[headerFlag] + cityNum;
			// ����ʣ��10λ��
			for (int i = 0; i < 10; i++) {
				String s = String.valueOf((int) (Math.random() * 10));
				card += s;
			}

			System.out.println(card.length() + "��ǿ�:" + card);

		} else if (type == 12) {
			// ���ǿ�
			card += header2[headerFlag] + cityNum;
			// ����ʣ��6λ��
			for (int i = 0; i < 6; i++) {
				String s = String.valueOf((int) (Math.random() * 10));
				card += s;
			}

			System.out.println(card.length() + "���ǿ�:" + card);
		}
			
		
		return card;
	}

}
