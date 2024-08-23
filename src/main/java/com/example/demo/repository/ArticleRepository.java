package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Mapper
public interface ArticleRepository {

//	@Insert("INSERT INTO article SET regDate = NOW() updateDate = NOW(), m,title = #{title}, `body` = #{body}")
	public void writeArticle(int memberId, String title, String body, String boardId);

//	@Update("UPDATE article SET regDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(int id, String title, String body);

	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);

	@Select("""
			SELECT A.*, M.nickname AS extra__writer,
			IFNULL(SUM(RP.point), 0) AS extra__sumReactionPoint,
			IFNULL(SUM(IF(RP.point > 0, RP.point, 0)), 0) AS extra__goodReactionPoint,
			IFNULL(SUM(IF(RP.point < 0, RP.point, 0)), 0) AS extra__badReactionPoint
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			LEFT JOIN reactionPoint AS RP
			ON A.id = RP.relId AND RP.relTypeCode = 'article'
			WHERE A.id = #{id}
				""")
	public Article getArticleById(int id);

//	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();

	List<Article> getForPrintArticles(@Param("boardId") int boardId, @Param("itemsInApage") int itemsInApage,
			@Param("limitFrom") int limitFrom, @Param("searchKeywordTypeCode") String searchKeywordTypeCode,
			@Param("searchKeyword") String searchKeyword);

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	public int increaseHitCount(int id);

	public Object getArticleHitCount(int id);

}
