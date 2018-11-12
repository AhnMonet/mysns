package mysns.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mysns.util.DBManager;

public class MemberDAO {
	Logger logger = LoggerFactory.getLogger(MemberDAO.class);
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	BCrypt bc = new BCrypt();
	
	public boolean addMember(Member member) {
		conn = DBManager.getConnection();
		String sql = "insert into s_member(uid, name, passwd, email, date) values(?, ?, ?, ?, now());";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUid());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, bc.hashpw(member.getPasswd(), bc.gensalt(10)));
			pstmt.setString(4, member.getEmail());
			pstmt.executeUpdate();
		} catch(SQLException se) {
			se.printStackTrace();
			logger.info("Error Code : {}", se.getErrorCode());
			return false;
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return true;
	}
	
	public Member changPassword(String uid, String passwd) {
		conn = DBManager.getConnection();
		Member member = new Member();
		String sql = "update s_member set passwd=? where uid=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bc.hashpw(member.getPasswd(), bc.gensalt(10)));
			pstmt.setString(2, uid);
			pstmt.executeUpdate();
		} catch(SQLException se) {
			se.printStackTrace();
			logger.info("Error Code : {}", se.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return member;
	}
	
	public boolean login(String uid, String passwd) {
		conn = DBManager.getConnection();
		String sql = "select uid, passwd from s_member where uid=?;";
		boolean result = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			rs.next();
			if(bc.checkpw(passwd, rs.getString("passwd"))) {
				result = true;
			}
			rs.close();
		} catch(SQLException se) {
			se.printStackTrace();
			logger.info("Error Code : {}", se.getErrorCode());
			return false;
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<String> getNewMembers(){
		ArrayList<String> newmemlist = new ArrayList<String>();
		conn = DBManager.getConnection();
		String sql = "select * from s_member order by date desc limit 7;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				newmemlist.add(rs.getString(1));
			}
			rs.close();
		} catch(SQLException se) {
			se.printStackTrace();
			logger.info("Error Code : {}", se.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return newmemlist;
	}
	
	public Member getMember(String uid) {
		Member member = new Member();
		conn = DBManager.getConnection();
		String sql = "select * from s_member where uid=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			member.setUid(rs.getString(1));
			member.setName(rs.getString(2));
			member.setPasswd(rs.getString(3));
			member.setEmail(rs.getString(4));
			member.setDate(rs.getDate(5));
			
			rs.close();
		} catch(SQLException se) {
			se.printStackTrace();
			logger.info("Error Code : {}", se.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return member;
	}
}
