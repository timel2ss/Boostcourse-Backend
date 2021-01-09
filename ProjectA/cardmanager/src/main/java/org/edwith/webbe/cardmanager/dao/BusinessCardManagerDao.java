package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BusinessCardManagerDao {

    private Connection getConnection() {
        String resource = "config.properties";
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(resource);
            properties.load(fileInputStream);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BusinessCard> searchBusinessCard(String keyword){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<BusinessCard> cards = new ArrayList<>();
        try {
            conn = getConnection();
            String SQL = "select * from BusinessCard where name like concat('%', ?, '%')";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String name = rs.getString(1);
                String phone = rs.getString(2);
                String companyName = rs.getString(3);
                Date createDate = rs.getDate(4);

                BusinessCard businessCard = new BusinessCard(name, phone, companyName);
                businessCard.setCreateDate(createDate);
                cards.add(businessCard);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return cards;
    }

    public BusinessCard addBusinessCard(BusinessCard businessCard){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String SQL = "insert into BusinessCard values(?, ?, ?, ?)";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, businessCard.getName() );
            pstmt.setString(2, businessCard.getPhone() );
            pstmt.setString(3, businessCard.getCompanyName() );
            pstmt.setDate(4, new java.sql.Date(businessCard.getCreateDate().getTime()));
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return businessCard;
    }
}
