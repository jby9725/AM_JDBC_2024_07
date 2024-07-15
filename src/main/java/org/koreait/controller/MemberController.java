package org.koreait.controller;

import org.koreait.Container;
import org.koreait.dto.Member;
import org.koreait.service.MemberService;

import java.sql.Connection;

public class MemberController {

    private Connection conn;

    private MemberService memberService;

    private Member loginedMember = null;

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

    public Member doLogin() {
        String userId = null;
        String password = null;

        int tryMaxCount = 5;
        int tryCount = 0;

        boolean loginPwdCheck = false;

        Member rsMember = null;
        Member checkMember = null;

        while (tryCount < tryMaxCount) {
            System.out.println("남은 시도 횟수 : " + (tryMaxCount - tryCount - 1));
            System.out.print("로그인 아이디 : ");
            userId = Container.getScanner().nextLine();

            if (userId.length() == 0 || userId.contains(" ")) {
                tryCount++;
                System.out.println("아이디 재입력 필요");
                continue;
            }

            // 아이디 있는지 없는지.
            boolean this_id_in_db = memberService.isLoginIdDuplicate(conn, userId);

            if (!this_id_in_db) {
                loginPwdCheck = false;
                System.out.println("아이디가 데이터베이스 내에 없습니다. 다시 입력해주세요. 남은 시도 횟수 : " + (tryMaxCount - tryCount - 1));
                tryCount++;
                continue;

            } else {

                checkMember = memberService.getMemberByLoginId(conn, userId);

                while (tryCount < tryMaxCount) {
                    System.out.println("남은 시도 횟수 : " + (tryMaxCount - tryCount - 1));

                    System.out.print("비밀번호 : ");
                    password = Container.getScanner().nextLine().trim();
                    if (password.length() == 0 || password.contains(" ")) {
                        System.out.println("비밀번호를 다시 입력해주세요.");
                        tryCount++;
                        continue;
                    }

                    if (!password.equals(checkMember.getPassword())) {
                        loginPwdCheck = false;
                        rsMember = null;
                        tryCount++;
                        continue;
                    } else {
                        loginPwdCheck = true;
                        rsMember = checkMember;
                        break;
                    }
                }

                if (tryCount >= tryMaxCount) {
                    System.out.println("로그인 실패");
                    return null;
                }

                // loginPwdCheck = memberService.loginCheck(conn, userId, password);
//                System.out.println("loginPwdCheck : " + loginPwdCheck);
            }

            if (rsMember != null) {
                loginedMember = rsMember;
                break;
            } else {
                loginedMember = null;
                System.out.println("일치하는 정보가 데이터베이스 내에 없습니다. 남은 시도 횟수 : " + (tryMaxCount - tryCount - 1));
            }
            tryCount++;
        }

        if (loginPwdCheck) {
            System.out.println("로그인 성공");
            System.out.println(rsMember.getNickname() + "님 환영합니다.");
            return rsMember;
        } else {
            System.out.println("로그인 실패");
            return null;
        }

    }

    public void doLogout() {
        this.loginedMember = null;
        System.out.println("로그아웃 되었습니다.");
    }

    public void showUserProfile(Member loginMember) {
        Member member = loginMember;
        if (member == null) {
            System.out.println("로그인하지 않았습니다. 프로필을 표시할 수 없습니다.");
            return;
        }
        System.out.println("== 회원 정보 ==");
        System.out.println("아이디 : " + member.getUserId());
        System.out.println("비밀번호 : " + member.getPassword());
        System.out.println("닉네임 : " + member.getNickname());
        System.out.println("가입 날짜 : " + member.getRegDate());
    }
}
