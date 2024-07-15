package org.koreait.controller;

import org.koreait.Container;
import org.koreait.dto.Article;
import org.koreait.service.ArticleService;

import java.sql.Connection;
import java.util.List;

public class ArticleController {

    private Connection conn;
    private ArticleService articleService;

    public ArticleController(Connection conn) {
        this.conn = conn;
        this.articleService = new ArticleService();
    }

    public void doWrite() {

        System.out.print("제목 : ");
        String title = Container.getScanner().nextLine().trim();
        System.out.print("내용 : ");
        String body = Container.getScanner().nextLine().trim();

        int id = articleService.articleWrite(conn, title, body);

        System.out.println(id + "번 게시글이 생성되었습니다.");

    }

    public void doShowAll() {

        List<Article> articleList = articleService.articleListShowAll(conn);

        if (articleList.size() == 0) {
            System.out.println("게시글 없음");
            return;
        }

        System.out.println(" 번호 /    제목    /     내용 /        작성 날짜        /        수정 날짜        / ");
        for (Article article : articleList) {
            System.out.printf(" %3d /%8s /%10s / %21s / %21s    \n", article.getId(), article.getTitle(), article.getBody(), article.getRegDate(), article.getUpdateDate());
        }
    }

    public void doShowDetail() {
        System.out.print("자세히 볼 게시물의 id : ");
        int id = Container.getScanner().nextInt();
        Container.getScanner().nextLine();

        Article article = articleService.getArticleById(conn, id);

        System.out.println("== 검색 결과 ==");
        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doModify() {
        System.out.print("수정할 게시물의 id : ");
        int id = Container.getScanner().nextInt();
        Container.getScanner().nextLine();

        boolean isFoundArticle = articleService.articleDuplicate(conn, id);

        if (!isFoundArticle) {
            System.out.println(id + "번 글은 없습니다.");

            return;
        } else {
            System.out.print("게시물의 새 제목 : ");
            String title = Container.getScanner().nextLine().trim();
            System.out.print("게시물의 새 내용 : ");
            String body = Container.getScanner().nextLine().trim();

            int id2 = articleService.articleModify(conn, id, title, body);

            System.out.println(id2 + "번 글이 수정되었습니다.");

        }
    }

    public void doDelete() {
        System.out.print("삭제할 게시물의 id : ");
        int id = Container.getScanner().nextInt();
        Container.getScanner().nextLine();

        boolean isFoundArticle = articleService.articleDuplicate(conn, id);

        if (!isFoundArticle) {
            System.out.println(id + "번 글은 없습니다.");

            return;
        } else {

            int id2 = articleService.articleDelete(conn, id);

            System.out.println(id2 + "번 글이 삭제되었습니다.");

        }
    }
}
