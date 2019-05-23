package top.ghsos.tools.idcard.model;

public interface ICardModel {

	public enum Gender {
		MAN,
		WOMAN,
		UNKNOWN,
	}
	
	public enum CardType {
		CN_SECOND
	}
	
	boolean verify();
}
