import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.border.*;
public class Register extends JDialog {//

  JPanel panel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField nickname = new JTextField();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JPasswordField password = new JPasswordField();
  JLabel jLabel4 = new JLabel();
  JTextField email = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JTextPane info = new JTextPane();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JLabel jLabel7 = new JLabel();
  JRadioButton boy = new JRadioButton();
  JRadioButton girl = new JRadioButton();
  JLabel jLabel8 = new JLabel();
  JComboBox place = new JComboBox();
  JComboBox headpic = new JComboBox();

 private String[] pics = new String[] {
		"1.jpg", "3.jpg",
		"5.jpg", "7.jpg"};
String sername;
int serverport;

  public Register(String  s,int port) {
   sername=s;
   serverport=port;
     try {
      jbInit();
      pack();
    }
    catch(Exception e) {
      ex.printStackTrace();}
   
  }
  void jbInit() throws Exception {
    panel1.setLayout(null);
    this.getContentPane().setLayout(null);
    panel1.setMaximumSize(new Dimension(200, 200));
    panel1.setMinimumSize(new Dimension(200, 100));
    panel1.setBounds(new Rectangle(-2, 0, 419, 452));
    this.setTitle("register");
    jLabel1.setText("Name");
    jLabel1.setBounds(new Rectangle(9, 45, 41, 18));
    nickname.setBounds(new Rectangle(50, 44, 128, 22));
    jLabel2.setText("Your Info");
    jLabel2.setBounds(new Rectangle(9, 9, 103, 18));
    jLabel3.setText("Password");
    jLabel3.setBounds(new Rectangle(200, 44, 41, 18));
    password.setBounds(new Rectangle(247, 42, 100, 22));
    jLabel4.setText("Email");
    jLabel4.setBounds(new Rectangle(2, 102, 58, 18));
    email.setBounds(new Rectangle(55, 96, 124, 22));
    jLabel5.setText("Image");
    jLabel5.setBounds(new Rectangle(193, 96, 51, 18));
   
      ComboBoxModel model = new HeadPicCombobox(pics);

   ListCellRenderer renderer = new HeadpicCellRenderer();

    jLabel6.setText("Info");
    jLabel6.setBounds(new Rectangle(6, 189, 87, 18));
    info.setBounds(new Rectangle(5, 208, 363, 103));
    jButton1.setText("Yes");
    jButton1.setBounds(new Rectangle(147, 330, 79, 29));
    jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jButton1_mouseClicked(e);
      }
    });
    jButton2.setText("Cancel");
    jButton2.setBounds(new Rectangle(260, 329, 79, 29));
    jLabel7.setText("Sex");
    jLabel7.setBounds(new Rectangle(9, 156, 41, 18));
    boy.setText("M");
    boy.setBounds(new Rectangle(43, 152, 38, 26));
    girl.setText("F");
    girl.setBounds(new Rectangle(80, 152, 36, 26));
    jLabel8.setText("From");
    jLabel8.setBounds(new Rectangle(147, 154, 41, 18));
    place.setToolTipText("");
    place.addItem("USA");
    place.addItem("China");
    place.setBounds(new Rectangle(181, 153, 163, 22));
    headpic.setBounds(new Rectangle(249, 91, 71, 28));
    headpic.setModel(model);
    headpic.setRenderer(renderer);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(panel1, null);
    panel1.add(jLabel2, null);
    panel1.add(jLabel1, null);
    panel1.add(nickname, null);
    panel1.add(jLabel3, null);
    panel1.add(password, null);
    panel1.add(jLabel4, null);
    panel1.add(email, null);
    panel1.add(jLabel5, null);
    panel1.add(info, null);
    panel1.add(jButton2, null);
    panel1.add(jLabel6, null);
    panel1.add(jLabel7, null);
    panel1.add(boy, null);
    panel1.add(jLabel8, null);
    panel1.add(girl, null);
    panel1.add(place, null);
    panel1.add(headpic, null);
  }

  void jButton1_mouseClicked(MouseEvent e) {
 try{ System.out.println(sername);
    System.out.println(serverport);
  Socket socket=new Socket(InetAddress.getByName(sername),serverport);

      BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out=new PrintWriter(new BufferedWriter(
                              new OutputStreamWriter(socket.getOutputStream())),true);
out.println("new");
out.println(nickname.getText().trim());
out.println(password.getPassword());
out.println(email.getText().trim());
out.println(info.getText().trim());
out.println(place.getSelectedItem());
out.println(headpic.getSelectedIndex());
int no;
no=Integer.parseInt(in.readLine());
System.out.print(no);

 String str=" ";

       
        if(str.equals("false")) JOptionPane.showMessageDialog(this,"Sorry:-(","ok",JOptionPane.INFORMATION_MESSAGE);
       else{
       JOptionPane.showMessageDialog(this,"your ID# is"+no,"ok",JOptionPane.INFORMATION_MESSAGE);

       this.dispose();
            MainWin f2=new MainWin(no,sername,serverport);
    f2.setVisible(true);}

    }catch(IOException e1){}

  }
}
class HeadPicCombobox extends DefaultComboBoxModel {
	public HeadPicCombobox(String[] pics) {
		for(int i=0; i < pics.length; ++i) {

			addElement(new Object[] { new ImageIcon(pics[i]) } );
		}
	}

	public Icon getIcon(Object object) {
		Object[] array = (Object[])object;
		return (Icon)array[0];
	}
}
class HeadpicCellRenderer extends JLabel implements ListCellRenderer {
	private Border
		lineBorder = BorderFactory.createLineBorder(Color.red, 2),
		emptyBorder = BorderFactory.createEmptyBorder(2,2,2,2);

	public HeadpicCellRenderer() {
		setOpaque(true);
	}
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		HeadPicCombobox model =(HeadPicCombobox)list.getModel();

		setIcon(model.getIcon(value));

		if(isSelected) {
			setForeground(list.getSelectionForeground());
			setBackground(list.getSelectionBackground());
		}
		else {
			setForeground(list.getForeground());
			setBackground(list.getBackground());
		}

		if(cellHasFocus) setBorder(lineBorder);
		else 			 setBorder(emptyBorder);

		return this;
	}
}
