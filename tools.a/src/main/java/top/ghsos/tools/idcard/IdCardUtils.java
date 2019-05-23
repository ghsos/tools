package top.ghsos.tools.idcard;

import top.ghsos.tools.idcard.model.CnSecondCardModel;
import top.ghsos.tools.idcard.model.ICardModel;
import top.ghsos.tools.idcard.model.ICardModel.CardType;

public final class IdCardUtils {
	
	private IdCardUtils() {
		// private construct
	}
	
	public static boolean verifyCnSecond(String cardNO) {
		return verify(cardNO, CardType.CN_SECOND);
	}

	public static boolean verify(String cardNO, CardType type) {
		if (cardNO == null || type == null) {
			return false;
		}
		ICardModel cm = null;
		switch (type) {
		case CN_SECOND:
			cm = new CnSecondCardModel(cardNO);
			break;
		default:
			break;
		}
		if (cm == null) {
			return false;
		}
		return cm.verify();
	}
	
	
	
	
	
	
}
