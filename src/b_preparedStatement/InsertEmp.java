package b_preparedStatement;

import java.sql.*;

public class InsertEmp {

	public static void main(String[] args) {

		// 프로젝트 오른쪽 마우스 => Build Path => Configure Build Path => Libraries => Classpath => AddExternar => 오라클6 파일 추가
		
		try {	// 
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버로딩 성공");
			// 2. 연결객체 얻어오기
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			//------ 입력값 (사용자가 입력한 데이터를 해당 변수에 받아옴)
			String bonmyeng = "이선아";
			int welgup = 10000;
			String jikup = "IT";
			
			// 3. SQL 문장
			String sql = "INSERT INTO emp(empno , ename , sal, job) "
					+ " VALUES( 5555, ?,?,?)";
			
			// 4. SQL 전송객체
			PreparedStatement stmt = con.prepareStatement(sql);	//******************* 이미 sql 연결함
			stmt.setString(1, bonmyeng);
			stmt.setInt(2, welgup);
			stmt.setString(3, jikup);
			// 5. SQL 전송
			/*		- ResultSET executeQuery() : SELECT
			 * 		- int형 (입력/삭제/수정 값을 리턴하기 때문에 int형) executeUpdate() : INSERT / DELETE / UPDATE
			 */
			int result = stmt.executeUpdate();
			System.out.println(result + "행을 실행");
			
			// 6. 닫기 (순서 필수) : 
			stmt.close();	
			con.close();	// 본체
			
		} catch (Exception e) {
			System.out.println("DB 실패 : " + e);
		}
		
	}

}
