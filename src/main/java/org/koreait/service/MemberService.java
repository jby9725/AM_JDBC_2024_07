package org.koreait.service;

import org.koreait.dao.MemberDao;
import org.koreait.dto.Member;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = new MemberDao();
    }

    public boolean isLoginIdDuplicate(String loginId) {
        return memberDao.isLoginIdDuplicate(loginId);
    }

    public boolean loginCheck(String userId, String password) {
        return memberDao.loginCheckIdandPwd(userId, password);
    }

    public int memberJoin(String userId, String password, String nickname) {
        return memberDao.insertMember(userId, password, nickname);
    }

    public Member getMemberByLoginId(String userId) {
        return memberDao.getMemberByLoginId(userId);
    }
}
