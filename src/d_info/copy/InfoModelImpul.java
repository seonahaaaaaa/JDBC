package d_info.copy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoModelImpul implements InfoModel { // 프로그래머가 확인하는 창

	// final로 선언한 경우 변수 부분 대문자로 표기
	final static String DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	final static String URL 	= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	final static String USER 	= "scott";
	final static String PASS 	= "tiger";
	
	public InfoModelImpul() throws ClassNotFoundException {
	// 1. 드라이버 로딩	
	Class.forName(DRIVER); // 드라이버 로딩
	System.out.println("드라이버로딩 성공");
	}
	
	public void insertInfo(InfoVO vo) throws SQLException {	// try ~ finally : 예외가 발생하는 것과 상관없이 언제나 실행되는 로직
	// 2. 연결객체 얻어오기
	// 지역변수 초기화	
		Connection con = null;	// 데이터베이스와 연결되어 작업을 수행할 수 있는 통로 역할
		PreparedStatement ps = null;	// 쿼리 값을 전달받아 오라클에 날리는 역할
		
		try {	// 해당 구문에서 예외처리가 발생하던, 발생하지 않던 finally문 무조건 실행
		con = DriverManager.getConnection(URL, USER, PASS);
		// DriverManager : Class.forName(DRIVER) => 로드 된 드라이버를 통해서 커넥션을 활성화 해주는 객체 
		
		// 3. sql 문장 (INSERT 문장)
		String sql = " INSERT INTO info_tab(name , jumin , tel , gender , age , home) " + " VALUES(?,?,?,?,?,?)"; 
		
		// 4. 전송 객체 (위에서 입력한 sql 문장을 전달받아 오라클에 날림)
		ps = con.prepareStatement(sql);
		// 세팅 => InfoVO vo 객체 생성 후 얻어온 값을 ps 입력 값 순서대로 저장
		ps.setString(1,vo.getName());
		ps.setString(2,vo.getId());
		ps.setString(3, vo.getTel());
		ps.setString(4,vo.getGender());
		ps.setString(5, Integer.toString(vo.getAge()));
		ps.setString(6, vo.getHome());
		
		// 5. 전송
		ps.executeUpdate();	// SQL문 실행
		
	} finally {
		ps.close();
		con.close();
	
	  }
		
	}	
	
	
	// info_tab의 전체 데이터 가져오기
	// selectAll() : 전체 데이터 가져오기
	public ArrayList<InfoVO> selectAll() throws SQLException {
		
		// 지역변수 초기화
		Connection con = null;	// 데이터베이스와 연결되어 작업을 수행할 수 있는 통로 역할
		PreparedStatement ps = null;	// 쿼리 값을 전달받아 오라클에 날리는 역할
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		// DriverManager : Class.forName(DRIVER) => 로드 된 드라이버를 통해서 커넥션을 활성화 해주는 객체 
		
		// 3. sql 문장 (info_tab 의 전체 데이터 불러오기)
		String sql = "SELECT * FROM info_tab"; 
		
		// 4. 전송 객체 (위에서 입력한 sql 문장을 전달받아 오라클에 날림)
		ps = con.prepareStatement(sql);	// sql : 현재 info_tab 테이블에 저장 되어있는 전체 데이터
		
		// 5. 전송
		rs = ps.executeQuery();		// SELECT * FROM 테이블명 : 전체 테이블에 저장 된 값을 가져와야하므로 executequery() 사용	
		
		ArrayList<InfoVO> list = new ArrayList<InfoVO>(); // 테이블에 저장 된 모든 값을 나열해와서 list에 저장.
		while (rs.next()) {	// 5. 전송받은 쿼리에 값이 있으면 true
			// 가져올 테이블에 데이터가 몇줄이 있을지 정확하게 확인이 어려우니 while 문 사용해서 한줄 나열, 리턴해서 list로 가서 다시 반복하여
			// 두번째 줄 나열함
			InfoVO vo = new InfoVO();
			vo.setName(rs.getString("name"));
			vo.setId(rs.getString("jumin"));
			vo.setTel(rs.getString("tel"));
			vo.setGender(rs.getString("gender"));
			vo.setAge(rs.getInt("age"));
			vo.setHome(rs.getString("home"));
			
			list.add(vo);
			// vo 의 값을 list 에 추가
		}
		return list;
			// vo 의 값을 list에 추가 후 list로 리턴
		
	} finally {
		rs.close();
		ps.close();
		con.close();
		}
	}
	
	// 입력한 tel 값 가져오기
	public InfoVO selectByTel(String tel) throws SQLException {
	// InfoVO 클래스에서 입력받은 tel 값을 가져와야함.
		
		// 지역변수 초기화
		Connection con = null;	// 데이터베이스와 연결되어 작업을 수행할 수 있는 통로 역할
		PreparedStatement ps = null;	// 쿼리 값을 전달받아 오라클에 날리는 역할
		ResultSet rs = null;
		
		InfoVO vo = new InfoVO();	// InfoVO 클래스에서 받아온 값을 가져와야하므로 객체 생성
									// 값을 가져와 vo 변수에 담음
		try {
			con = DriverManager.getConnection(URL , USER , PASS);
			// DriverManager : Class.forName(DRIVER) => 로드 된 드라이버를 통해서 커넥션을 활성화 해주는 객체 
			
			// 3. sql 문장 (tel 값을 비교하여 해당하는 데이터 가져오기)
			String sql = "SELECT * FROM info_tab WHERE tel=?";
			
			// 4. 전송 객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			
			// 5. 전송
			rs = ps.executeQuery();
			
			// if 문 사용 이유 : 입력한 값이 조회 할 해당 테이블에 있을수도 있고 없을 수도 있는 두가지 경우의 수가 있으므로 if 문 사용
			if (rs.next()) {
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(rs.getInt("age"));
				vo.setHome(rs.getString("home"));
			}
			return vo;	// if 문은 무한 반복이 아니므로 리턴 순서 상관없음. finally 다음 구문에 나와도 상관없음. 
			
		  } finally {	// try문이 없으니 오류가 발생하여도 정상적으로 작동됨.
			  ps.close();
			  con.close();
		  }

	}


	@Override
	public int delate(String tel) throws SQLException {
		// 입력한 tel 값 초기화
		return 0;
	}
	

	
}
