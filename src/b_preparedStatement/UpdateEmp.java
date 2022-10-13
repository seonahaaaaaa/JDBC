package b_preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateEmp {

	public static void main(String[] args) {

		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버로딩 성공");
			// 2. 연결객체 얻어오기
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";

			Connection con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");

			// 입력값
			int sabun = 7698;
			String saname = "아무개";
			int salary = 15000;

			// 7698 사원의 이름과 월급을 변경하세요
			// 3. SQL 문장
			String sql = "UPDATE emp_cp SET ename = ?  , sal = ? WHERE empno = ? ";
			System.out.println(sql);

			// 4. SQL 전송객체
			PreparedStatement stmt = con.prepareStatement(sql);	//******************* 이미 sql 연결함
			stmt.setString(1, saname);
			stmt.setInt(2, salary);
			stmt.setInt(3, sabun);
			
			// 5. SQL 전송
			/*
			 * - ResultSET rs = executeQuery() : SELECT - int형 (입력/삭제/수정 값을 리턴하기 때문에 int형)
			 * - int result = executeUpdate() : INSERT / DELETE / UPDATE
			 */
			int result = stmt.executeUpdate(); // => rs로 오라클의 호출한 테이블의 데이터가 들어옴
			System.out.println(result + "행을 실행");
		
			// 6. 닫기 (순서 필수) :
			stmt.close();
			con.close(); // 본체

		} catch (Exception e) {
			System.out.println("DB 실패 : " + e);
		}

	}

}
