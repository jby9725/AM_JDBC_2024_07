package org.koreait;

import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {

    String cmd = "";
    int sys_status = 0;

    String url = "jdbc:mariadb://localhost:3306/ArticleManager";
    String userName = "root";
    String password = "";
    Connection conn = null;
    PreparedStatement pstmt = null;

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
            memberController.doJoin();
        }
        ///////////////////////////////////////// article /////////////////////////////////
        else if (cmd.equals("article write")) {
            articleController.doWrite();

        } else if (cmd.equals("article list")) {
            articleController.doShowAll();

        } else if (cmd.equals("article detail")) {
            articleController.doShowDetail();

        } else if (cmd.equals("article modify")) {
            articleController.doModify();

        } else if (cmd.equals("article delete")) {
            articleController.doDelete();

        } else {
            System.out.println("잘못된 명령어");
        }

        return 0;
    }

}





