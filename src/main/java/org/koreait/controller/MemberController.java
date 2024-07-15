package org.koreait.controller;

import org.koreait.Container;
import org.koreait.service.MemberService;

import java.sql.Connection;

public class MemberController {

    private Connection conn;

    private MemberService memberService;

    private String loginedId = "";

    public MemberController(Connection conn) {
        this.conn = conn;
        this.memberService = new MemberService();
    }

    public void doJoin() {

        System.out.println("== 회원가입 ==");

        String userId = null;
        String password = null;
        String pwdConfirm = null;
        String nickname = null;

        while (true) {
            System.out.print("사용자 아이디 : ");
            userId = Container.getScanner().nextLine().trim();

            if (userId.length() == 0 || userId.contains(" ")) {
                System.out.println("아이디 재입력 필요");
                continue;
            }

            // 아이디가 중복인지 체크....
            boolean is_id_not_unique = memberService.isLoginIdDuplicate(conn, userId);

            if (!is_id_not_unique) {
                break;

            } else {
                System.out.println("해당 아이디가 이미 있습니다.");
                System.out.println("아이디를 다시 입력 받습니다.");
                continue;
            }
        }

        while (true) {
            System.out.print("사용자 비밀번호 : ");
            password = Container.getScanner().nextLine().trim();

            if (password.length() == 0 || password.contains(" ")) {
                System.out.println("비밀번호 재입력 필요");
                continue;
            }

            boolean loginPwdCheck = true;

            while (true) {
                System.out.print("사용자 비밀번호 확인 : ");
                pwdConfirm = Container.getScanner().nextLine().trim();
                if (pwdConfirm.length() == 0 || pwdConfirm.contains(" ")) {
                    System.out.println("확인 비밀번호 재입력 필요");
                    continue;
                }

                if (!pwdConfirm.equals(password)) {
                    loginPwdCheck = false;
                }
                break;
            }
            if (loginPwdCheck) {
                break;
            }

        }

        while (true) {
            System.out.print("사용자 닉네임 : ");
            nickname = Container.getScanner().nextLine().trim();
            if (nickname.length() == 0 || nickname.contains(" ")) {
                System.out.println("닉네임 재입력 필요");
                continue;
            }
            break;
        }

        int id = memberService.memberJoin(conn, userId, password, nickname);

        System.out.println(id + "번 멤버가 생성되었습니다.");
    }

    public boolean doLogin() {
        String userId = null;
        String password = null;

        int tryCount = 0;
        boolean loginPwdCheck = false;

        while (tryCount < 3) {
            System.out.print("로그인 아이디 : ");
            userId = Container.getScanner().nextLine();

            if (userId.length() == 0 || userId.contains(" ")) {
                System.out.println("아이디 재입력 필요");
                continue;
            }


            boolean this_id_in_db = memberService.isLoginIdDuplicate(conn, userId);

            if (!this_id_in_db) {
                loginPwdCheck = false;
                System.out.println("아이디가 데이터베이스 내에 없습니다. 다시 입력해주세요. 남은 시도 횟수 : " + (2 - tryCount));
                tryCount++;
                continue;

            } else {
                while (true) {
                    System.out.print("비밀번호 : ");
                    password = Container.getScanner().nextLine().trim();
                    if (password.length() == 0 || password.contains(" ")) {
                        System.out.println("비밀번호를 다시 입력해주세요.");
                        continue;
                    }
                    break;
                }

                loginPwdCheck = memberService.loginCheck(conn, userId, password);
//                System.out.println("loginPwdCheck : " + loginPwdCheck);
            }

            if (loginPwdCheck) {
                this.loginedId = userId;
                break;
            } else {
                this.loginedId = null;
                System.out.println("일치하는 정보가 데이터베이스 내에 없습니다. 남은 시도 횟수 : " + (2 - tryCount));
            }
            tryCount++;
        }

        if (loginPwdCheck) {
            System.out.println("로그인 성공");
            return true;
        } else {
            System.out.println("로그인 실패");
            return false;
        }

    }

    public void doLogout() {
        this.loginedId = null;
        System.out.println("로그아웃 되었습니다.");
    }
}
