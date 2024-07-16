package org.koreait.session;

import org.koreait.dto.Member;

public class Session {
    private Member loginedMember;
    private int loginedMemberID;

    public Session() {
        loginedMember = null;
        loginedMemberID = -1;
    }

    public Member getLoginedMember() {
        return loginedMember;
    }

    public void setLoginedMember(Member loginedMember) {
        this.loginedMember = loginedMember;
    }

    public int getLoginedMemberID() {
        return loginedMemberID;
    }

    public void setLoginedMemberID(int loginedMemberID) {
        this.loginedMemberID = loginedMemberID;
    }

    public void login() {

    }

    public void logout() {
        this.setLoginedMember(null);
        this.setLoginedMemberID(-1);
    }

    public boolean isLogined(){
//        System.out.println(getLoginedMemberID());
//        System.out.println(getLoginedMember());
        if (getLoginedMemberID() == -1 && getLoginedMember() == null) {
            return false;
        }
        else {
            return true;
        }
    }
}
