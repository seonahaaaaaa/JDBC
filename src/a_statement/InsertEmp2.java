package a_statement;

import java.sql.*;

public class InsertEmp2 {

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
			//------ 입력값 (사용자가 입력한 데이터를 해당 변수에 받아옴)
			String bonmyeng = "본명";
			int welgup = 10000;
			String jikup = "IT";
			
			String sql = "INSERT INTO emp(ename, empno ,sal , job) "
						+ " VALUES( seq_temp2.nextval, '" + bonmyeng +"',"+welgup+",'"+jikup+"')";	// 한줄 내려서 작성 할 경우 공백 필수 
			System.out.println(sql);
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
