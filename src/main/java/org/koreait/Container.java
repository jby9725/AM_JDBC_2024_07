package org.koreait;

import org.koreait.session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Container {
    // 스캐너
    private static Scanner scanner;
    // 데이터베이스 변수.
    private static Connection connection;
    // 세션
    private static Session session;

    // 데이터베이스 접속에 필요한 변수들
    private static String url = "jdbc:mariadb://localhost:3306/ArticleManager";
    private static String userName = "root";
    private static String password = "";

    public static void init() {
        // 공유 자원을 모아두는 공간 초기화
        scanner = new Scanner(System.in);
        // 세션 만들어두기
        session = new Session();
        // DB 접속
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(url, userName, password);
            if (connection != null) {
                System.out.println("Connected to database");
            }
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
//            throw new RuntimeException(e);
        }

        // 할려면. 이 안에 클래스 초기화시키기. 근데 dao 부터 순서대로. dao - service - controller 순으로.
    }

    // 공유 자원을 모아두는 공간 자원 해제
    public static void close() {
        scanner.close();
        // DB 접속 해제

    }

    public static Scanner getScanner() {
        return scanner;
    }


    //////////
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        Container.connection = connection;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        Container.session = session;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Container.url = url;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Container.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Container.password = password;
    }
}