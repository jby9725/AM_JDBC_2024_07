package org.koreait.service;

import org.koreait.dao.ArticleDao;
import org.koreait.dto.Article;

import java.sql.Connection;
import java.util.List;

public class ArticleService {

    private ArticleDao articleDao;

    public ArticleService() {
        this.articleDao = new ArticleDao();
    }

    public boolean articleDuplicate(int id) {
        return articleDao.isArticleDuplicate(id);
    }

    public int articleWrite(String title, String body) {
        return articleDao.insertArticle(title, body);
    }

    public List<Article> articleListShowAll() {
        return articleDao.selectAllArticle();
    }

    public Article getArticleById(int id) {
        return articleDao.selectArticle(id);
    }

    public int articleModify(int id, String title, String body) {
        return articleDao.updateArticle(id, title, body);
    }

    public int articleDelete(int id) {
        return articleDao.deleteArticle(id);

    }
}
