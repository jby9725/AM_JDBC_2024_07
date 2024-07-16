package org.koreait.service;

import org.koreait.dao.ArticleDao;
import org.koreait.dto.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Article> getForPrintArticles(int page, int itemsInAPage, String searchKeyword) {

        int start = (page - 1) * itemsInAPage;
        int count = itemsInAPage;

        Map<String, Object> args = new HashMap<>();
        args.put("searchKeyword", searchKeyword);
        args.put("count", count);
        args.put("start", start);

        return articleDao.getForPrintArticles(args);
    }


}
