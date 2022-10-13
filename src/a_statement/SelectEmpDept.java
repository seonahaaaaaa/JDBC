package a_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectEmpDept {

	public static void main(String[] args) {

		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버로딩 성공2");
			
			// 2. 연결객체 얻어오기
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(ur1, user, pass);	// 자바와 오라클을 연결하는 통로 : Connection
			System.out.println("디비 연결 성공2");
			
			// 3. SQL 문장 생성  
			//  => 20번 부서의 사원들의 정보를 추출 - 사번 , 사원명 , 월급 , 부서명 , 근무지
			String sql = "SELECT e.empno empno , e.ename ename , e.sal sal , d.dname dname FROM emp_cp e inner join dept d on d.deptno = e.deptno WHERE e.deptno = 20 ";
			
			// 4. 전송객체 얻어오기
			Statement stmt = con.createStatement();
			
			// 5. SQL 전송
						/*		- ResultSET executeQuery() : SELECT
						 * 		- int형 (입력/삭제/수정 값을 리턴하기 때문에 int형) executeUpdate() : INSERT / DELETE / UPDATE
						 */
			ResultSet rs = stmt.executeQuery(sql); // => rs로 오라클의 호출한 테이블의 데이터가 들어옴
			while(rs.next()) {	// next() : 데이터를 볼때 한칸씩 확인하고 다음 한칸으로 넘어가는 함수
				// 여기에 출력
				int empno 		= rs.getInt("EMPNO"); // => getInt("별칭한 컬럼값")
				String ename 	= rs.getString("ENAME");
				int sal 		= rs.getInt("SAL");
				String dname 		= rs.getString("DNAME");
				System.out.println(empno + "/" + ename + "/" + sal + "/" + dname);
			}
			
			// 6. 닫기 (순서 필수) : 
			rs.close();
			stmt.close();
			con.close();	// 본체
			
			
		} catch (Exception e) {
			System.out.println("실패 : " + e);
		}

	}
}