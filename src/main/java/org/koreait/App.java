package org.koreait;

import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;
import org.koreait.session.Session;

public class App {

    String cmd = "";
    int sys_status = 0;

    public void run() {

        System.out.println("== ArticleManager 프로그램 실행 ==");

        while (sys_status == 0) {
            System.out.print("명령어 > ");
            cmd = Container.getScanner().nextLine().trim();

            int artionResult = action(cmd);

            if (artionResult == -1) {
                sys_status = -1;
                System.out.println("프로그램 종료");
//                continue;
            }
        }
    }

    public int action(String cmd) {

        MemberController memberController = new MemberController();
        ArticleController articleController = new ArticleController();

        if (cmd.equals("exit")) {
            return -1;
        }

        ///////////////////////////////////////// member /////////////////////////////////
        if (cmd.equals("member join")) {
            if (!Container.getSession().isLogined())
//            if (!loginStatus && loginMember == null)
                memberController.doJoin();
            else {
                System.out.println("로그아웃한 상태여야 합니다.");
                return 0;
            }
        } else if (cmd.equals("member login")) {
            if (!Container.getSession().isLogined()) {
                Container.getSession().setLoginedMember(memberController.doLogin()); // loginMember = memberController.doLogin();
                Container.getSession().setLoginedMemberID(Container.getSession().getLoginedMember().getId());
//                System.out.println(Session.getLoginedMember().getUserId());
//                System.out.println(Session.getLoginedMember().getNickname());
            } else {
                System.out.println("로그아웃한 상태여야 합니다.");
                return 0;
            }
        } else if (cmd.equals("member logout")) {
            if (Container.getSession().isLogined()) {
                memberController.doLogout();
            } else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;

            }
        } else if (cmd.equals("member profile")) {
            if (Container.getSession().isLogined()) {
                memberController.showUserProfile(Container.getSession().getLoginedMember());
            } else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;
            }
        }
        ///////////////////////////////////////// article /////////////////////////////////
        else if (cmd.equals("article write")) {
            if (Container.getSession().isLogined())
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
            if (Container.getSession().isLogined())
                articleController.doModify();
            else {
                System.out.println("로그인한 상태여야 합니다.");
                return 0;
            }

        } else if (cmd.equals("article delete")) {
            if (Container.getSession().isLogined())
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





