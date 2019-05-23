package top.ghsos.tools.idcard.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CnSecondCardModel implements ICardModel {
	
	private final static Map<String, String> provinceCodeMap;
	
	/**
	 *   1-17位权重值
	 */
	private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/** 第18位校验值 其中X=10 */
	private static final int verifyCode[] = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	
	static {
		provinceCodeMap = new HashMap<String, String>(40);
		provinceCodeMap.put("11", "北京");
		provinceCodeMap.put("12", "天津");
		provinceCodeMap.put("13", "河北");
		provinceCodeMap.put("14", "山西");
		provinceCodeMap.put("15", "内蒙古");
		provinceCodeMap.put("21", "辽宁");
		provinceCodeMap.put("22", "吉林");
		provinceCodeMap.put("23", "黑龙江");
		provinceCodeMap.put("31", "上海");
		provinceCodeMap.put("32", "江苏");
		provinceCodeMap.put("33", "浙江");
		provinceCodeMap.put("34", "安徽");
		provinceCodeMap.put("35", "福建");
		provinceCodeMap.put("36", "江西");
		provinceCodeMap.put("37", "山东");
		provinceCodeMap.put("41", "河南");
		provinceCodeMap.put("42", "湖北");
		provinceCodeMap.put("43", "湖南");
		provinceCodeMap.put("44", "广东");
		provinceCodeMap.put("45", "广西");
		provinceCodeMap.put("46", "海南");
		provinceCodeMap.put("50", "重庆");
		provinceCodeMap.put("51", "四川");
		provinceCodeMap.put("52", "贵州");
		provinceCodeMap.put("53", "云南");
		provinceCodeMap.put("54", "西藏");
		provinceCodeMap.put("61", "陕西");
		provinceCodeMap.put("62", "甘肃");
		provinceCodeMap.put("63", "青海");
		provinceCodeMap.put("64", "宁夏");
		provinceCodeMap.put("65", "新疆");
		provinceCodeMap.put("71", "台湾");
		provinceCodeMap.put("81", "香港");
		provinceCodeMap.put("82", "澳门");
		provinceCodeMap.put("91", "国外");
	}
	
	
	private String cardNO;

	private String provinceCode;
	
	private String cityCode;
	
	private String countyCode;
	
	private Date birthDate;
	
	private Gender gender;
	
	
	public CnSecondCardModel() {
	}
	
	public CnSecondCardModel(String cardNO) {
		this.cardNO = cardNO;
	}
	
	public static CnSecondCardModel parse(String cardNO) {
		CnSecondCardModel m = new CnSecondCardModel(cardNO);
		if (!m.verify()) {
			return null;
		}
		m.provinceCode = m.cardNO.substring(0, 2);
		
		if (!provinceCodeMap.containsKey(m.provinceCode)) {
			return null;
		}
		
		m.cityCode = m.cardNO.substring(2, 4);
		m.countyCode = m.cardNO.substring(4, 6);
		m.birthDate = m.parseBirthDate();
		m.gender = m.parseGender();
		return m;
	}
	
	
	
	/**
	 * 检验号码是否符合规则
	 * @param idCard
	 * @return
	 */
	@Override
	public boolean verify() {
		if (this.cardNO == null) {
			return false;
		}
		int idCardLen = 18;

		String ve = this.cardNO.trim();
		if (ve.length() != idCardLen) {
			return false;
		}
		char[] idChars = this.cardNO.toCharArray();

		int[] idInts = new int[idCardLen];
		for (int n = 0; n < idCardLen - 1; n++) {
			if (!Character.isDigit(idChars[n])) {
				return false;
			}
			try {
				idInts[n] = Integer.parseInt(String.valueOf(idChars[n]));
			} catch (NumberFormatException ne) {
				return false;
			}
		}
		char checkCode = idChars[idCardLen - 1];
		if (Character.isDigit(checkCode)) {
			try {
				idInts[idCardLen - 1] = Integer.parseInt(String.valueOf(checkCode));
			} catch (NumberFormatException ne) {
				return false;
			}
		} else if ('x' == checkCode || 'X' == checkCode) {
			idInts[idCardLen - 1] = 10;
		} else {
			return false;
		}

		int sumCode = 0;
		for (int n = 0; n < idCardLen - 1; n++) {
			sumCode += idInts[n] * CnSecondCardModel.power[n];
		}
		if (idInts[idCardLen - 1] != CnSecondCardModel.verifyCode[sumCode % 11]) {
			return false;
		}
		return true;
	}
	

	
	private Gender parseGender() {
		String s = this.cardNO.substring(16, 17);
		if (Integer.parseInt(s) % 2 != 0) {
			return Gender.MAN;
		} else {
			return Gender.WOMAN;
		}
	}
	
	private Date parseBirthDate() {
		String yyyyMMdd = this.cardNO.substring(6, 14);
		try {
			Date dd = new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd);
			return dd;
		} catch (ParseException e) {
		}
		return null;
	}
	
	
	

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
}
