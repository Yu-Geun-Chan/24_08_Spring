package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;

@Mapper
public interface ReplyRepository {

	// DB에서 작성댓글들을 가져오기 위한 쿼리
	@Select("""
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			INNER JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.relTypeCode = #{relTypeCode}
			AND R.relId = #{relId}
			ORDER BY R.id ASC;
			""")
	public List<Reply> getForPrintReplies(int loginMemberId, String relTypeCode, int relId);

	// DB에 댓글 작성을 하기 위한 쿼리
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

    @Select("""
    		SELECT COUNT(*) 
    		FROM reply 
    		WHERE relId = #{articleId} 
    		AND relTypeCode = 'article'
    		""")
    public int getRepliesCount(int articleId);
	
	// 마지막 댓글의 id를 가져오는 쿼리
	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();
}
