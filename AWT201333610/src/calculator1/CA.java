package calculator1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CA extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private double num1, num2, mem;
	private String s1, flag, sign, temp;
	private char key;
	JPanel jp = new JPanel();
	MenuBar mb = new MenuBar();
	JTextField jf = new JTextField(20);
	JButton jl = new JButton();
	JButton jb[] = new JButton[27];
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
	
	CA(){ // 기본틀제작
		jp.setLayout(null);
	
	
		
		jb[0] = new JButton("C");
		jb[1] = new JButton("CE");
		jb[2] = new JButton("Backspace");
		jb[3] = new JButton("MC");
		jb[4] = new JButton("MR");
		jb[5] = new JButton("MS");
		jb[6] = new JButton("M+");
		jb[7] = new JButton("7");
		jb[8] = new JButton("8");
		jb[9] = new JButton("9");
		jb[10] = new JButton("/");
		jb[11] = new JButton("sqrt");
		jb[12] = new JButton("4");
		jb[13] = new JButton("5");
		jb[14] = new JButton("6");
		jb[15] = new JButton("*");
		jb[16] = new JButton("%");
		jb[17] = new JButton("1");
		jb[18] = new JButton("2");
		jb[19] = new JButton("3");
		jb[20] = new JButton("-");
		jb[21] = new JButton("1/x");
		jb[22] = new JButton("0");
		jb[23] = new JButton("±");
		jb[24] = new JButton(".");
		jb[25] = new JButton("+");
		jb[26] = new JButton("=");
		
		jf.setBounds(5,3,300,25);
		jf.setHorizontalAlignment(JTextField.RIGHT);
		jf.setFocusable(false);
		
		jl.setBounds(5,35,52,30);
		jl.setBackground(new Color(210,220,250));
		
		jb[0].setBounds(65,35,70,30);
		jb[1].setBounds(135,35,70,30);
		jb[2].setBounds(205,35,100,30);
		
		for(int i=3,j=70;i<7;i++,j+=30){
			jb[i].setBounds(5,j,52,25);
		}
		for(int i=0,k=30;i<20;i+=5,k+=30){
			for(int ii=7,j=65;ii<11;ii++,j+=45){
				jb[ii+i].setBounds(j,40+k,41,25);
			}
		}
		for(int i=11, j=70;i<27;i+=5,j+=30){
			jb[i].setBounds(245,j,60,25);
		}
		
		jp.add(jf);
		jp.add(jl);
		for(int i=0;i<27;i++){
			jp.add(jb[i]);
			jb[i].addActionListener(this);
			jb[i].setFocusable(false); // 모든 포커스 삭제
		}
		addKeyListener(this);
		
		setMenuBar(mb);
		add(jp);
		
		setFocusable(true);
		setTitle("201333610 kang Calculator");
		setBounds(res.width/2-157,res.height/2-120,315,240); //중앙위치 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 사이즈조절금지
		setVisible(true);
		
		init();
	}
	
	public void actionPerformed(ActionEvent ae){
		flag = ae.getActionCommand();
		process();
	}
	
	public void keyPressed(KeyEvent ke){ // 키누를때
		try{
			key = ke.getKeyChar(); // key = 유니코드값으로입력
			if(key!=65535){ // 연속 쉬프트 방지
				if(key==8)flag="Backspace";
				if(key==12)flag="MC"; //ctrl+l
				if(key==18)flag="MR"; //ctrl+r
				if(key==13)flag="MS"; //ctrl+m
				if(key==16)flag="M+"; //ctrl+p
				if(key==27)flag="C"; // ESC
				if(key==37)flag="%";
				if(key==42)flag="*";
				if(key==43)flag="+";	
				if(key==45)flag="-";	
				if(key==46)flag=".";	
				if(key==47)flag="/";
				if(key==64)flag="sqrt"; // @
				if(key==114)flag="1/x"; // r
				if(key==115)flag="°±"; // s
				if(key==127)flag="CE"; // delete
				if(key==61 || key==10)flag="="; // ENTER or =
				if(key >=48 && key<=57){
					key-=48;
					flag = Integer.toString(key);
				}
				process();
			}
		}catch(Exception e){
			System.out.println("키리너스 잘못 인식" + "\n"  + e);
		}
	}
	
	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}
	
	private void process(){ // 각 버튼에 대한 반응, 버튼, 키 이벤트 실제 처리부분
		try{
			if(flag.equals("Backspace")){
				if(is_hidedouble()) s1 = get_intstr();
				if(s1.length() != 1){ // 한자릿수면 삭제  X 
					s1 = s1.substring(0, s1.length()-1);
				}
				num2 = Double.parseDouble(s1);
				print();
			}
			if("0123456789".indexOf(flag) != -1){ // 0~9 숫자 눌를
				if(s1.isEmpty()){
					s1 = flag;
				}else{
					if((num2 % 1 == 0 && s1.indexOf(".") == -1) ||
							(s1.charAt(s1.length()-2) == '.' && s1.charAt(s1.length()-1) == '0')){ //
						s1 = get_intstr(); // 실수형을 정수형으로 바꿔서 저장
						s1+=flag;
					}else{
						s1+=flag;
					}
				}
				num1 = Double.parseDouble(s1);
				jf.setText(s1);
			}
			if("+-/*%".indexOf(flag) != -1){ // 연산버튼 눌렀을때
				if(temp.equals("+")){ // 연속되게 연산버튼을 눌렀을때
					operation('+',true);
				}else if(temp.equals("-")){
					operation('-',true);
				}else if(temp.equals("*")){
					operation('*',true);
				}else if(temp.equals("/")){
					operation('/',true);
				}else if(temp.equals("%")){
					operation('%',true);
				}else{ // = 버튼으로 연산할때
					if(temp.isEmpty()) temp = flag;
					if(!temp.equals("=")) num2 = num1;
					else temp = flag;
				}
				sign = flag;
				num1 = 0;
				s1 = "";
			}
			if(flag.equals(".")){
				if(is_hidedouble()) s1 = get_intstr();
				if(s1.indexOf(".") == -1){
					s1+=flag;
					jf.setText(s1);
				}
			}
			if(flag.equals("CE")){ //  CE는 숫자만 초기화, C는 완전 초기화 
				num1 = 0; num2 = 0;
				s1 = ""; flag =""; sign=""; temp="";
				jf.setText("0");
			}
			if(flag.equals("C")){
				init();
			}
			if(flag.equals("MC")){
				mem = 0;
				jl.setText("");
			}
			if(flag.equals("MR")){
				if(jl.getText().equals("M")){
					num2 = mem;
					print();
				}
			}
			if(flag.equals("MS")){
				if(temp.isEmpty()){ mem = num1;}
				else {mem = num2;}
				jl.setText("M");
			}
			if(flag.equals("M+")){
				mem+=num2;
			}
			if(flag.equals("1/x")){
				if(num2 == 0) {num1 = 1 / num1; num2 = num1;}
				else {num2 = 1 / num2;}
				print();
			}
			if(flag.equals("±")){
				if(num2 == 0) {num1 *= -1; num2 = num1;}
				else {num2 *= -1;}
				print();
			}
			if(flag.equals("=")){
				if(sign=="+") operation('+',false);
				if(sign=="-") operation('-',false);
				if(sign=="*") operation('*',false);
				if(sign=="/") operation('/',false);
				if(sign=="%") operation('%',false);
			}
			if(flag.equals("sqrt")){
				if(num2 == 0) {num1 *= num1; num2 = num1;}
				else {num2 *= num2;}
				print();
			}
		}catch(Exception e){
			System.out.println("process()∏ﬁº“µÂø°º≠ ¿ﬂ∏¯µ " + "\n" + e);
		}
	}
	
	private String get_intstr(){ // double형을 int 형으로 리턴되어 다시받는 메소드
		return Integer.toString((int)Double.parseDouble(s1));
	}
	
	private boolean is_hidedouble(){ // 겉으로 정수처럼 보이나 실제론 더블형인지 판단.
		if(num2 % 1 == 0 && ((s1.charAt(s1.length()-1)=='0') && s1.charAt(s1.length()-2)=='.')) return true;
		else return false;
	}
	
	private void operation(char swit, boolean swit2){
		switch(swit){
			case '+': num2 += num1; break;
			case '-': num2 -= num1; break;
			case '*': num2 *= num1; break;
			case '/': num2 /= num1; break;
			case '%': num2 %= num1; break;
		}
		temp  = swit2? flag : "=";
		print();
	}
	
	private void init(){ // 초기화
		num1 = 0; num2 = 0; mem=0;
		s1 = ""; flag =""; sign=""; temp="";
		jf.setText("0");
		jl.setText("");
	}
	
	private void print(){ // 출력
		try{
			s1 = Double.toString(num2);
			if(num2 % 1 == 0){ // 정수형으로 나눠떨어지면 화면에 정수형으로 출력
				jf.setText(Integer.toString((int)num2));
			}else{
				jf.setText(Double.toString(num2));
			}
		}catch(Exception e){
			System.out.println("print()메소드에서 잘못됬음 " + "\n"  + e);
		}
	}
}