package org.koreait.dao;

// DAO는 실제로 DB의 data에 접근하기 위한 객체입니다.
// 실제로 DB에 접근하여 data를 삽입, 삭제, 조회, 수정 등 CRUD 기능을 수행합니다.
// Service와 DB를 연결하는 고리 역할을 합니다.

import org.koreait.Container;
import org.koreait.dto.Article;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {

    private Connection conn = Container.getConnection();

    public int insertArticle(String title, String body) {
        // 데이터 삽입..
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("author = ?,", Container.getSession().getLoginedMemberID());
        sql.append("title = ?,", title);
        sql.append("`body` = ?", body);

        int id = DBUtil.insert(conn, sql);
        System.out.println(id + "번 글이 생성되었습니다.");

        return id;
    }

    public List<Article> selectAllArticle() {
        // 데이터 조회...
        List<Article> articles = new ArrayList<>();

        SecSql sql = new SecSql();
//        sql.append("SELECT *");
//        sql.append("FROM article");
//        sql.append("ORDER BY id DESC");

        sql.append("SELECT A.*, M.nickname");
        sql.append("FROM article A");
        sql.append("INNER JOIN `member` M");
        sql.append("ON A.author = M.id");
        sql.append("ORDER BY A.id DESC");


        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        return articles;
    }

    public Article selectArticle(int id) {
        // 조회...
        SecSql sql = new SecSql();
//        sql.append("SELECT *");
//        sql.append("FROM article");
//        sql.append("WHERE id = ?", id);

        sql.append("SELECT A.*, M.nickname");
        sql.append("FROM article A");
        sql.append("INNER JOIN `member` M");
        sql.append("ON A.author = M.id");
        sql.append("WHERE A.id = ?", id);

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없습니다.");
            return null;
        }

        return new Article(articleMap);
    }

    public boolean isArticleDuplicate(int id) {
        // 조회...
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `article`");
        sql.append("WHERE id = ?;", id);

        return DBUtil.selectRowBooleanValue(conn, sql);

    }

    public int updateArticle(int id, String title, String body) {
        // 수정...
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        if (title.length() > 0)
            sql.append(",title = ?", title);
        if (body.length() > 0)
            sql.append(",`body` = ?", body);
        sql.append("WHERE id = ?", id);

        DBUtil.update(conn, sql);

        return id;
    }

    public int deleteArticle(int id) {
        // 삭제...
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);

        DBUtil.delete(conn, sql);

        return id;
    }

    public List<Article> getForPrintArticles(Map<String, Object> args) {
        SecSql sql = new SecSql();

        String searchKeyword = null;

        if (args.containsKey("searchKeyword")) {
            searchKeyword = (String) args.get("searchKeyword");
        }

        int start = -1;
        int count = -1;

        if (args.containsKey("start")) {
            start = (int) args.get("start");
        }
        if (args.containsKey("count")) {
            count = (int) args.get("count");
        }

        sql.append("SELECT A.*, M.nickname");
        sql.append("FROM article A");
        sql.append("INNER JOIN `member` M");
        sql.append("ON A.author = M.id");
        if (searchKeyword.length() > 0) {
            sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
        }
        sql.append("ORDER BY id DESC");
        if (start != -1) {
            sql.append("LIMIT ?, ?;", start, count); // limitFrom, limitTake);
        }

//        System.out.println(sql);

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.getConnection(), sql);

        List<Article> articles = new ArrayList<>();

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        return articles;
    }
}
