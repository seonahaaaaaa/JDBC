package c_info2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoModelImpul implements InfoModel { // 프로그래머가 확인하는 창

	// final로 선언한 경우 변수 부분 대문자로 표기
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe"; // 127.0.0.1 : 본인 컴퓨터
	final static String USER = "scott";
	final static String PASS = "tiger";

	public InfoModelImpul() throws ClassNotFoundException {
		// try catch 문 : 예외를 선언한 자리에서 잡아야 함 (고객이 오류 발생시에 오류건을 해결하지 않으면 다음 동작이 실행되지 않음)
		// throws 문 : 오류가 발생하여도 우선 다음 동작을 실행함 (고객은 오류가 발생한지 모름) ==> infoview에서 try
		// catch문을 사용하여 throws 해당 구문에서 발생한 오류를 잡음
		// 1. 드라이버 로딩
		Class.forName(DRIVER);
		System.out.println("드라이버로딩 성공");
	}

	/*
	 * 사용자 입력값을 받아서 DB에 저장하는 역할
	 */

	public void insertInfo(InfoVO vo) throws SQLException {
		// 2. 연결객체 얻어오기
		Connection con = null; // 자바에서 작성한 SQL문을 오라클로 넘기기 위한 값을 담는 카트의 역할
		PreparedStatement ps = null; // Connection 에서 선언한 con이라는 변수에 담긴 쿼리의 값을 오라클로 날리는 역할
		// PreparedStatement : 3.sql 문장에서 바로 값 지정 안하고 따로 값을 세팅 할 때 사용
		// Statement : 3.sql 변수에 바로 값을 입력 할 때 사용
		try {
			con = DriverManager.getConnection(URL, USER, PASS); // 상단에 선언한 url , user , pass

			// 3. sql 문장 (#)
			String sql = " INSERT INTO info_tab(name , jumin , tel , gender , age , home) " + " VALUES(?,?,?,?,?,?)"; // 값
																														// 미지정
																														// (?,?,?,?,?,?)

			// 4. 전송객체
			ps = con.prepareStatement(sql); // 작성한 쿼리를 con 카트에 담아서 ps 변수에 저장
			// 세팅 - #
			ps.setString(1, vo.getName()); // infoView 에 지정했던 vo.setName(name); name 값을 얻어와서 ps에 저장
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getTel());
			ps.setString(4, vo.getGender());
			ps.setInt(5, vo.getAge());
			ps.setString(6, vo.getHome());

			// 5. 전송
			ps.executeUpdate(); // 데이터베이스에서 데이터를 추가(Insert), 삭제(Delete), 수정(Update)하는 SQL 문을 실행

		} finally {
			// 6. 닫기 (필수) : Connection 의 갯수는 사용하는 사용자의 수보다
			ps.close();
			con.close();

		}
	}

	/*
	 * 전체 Info_tab 의 레코드 검색
	 */

	@Override
	public ArrayList<InfoVO> selectAll() throws SQLException { // 테이블의 전체 레코드 가져오기.

		// ArrayList<InfoVO> 가져올 자료형

		Connection con = null; // 자바에서 작성한 SQL문을 오라클로 넘기기 위한 값을 담는 카트의 역할
		PreparedStatement ps = null; // Connection 에서 선언한 con이라는 변수에 담긴 쿼리의 값을 오라클로 날리는 역할
		ResultSet rs = null;
		// PreparedStatement : 3.sql 문장에서 바로 값 지정 안하고 따로 값을 세팅 할 때 사용
		// Statement : 3.sql 변수에 바로 값을 입력 할 때 사용
		try {
			con = DriverManager.getConnection(URL, USER, PASS); // 상단에 선언한 url , user , pass

			// 3. SQL 문장
			String sql = "SELECT * FROM info_tab"; // info_tab 의 전체 레코드 불러오기

			// 4. 전송 객체 얻어오기
			ps = con.prepareStatement(sql);

			// 5. 전송
			rs = ps.executeQuery();

			ArrayList<InfoVO> list = new ArrayList<InfoVO>(); // 전체 레코드의 해당하는 갯수가 몇개인지 확실치 않으니 리스트로 배열해서 가져오기.
			while (rs.next()) { // 전송 할 값이 있으면 true
				InfoVO vo = new InfoVO();
				vo.setName(rs.getString("NAME"));
				vo.setId(rs.getString("JUMIN")); // 실제 컬럼명
				vo.setTel(rs.getString("TEL"));
				vo.setGender(rs.getString("GENDER"));
				vo.setAge(rs.getInt("AGE"));
				vo.setHome(rs.getString("HOME"));

				list.add(vo);

			}

			return list;
			
		} finally {
			// 6. 닫기 (필수) : Connection 의 갯수는 사용하는 사용자의 수보다
			rs.close();
			ps.close();
			con.close();

		}
	}

	@Override
	public InfoVO selectByTel(String tel) throws SQLException {
		Connection con = null; // 자바에서 작성한 SQL문을 오라클로 넘기기 위한 값을 담는 카트의 역할
		PreparedStatement ps = null; // Connection 에서 선언한 con이라는 변수에 담긴 쿼리의 값을 오라클로 날리는 역할
		ResultSet rs = null;	// ResultSet : SELECT의 결과를 저장하는 객체
		InfoVO vo = new InfoVO();
		// PreparedStatement : 3.sql 문장에서 바로 값 지정 안하고 따로 값을 세팅 할 때 사용
		// Statement : 3.sql 변수에 바로 값을 입력 할 때 사용
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. SQL 문장
			String sql = "SELECT * FROM info_tab WHERE tel=?"; 	// 입력 되어있는 tel 의 전체 레코드를 sql 변수에 저장

			// 4. 전송 객체 얻어오기
			ps = con.prepareStatement(sql);	// 전송받은 sql 을 con 에 담음
			ps.setString(1, tel);

			// 5. 전송
			rs = ps.executeQuery();

			if (rs.next()) {
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(rs.getInt("age"));
				vo.setHome(rs.getString("home"));
			}

		} finally {
			// 6. 닫기 (필수) : Connection 의 갯수는 사용하는 사용자의 수보다
			ps.close();
			con.close();

		}

		return vo;
	}
	
	/*
	 * 메소드명 : delete
	 * 인자    : 전화번호
	 * 리턴값   : 삭제한 행 수
	 * 역할    : 전화번호를 넘겨받아 해당 전화번호의 레코드를 삭제
	 */


	@Override
	public int delate(String tel) throws SQLException {
		return 0;
	}

}
