package org.koreait.session;

import org.koreait.dto.Member;

public class Session {
    private static Member loginedMember;
    private static int loginedMemberID;

    public Session() {
        loginedMember = null;
        loginedMemberID = -1;
    }

    public static Member getLoginedMember() {
        return loginedMember;
    }

    public static void setLoginedMember(Member loginedMember) {
        Session.loginedMember = loginedMember;
    }

    public static int getLoginedMemberID() {
        return loginedMemberID;
    }

    public static void setLoginedMemberID(int loginedMemberID) {
        Session.loginedMemberID = loginedMemberID;
    }
}
