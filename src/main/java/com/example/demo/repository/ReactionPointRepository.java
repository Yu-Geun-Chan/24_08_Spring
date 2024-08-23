package com.example.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Board;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			SELECT IFNULL(SUM(RP.point),0)
			FROM reactionPoint AS RP
			WHERE RP.relTypeCode = #{relTypeCode}
			AND RP.relId = #{redId}
			AND RP.memberId = #{loginedMemberId};
			""")
	int getSumReactionPoint(int memberId, String relTypeCode, int relId);

}