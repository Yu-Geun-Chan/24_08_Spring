package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // int, String, String, String, String의 인자를 가지는 생성자(변수를 선언한 모든 타입)
@NoArgsConstructor // 인자가 없는 생성자
@Data // getter, setter, toString, hashCode가 들어있다.
public class Member {
	
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private String name;
	private String nickname;
	private String cellphoneNum;
	private String email;
	private boolean delStatus;
	private String delDate;
	
	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br />");
	}

	public String getForPrintType1UpdateDate() {
		return updateDate.substring(2, 16).replace(" ", "<br />");
	}

	public boolean isAdmin() {
		return this.authLevel == 7;
	}
	
	private int authLevel;
	
}
