# ProjectA. 명함 관리 프로그램

https://www.boostcourse.org/web326/project/205/content/164#summary

## Environment
- java 8
- maven
- mariaDB

## TODO
1. MariaDB에 데이터베이스 사용자와 데이터베이스를 생성합니다.
2. 데이터베이스에 명함정보를 저장할 수 있는 테이블을 생성합니다.
3. 주어진 프로젝트의 library dependencies를 maven으로 import합니다.
4. 해당 프로젝트의 BusinessCardManagerDao를 알맞게 수정하여 기획서의 내용과 같이 동작하도록 합니다.

### Code Details
#### 명함 정보 추가

```java
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
```

#### 명함 정보 검색

```java
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
```