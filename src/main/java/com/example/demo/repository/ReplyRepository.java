package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;

@Mapper
public interface ReplyRepository {

	@Select("""
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			INNER JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.relTypeCode = #{relTypeCode}
			AND R.relId = #{relId}
			ORDER BY R.id ASC;
			""")
	public List<Reply> getForPrintReplies(String relTypeCode, int relId);

	@Insert("""
			INSERT INTO reply
			SET regDate = NOW(),
			updateDate = NOW(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{loginedMemberId},
			`body` = #{body}
			""")
	public void writeReply(int loginedMemberId, String relTypeCode, int relId, String body);
		
	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();
}
