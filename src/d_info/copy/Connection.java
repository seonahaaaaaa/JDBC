package d_info.copy;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection {

   public static void main(String[] args) {
      // 지역변수 초기화
      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      String DRIVER = "oracle.jdbc.driver.OracleDriver";
      String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
      String USER = "scott";
      String PASS = "tiger";

      
      // 사용자의 입력값이 존재하지 않고 테이블의 쿼리만 사용하는거면 statement
      // 조건이 붙으면 preparedstatement
      try {
         Class.forName(DRIVER);
         System.out.println("클래스 로딩 성공!");

         String searchname = "임꺽정";
         String sql = "select \"name\", \"num\", \"birth\" from \"INFO\" where \"name\" = ?";
         
         con = DriverManager.getConnection(URL, USER, PASS);
         System.out.println("sql");
         // DriverManager 클래스 : 드라이버 검색할때 사용
         // Connection con : 접속 정보를 가지고 있음
         stmt = con.prepareStatement(sql); // prepareStatement : 커넥션을 통해서 쿼리문을 전송하기 위해 sql 문장을 준비
         stmt.setString(1, searchname);
         
         rs = stmt.executeQuery(); // PrepareStatement 클래스를 통해 이미 준비 된 문장을 가지고 있기 때문에 쿼리를 줄 필요가 없음.
         if (rs.next()) {
            System.out.println(rs.getLong(1)); // 오라클 테이블의 첫번째 컬럼의 데이터를 getLong 메소드를 사용하여 꺼냄
            System.out.println(rs.getString("name")); // 두번째 컬럼의 데이터
            System.out.println(rs.getDate(3)); // 세번째 컬럼의 데이터
            System.out.println(rs.getString("phone")); // 네번째 컬럼의 데이터
         }
      } catch (ClassNotFoundException e) { // DRIVER 해당 클래스를 찾을 수 없을때 발생하는 예외
         e.printStackTrace();
      } catch (SQLException e) { // SQLException e : 오라클에 쿼리를 전송했는데 오라클쪽에서 에러가 발생하면 해당 에러를 SQLException 으로 반환하게
                           // 됨.
         e.printStackTrace(); // 어떤 오류가 났는지 확인하는 구문
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }

         }
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (con != null) {
            try {
               con.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }	

      }

   }

}
