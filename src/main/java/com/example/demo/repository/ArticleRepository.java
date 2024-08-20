package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Article;

@Mapper
public interface ArticleRepository {

//	@Insert("INSERT INTO article SET regDate = NOW() updateDate = NOW(), m,title = #{title}, `body` = #{body}")
	public void writeArticle(int memberId, String title, String body, String boardId);

//	@Update("UPDATE article SET regDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(int id, String title, String body);

	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);

//	@Select("SELECT * FROM article WHERE id = #{id}")
	public Article getArticleById(int id);

//	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();

	List<Article> getForPrintArticles(@Param("boardId") int boardId, @Param("itemsInApage") int itemsInApage,
			@Param("limitFrom") int limitFrom, @Param("searchWord") String searchWord);

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();

	public int getArticlesCount(int boardId);

}
