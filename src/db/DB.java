package db;

import model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String server = "localhost:3306"; // MySQL 서버 주소
    String database = "eyetol"; // MySQL DATABASE 이름
    String user_name = "root"; //  MySQL 서버 아이디
    String password = "oracle"; // MySQL 서버 비밀번호


    /* MySQL 연결정보 */
    String jdbc_driver = "com.mysql.cj.jdbc.Driver";

    // DB연결 메서드
    public void connect() {
        try {
            Class.forName(jdbc_driver);

            conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul", user_name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 수정된 주소록 내용 갱신을 위한 메서드
    public boolean updateDB(DAO DAO) {
        connect();

        String sql ="update bollardtest set name=?, latitude=?, longitude=? where id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, DAO.getName());
            pstmt.setDouble(2, DAO.getLatitude());
            pstmt.setDouble(3, DAO.getLongitude());
            pstmt.setInt(4, DAO.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            disconnect();
        }
        return true;
    }

    // 특정 주소록 게시글 삭제 메서드
    public boolean deleteDB(int gb_id) {
        connect();

        String sql ="delete from bollardtest where id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,gb_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            disconnect();
        }
        return true;
    }

    // 신규 주소록 메시지 추가 메서드
    public boolean insertDB(DAO DAO) {
        List<DAO> models = getDBList();

        connect();
//        System.out.println("client access to server");
        // sql 문자열 , gb_id 는 자동 등록 되므로 입력하지 않는다.

        String sql ="insert into bollardtest(name,latitude,longitude) values(?,?,?)";

        for(DAO model : models){
            if(DAO.getLatitude() == model.getLatitude() && DAO.getLongitude() == model.getLongitude()) {
                disconnect();
                return false;
            }
        }

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, DAO.getName());
            pstmt.setDouble(2, DAO.getLatitude());
            pstmt.setDouble(3, DAO.getLongitude());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            disconnect();
        }
        return true;
    }

    // 특정 주소록 게시글 가져오는 메서드
    public DAO getDB(int gb_id) {
        connect();

        String sql = "select * from bollardtest where id=?";
        DAO DAO = new DAO();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,gb_id);
            ResultSet rs = pstmt.executeQuery();

            // 데이터가 하나만 있으므로 rs.next()를 한번만 실행 한다.
            rs.next();
            DAO.setId(rs.getInt("id"));
            DAO.setName(rs.getString("name"));
            DAO.setLatitude(rs.getDouble("latitude"));
            DAO.setLongitude(rs.getDouble("longitude"));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return DAO;
    }

    // 전체 주소록 목록을 가져오는 메서드
    public ArrayList<DAO> getDBList() {
        connect();
        ArrayList<DAO> datas = new ArrayList<DAO>();

        String sql = "select * from bollardtest order by id desc";
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                DAO DAO = new DAO();
                DAO.setId(rs.getInt("id"));
                DAO.setName(rs.getString("name"));
                DAO.setLatitude(rs.getDouble("latitude"));
                DAO.setLongitude(rs.getDouble("longitude"));
                datas.add(DAO);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return datas;
    }
}