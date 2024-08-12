package com.example.demo.vo;

import lombok.Getter;

public class ResultData {
	@Getter
	private String ResultCode;
	@Getter
	private String msg;
	@Getter
	private Object data;
	
//	public static ResultData from(String ResultCode, String msg) {
//		return ResultData.from(ResultCode, msg, null);
//	}
//	
//	public static ResultData from(String ResultCode, String msg, Object data1) {
//		ResultData rd = new ResultData();
//		rd.ResultCode = ResultCode;
//		rd.msg = msg;
//		rd.data1 = data1;
//		
//		return rd;
//	}
	
//	public static ResultData from(String ResultCode, String msg, Object data1, Object data2) {
//		ResultData rd = new ResultData();
//		rd.ResultCode = ResultCode;
//		rd.msg = msg;
//		rd.data1 = data1;
//		rd.data2 = data2;
//		
//		return rd;
//	}
	
	public static ResultData from(String ResultCode, String msg, Object ... data) {
		ResultData rd = new ResultData();
		rd.ResultCode = ResultCode;
		rd.msg = msg;
		rd.data = data;
		
		return rd;
	}
}
