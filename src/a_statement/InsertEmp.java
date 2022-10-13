package a_statement;

import java.sql.*;

public class InsertEmp {

	public static void main(String[] args) {

		// 프로젝트 오른쪽 마우스 => Build Path => Configure Build Path => Libraries => Classpath => AddExternar => 오라클6 파일 추가
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버로딩 성공");
			// 2. 연결객체 얻어오기
			String ur1 = "jdbc:oracle:thin:@192.168.0.12:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			// 3. SQL 문장
			//		10번 부서의 사원의 월급을 10% 인상
			
			//		월급이 10000 이상인 사원들 삭제
			String sql = "INSERT INTO emp(ename, empno ,sal , job) VALUES('이선아' , 1254 , 10000 , 'sales')";	// 한줄 내려서 작성 할 경우 공백 필수 
			// 4. SQL 전송객체
			Statement stmt = con.createStatement();
			
			// 5. SQL 전송
			/*		- ResultSET executeQuery() : SELECT
			 * 		- int형 (입력/삭제/수정 값을 리턴하기 때문에 int형) executeUpdate() : INSERT / DELETE / UPDATE
			 */
			int result = stmt.executeUpdate(sql);
			System.out.println(result + "행을 실행");
			
			// 6. 닫기 (순서 필수) : 
			stmt.close();	
			con.close();	// 본체
			
		} catch (Exception e) {
			System.out.println("DB 실패 : " + e);
		}
		
	}

}
