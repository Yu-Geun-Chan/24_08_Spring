package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	// INSERT INTO article SET regDate = NOW(), title = ?, `body` = ?;
	@Insert("INSERT INTO article SET regDate = NOW(), title = #{title}, `body` = #{body}")
	public void writeArticle(String title, String body);

	// UPDATE article SET regDate = NOW(), title = ?, `body` = ? WHERE id = ?;
	@Update("UPDATE article SET regDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(int id, String title, String body);

	// DELETE FROM article WHERE id = ?;
	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);

	// SELECT * FROM article WHERE id = ?;
	@Select("SELECT * FROM article WHERE id = #{id}")
	public Article getArticleById(int id);

	// SELECT * FROM article ORDER BY id DESC;
	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();


}
