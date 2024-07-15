package org.koreait;

import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;
import org.koreait.dto.Member;

import java.sql.*;

public class App {

    String cmd = "";
    int sys_status = 0;

    String url = "jdbc:mariadb://localhost:3306/ArticleManager";
    String userName = "root";
    String password = "";
    Connection conn = null;

    boolean loginStatus = false;
    Member loginMember = null;

    public void run() {

        System.out.println("== ArticleManager 프로그램 실행 ==");

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            conn = DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Connected to database");
            }
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
//            throw new RuntimeException(e);
        }

        while (sys_status == 0) {
            System.out.print("명령어 > ");
            cmd = Container.getScanner().nextLine().trim();

            int artionResult = action(conn, cmd);

            if (artionResult == -1) {
                sys_status = -1;
                System.out.println("프로그램 종료");
                continue;
            }

        }

    }

    public int action(Connection conn, String cmd) {

        MemberController memberController = new MemberController(conn);
        ArticleController articleController = new ArticleController(conn);

        if (cmd.equals("exit")) {
            return -1;
        }

        ///////////////////////////////////////// member /////////////////////////////////
        if (cmd.equals("member join")) {
            if (!loginStatus && loginMember == null)
                memberController.doJoin();
            else {
                System.out.println("로그아웃한 상태여야 합니다.");
                return 0;
            }
        } else if (cmd.equals("member login")) {
            if (!loginStatus && loginMember == null) {
                loginMember = memberController.doLogin();
                if (loginMember != null) {
                    loginStatus = true;
//                    System.out.println(loginMember.getUserId());
//                    System.out.println(loginMember.getNickname());
                }

            } else {
                System.out.println("로그아웃한 상태여야 합니다.");
                return 0;
            }
        } else if (cmd.equals("member logout")) {
            if (loginStatus && loginMember != null) {
                memberController.doLogout();
                loginMember = null;
                loginStatus = false;
            } else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;

            }
        } else if (cmd.equals("member profile")) {
            if (loginStatus && loginMember != null) {
                memberController.showUserProfile(loginMember);
            } else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;
            }
        }
        ///////////////////////////////////////// article /////////////////////////////////
        else if (cmd.equals("article write")) {
            if (loginStatus && loginMember != null)
                articleController.doWrite();
            else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;
            }

        } else if (cmd.equals("article list")) {
            articleController.showAll();

        } else if (cmd.equals("article detail")) {
            articleController.showDetail();

        } else if (cmd.equals("article modify")) {
            if (loginStatus && loginMember != null)
                articleController.doModify();
            else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;
            }

        } else if (cmd.equals("article delete")) {
            if (loginStatus && loginMember != null)
                articleController.doDelete();
            else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;
            }

        } else {
            System.out.println("잘못된 명령어");
        }

        return 0;
    }

}





