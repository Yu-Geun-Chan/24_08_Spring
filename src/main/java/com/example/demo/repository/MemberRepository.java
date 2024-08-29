package com.example.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

@Mapper
public interface MemberRepository {

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();

	@Insert("INSERT INTO `member` SET regDate = NOW(), updateDate = NOW(), loginId = #{loginId}, loginPw = #{loginPw}, `name` = #{name}, nickname = #{nickname}, cellphoneNum = #{cellphoneNum}, email = #{email}")
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("SELECT * FROM `member` WHERE id = #{id}")
	public Member getMemberById(int id);

	@Select("""
			SELECT *
			FROM `member`
			WHERE loginId = #{loginId}
			""")
	public Member getMemberByLoginId(String loginId);

	@Select("""
			SELECT *
			FROM `member`
			WHERE name = #{name}
			AND email = #{email}
			""")
	public Member getMemberByNameAndEmail(String name, String email);

	@Update("""
			<sciprt>
				UPDATE `member`
				<set>
					<if test="loginPw != null">
						loginPw = #{loginPw},
					</if>
					<if test="name != null">
						name = #{name},
					</if>
					<if test="nickname != null">
						nickname = #{nickname},
					</if>
					<if test="email != null">
						email = #{email},
					</if>
					<if test="cellphoneNum != null">
						cellphoneNum = #{cellphoneNum},
					</if>
					updateDate = NOW()
				</set>
			WHERE id = #{loginemMemberId}
			</sciprt>
			""")
	public void modifyMember(int loginemMemberId, String loginPw, String email, String cellphoneNum, String nickname,
			String name);

	@Update("""
			<sciprt>
				UPDATE `member`
				<set>
					<if test="name != null">
						name = #{name},
					</if>
					<if test="nickname != null">
						nickname = #{nickname},
					</if>
					<if test="email != null">
						email = #{email},
					</if>
					<if test="cellphoneNum != null">
						cellphoneNum = #{cellphoneNum},
					</if>
					updateDate = NOW()
				</set>
			WHERE id = #{loginemMemberId}
			</sciprt>
			""")
	public void modifyWithoutPwMember(int loginedMemberId, String email, String cellphoneNum, String nickname,
			String name);

}
