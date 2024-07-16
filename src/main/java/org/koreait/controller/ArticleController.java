package org.koreait.controller;

import org.koreait.Container;
import org.koreait.dto.Article;
import org.koreait.service.ArticleService;

import java.util.List;

public class ArticleController {

    private ArticleService articleService;

    public ArticleController() {
        this.articleService = new ArticleService();
    }

    public void doWrite() {

        System.out.print("제목 : ");
        String title = Container.getScanner().nextLine().trim();
        System.out.print("내용 : ");
        String body = Container.getScanner().nextLine().trim();

        int id = articleService.articleWrite(title, body);

        System.out.println(id + "번 게시글이 생성되었습니다.");

    }

    public void showAll() {

        // 전체 글 가져오기
        List<Article> articleList = articleService.articleListShowAll();

        if (articleList.size() == 0) {
            System.out.println("게시글 없음");
            return;
        }

        System.out.println(" 번호 /    제목    /   작성자   /   내용   /        작성 날짜        /        수정 날짜        / ");
        for (Article article : articleList) {
            System.out.printf(" %3d /%8s /%10s /%10s / %21s / %21s    \n", article.getId(), article.getTitle(), article.getM_author(), article.getBody(), article.getRegDate(), article.getUpdateDate());
        }
    }


    public void showPages(String cmd) {

        String[] cmdBits = cmd.split(" ");

        int page = 1;
        String searchKeyword = null;


        // 검색어
        if (cmdBits.length >= 3) {
            searchKeyword = cmdBits[2];
        }

        // 몇 페이지?
        if (cmdBits.length >= 4) {
            page = Integer.parseInt(cmdBits[3]);
        }

        // 한 페이지에 10개 씩
        int itemsInAPage = 10;

        List<Article> articles = articleService.getForPrintArticles(page, itemsInAPage, searchKeyword);

        if (articles.size() == 0) {
            System.out.println("게시글이 없습니다");
            return;
        }
        System.out.println(" 번호 /    제목    /   작성자   /   내용   /        작성 날짜        /        수정 날짜        / ");
        for (Article article : articles) {
            System.out.printf(" %3d /%8s /%10s /%10s / %21s / %21s    \n", article.getId(), article.getTitle(), article.getM_author(), article.getBody(), article.getRegDate(), article.getUpdateDate());
        }

    }

    public void showDetail() {
        System.out.print("자세히 볼 게시물의 id : ");
        int id = Container.getScanner().nextInt();
        Container.getScanner().nextLine();

        Article article = articleService.getArticleById(id);

        System.out.println("== 검색 결과 ==");
        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("작성자 : " + article.getM_author());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doModify() {
        System.out.print("수정할 게시물의 id : ");
        int id = Container.getScanner().nextInt();
        Container.getScanner().nextLine();

        Article foundArticle = articleService.getArticleById(id);

        if (foundArticle == null) {
            System.out.println(id + "번 글은 없습니다.");

            return;
        } else {

            // 권한 체크 하고 틀리면 return;
            if(foundArticle.getAuthor() != Container.getSession().getLoginedMemberID()) {
                System.out.println("권한이 없습니다.");
                return;
            }
            System.out.print("게시물의 새 제목 : ");
            String title = Container.getScanner().nextLine().trim();
            System.out.print("게시물의 새 내용 : ");
            String body = Container.getScanner().nextLine().trim();

            int id2 = articleService.articleModify(id, title, body);

            System.out.println(id2 + "번 글이 수정되었습니다.");

        }
    }

    public void doDelete() {
        System.out.print("삭제할 게시물의 id : ");
        int id = Container.getScanner().nextInt();
        Container.getScanner().nextLine();

        Article foundArticle = articleService.getArticleById(id);

        if (foundArticle == null) {
            System.out.println(id + "번 글은 없습니다.");

            return;
        } else {

            // 권한 체크 하고 틀리면 return;
            if(foundArticle.getAuthor() != Container.getSession().getLoginedMemberID()) {
                System.out.println("권한이 없습니다.");
                return;
            }

            int id2 = articleService.articleDelete(id);

            System.out.println(id2 + "번 글이 삭제되었습니다.");

        }
    }

}
