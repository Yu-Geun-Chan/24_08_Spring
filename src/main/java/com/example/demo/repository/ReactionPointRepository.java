package com.example.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Board;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			SELECT COUNT(*) 
			FROM reactionPoint 
			WHERE relTypeCode = #{relTypeCode} AND relId = #{relId} AND `point` = #{point}
			""")
	public int getReactionPointCount(String relTypeCode, int relId, int point);

	@Insert("""
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId}, 
			`point` = #{point}
			ON DUPLICATE KEY UPDATE `point` = #{point},
			updateDate = NOW()
			""")
	public void setReactionPoint(int memberId, String relTypeCode, int relId, int point);
}