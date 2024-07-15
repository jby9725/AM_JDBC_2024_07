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

    public boolean articleDuplicate(Connection conn, int id) {
        return articleDao.isArticleDuplicate(conn, id);
    }

    public int articleWrite(Connection conn, String title, String body) {
        return articleDao.insertArticle(conn, title, body);
    }

    public List<Article> articleListShowAll(Connection conn) {
        return articleDao.selectAllArticle(conn);
    }

    public Article getArticleById(Connection conn, int id) {
        return articleDao.selectArticle(conn, id);
    }

    public int articleModify(Connection conn, int id, String title, String body) {
        return articleDao.updateArticle(conn, id, title, body);
    }

    public int articleDelete(Connection conn, int id) {
        return articleDao.deleteArticle(conn, id);

    }
}
