package org.koreait.service;

import org.koreait.dao.MemberDao;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = new MemberDao();
    }

    public boolean isLoginIdDuplicate(Connection conn, String loginId) {
        return memberDao.isLoginIdDuplicate(conn, loginId);
    }

    public boolean loginCheck(Connection conn, String userId, String password) {
        return memberDao.loginCheckIdandPwd(conn, userId, password);
    }

    public int memberJoin(Connection conn, String userId, String password, String nickname) {
        return memberDao.insertMember(conn, userId, password, nickname);
    }

}
