package org.koreait.dao;

import org.koreait.Container;
import org.koreait.dto.Member;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.Map;

// DAO는 실제로 DB의 data에 접근하기 위한 객체입니다.
// 실제로 DB에 접근하여 data를 삽입, 삭제, 조회, 수정 등 CRUD 기능을 수행합니다.
// Service와 DB를 연결하는 고리 역할을 합니다.

public class MemberDao {

    private Connection conn = Container.getConnection();

    public boolean isLoginIdDuplicate(String userId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE `userId` = ?;", userId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public boolean loginCheckIdandPwd(String userId, String password) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE `userId` = ?", userId);
        sql.append("AND `password` = ?;", password);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public int insertMember(String userId, String password, String nickname) {
        // 데이터 삽입..
        SecSql sql = new SecSql();
        sql.append("INSERT INTO `member` (`regDate`, `userId`, `password`, `nickname`) VALUES");
        sql.append("(NOW(),");
        sql.append(" ?,", userId);
        sql.append(" ?,", password);
        sql.append(" ?)", nickname);

        int id = DBUtil.insert(conn, sql);

        return id;
    }

    public Member getMemberByLoginId(String userId) {

        SecSql sql = new SecSql();

        sql.append("SELECT * FROM `member` WHERE `userId` = ?", userId);

        Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);

        if(memberMap.isEmpty()){
            return null;
        }

        return new Member(memberMap);
    }
}
