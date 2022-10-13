package d_info.copy;

// awt 패키지 전부 : *
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

// swing 패키지 전부 : *
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InfoView {	// 고객한테 보여지는 화면

//	1. 멤버 변수 선언
	JFrame f;
	JTextField tfName, tfID, tfTel, tfGender, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelate, bCancel, bExit;
	ImageIcon image;

	// 비지니스로직 - 모델단
	InfoModel model;

//	2. 멤버 변수 객체생성
	InfoView() {
		// 타이틀
		f = new JFrame("DBTest");

		// 동쪽 입력값
		tfName = new JTextField(20);
		tfID = new JTextField(20);
		tfTel = new JTextField(20);
		tfGender = new JTextField(20);
		tfAge = new JTextField(20);
		tfHome = new JTextField(20);

		// 남쪽 버튼창
		bAdd = new JButton("Name");

		bShow = new JButton("Show (alt+s)", new ImageIcon("src\\b_info2\\imgs\\4.PNG"));
		bShow.setHorizontalTextPosition(JButton.CENTER); // LEFT - 왼쪽 / CENTER - 가운데 / RIGHT - 오른쪽
		bShow.setVerticalTextPosition(JButton.BOTTOM); // TOP - 상 / CENTER - 가운데 / BOTTOM - 하
		bShow.setMnemonic('s');

		bSearch = new JButton("Search");

		bDelate = new JButton("Delate");
		bCancel = new JButton("Cancel");

		bExit = new JButton("수정하기"); // ImageIcon => Icon의 자식 클래스 :
																							// 이미지 객체 생성 후 이미지 소스 경로 입력
		bExit.setHorizontalTextPosition(JButton.CENTER); // 텍스트 위치를 수평으로
		bExit.setVerticalTextPosition(JButton.BOTTOM); // 텍스트 위치를 수직으로
		bExit.setRolloverIcon(new ImageIcon("src\\b_info2\\imgs\\1.PNG")); // 마우스 커서 갖다댈시에 이미지 변경 효과
		bExit.setPressedIcon(new ImageIcon("src\\b_info2\\imgs\\2.PNG")); // 마우스 클릭시에 이미지 변경되는 효과
		bExit.setToolTipText("프로그램을 종료하겠습니다."); // 마우스 커서 갖다댈시 말풍선 삽입
		bExit.setMnemonic('x'); // 단축키 지정

		// center 입력창
		ta = new JTextArea(40, 20);

		// 모델객체 생성
		try { // InfoModelImpul 클래스 기본생성자에서 발생한 예외를 받아서 해결
			model = new InfoModelImpul();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	3. 화면 구성하고 출력
	/*
	 * 전체 프레임은 BorderLayout 지정 - West : JPanel 붙이기 (GridLayout(6,2)) - Center :
	 * 텍스트에어리어 - South : JPanel 붙이기 Button (GridLayout(1,6))
	 * 
	 */
	public void addLayout() {
		f.setLayout(new BorderLayout());

		JPanel tfWest = new JPanel();
		tfWest.setLayout(new GridLayout(6, 2));
		tfWest.add(new JLabel("Name", JLabel.CENTER));
		tfWest.add(tfName);
		tfWest.add(new JLabel("ID", JLabel.CENTER));
		tfWest.add(tfID);
		tfWest.add(new JLabel("Tel", JLabel.CENTER));
		tfWest.add(tfTel);
		tfWest.add(new JLabel("Sex", JLabel.CENTER));
		tfWest.add(tfGender);
		tfWest.add(new JLabel("Age", JLabel.CENTER));
		tfWest.add(tfAge);
		tfWest.add(new JLabel("Home", JLabel.CENTER));
		tfWest.add(tfHome);
		f.add(tfWest, BorderLayout.WEST);

		f.add(ta, BorderLayout.CENTER); // 가운데 입력값

		JPanel bSouth = new JPanel();
		bSouth.setLayout(new GridLayout(1, 2));
		bSouth.add(bAdd);
		bSouth.add(bShow);
		bSouth.add(bSearch);
		bSouth.add(bDelate);
		bSouth.add(bCancel);
		bSouth.add(bExit);
		f.add(bSouth, BorderLayout.SOUTH);

		// 화면 출력
		f.setBounds(300, 300, 900, 400); // 화면 출력 크기 (가로/세로 길이) 설정
		f.setVisible(true); // 화면에 나타나게 해줌. (출력)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.EXIT_ON_CLOSE : X 창 클릭시 실행 종료 , 존재하지 않을시 창은 없어지나 실행은
		f.setTitle("DBTest");

	}

	void eventProc() {
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertData();
			}
		});

		// show 버튼이 눌렸을때 
		bShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		});
		
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			}
		});
		
		tfTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			}
		});
		
		// bDelete 버튼이 눌렸을때
		bDelate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteByTel();
			}
		});
		
		
	}
	
	void deleteByTel() {
		// (1) 입력한 전화번호 값을 얻어오기
		String tel = tfTel.getText();
		// (2) 모델단에 deleteByTel() 호출
		try {
			model.delate(tel);
		}catch (SQLException e) {
			ta.setText(tel);
		}
		// (3) 화면을 지우고
		clearText();
		
	}

	void selectAll() {
		
		try {
			ArrayList<InfoVO> data = model.selectAll();
			ta.setText(" ------- 검색결과 ---------- \n\n");
			for (InfoVO vo : data) {
				ta.append(vo.toString());
			}

		} catch (SQLException e) {
			ta.setText("검색실패 : " + e.getMessage());
		}

	}

	void insertData() {
		// (1) 사용자 입력값 얻어오기
		String name = tfName.getText();
		String id = tfID.getText();
		String tel = tfTel.getText();
		String gender = tfGender.getText();
		int age = Integer.parseInt(tfAge.getText());
		String home = tfHome.getText();

		// (2) 1번의 값들을 하나의 InfoVO에 지정 - (1) 생성자 (2) setter
		InfoVO vo = new InfoVO();
		// 생성자 이용시 : InfoVO vo = new InfoVO(name, id, tel, gender, age, home);
		// setter 이용시
		vo.setName(name);
		vo.setId(id);
		vo.setTel(tel);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setHome(home);
		// (3) 모델의 insertInfo() 호출
		try { // InfoModelImpul 클래스 인자값이 있는 생성자에서 발생한 예외를 받아서 해결
			model.insertInfo(vo);
		} catch (SQLException e) {
			e.printStackTrace(); // 에러의 발생근원지를 찾아서 단계별로 에러를 출력합니다.
		}
		// (4) 화면의 입력값들을 지우기
		clearText();
	}

	void clearText() {
		tfName.setText(null);
		tfID.setText(null);
		tfTel.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfHome.setText(null);
	}
	
	void selectByTel() {
		try {
			// (1) 입력한 전화번호 값을 얻어오기
			String tel = tfTel.getText();
			
			// (2) 모델단에 selectByTel() 호출
			InfoVO vo = model.selectByTel(tel);
			
			// (3) 받은 결과를 각각의 텍스트 필드에 지정 (출력)
			tfName.setText(vo.getName());
			tfID.setText(vo.getId());
			tfTel.setText(vo.getTel());
			tfGender.setText(vo.getGender());
			tfAge.setText(Integer.toString(vo.getAge()));
			tfHome.setText(vo.getHome());
		} catch (Exception e) {
			ta.setText("전화번호 검색 실패 : " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	
	public static void main(String[] args) {

		InfoView info = new InfoView();
		info.addLayout();
		info.eventProc();
	}

}
