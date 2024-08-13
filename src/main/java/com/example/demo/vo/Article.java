package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor // int, String, String의 인자를 가지는 생성자(변수를 선언한 모든 타입)
@NoArgsConstructor // 인자가 없는 생성자
@Data // getter, setter, toString, hashCode가 들어있다.
public class Article {

	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
}
