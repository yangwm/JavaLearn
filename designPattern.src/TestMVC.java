import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class TestMVC {
	public static void main(String[] args) {
		View v=new View();
	}
}

class View{
	JTextField usernameField;
	JTextField passwordField;
	JTextField showField=new JTextField();
	Model m=new Model("huxz","1234");
	
	public View(){
		JFrame f=new JFrame("hehe");
		f.setSize(600,400);
		f.setLayout(new GridLayout(4,1));
		usernameField=new JTextField();
		passwordField=new JTextField();
		
		usernameField.setText(m.getUsername());
		passwordField.setText(m.getPassword());
		showField.setText(m.getUsername()+"  "+m.getPassword());
		
		JButton b=new JButton("OK");
		f.add(usernameField);
		f.add(passwordField);
		
		showField.setEditable(false);
		f.add(showField);
		f.add(b);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Controller c=new Controller(View.this);
				c.act();
				showField.setText(m.getUsername()+"  "+m.getPassword());
			}
		});	
	}
	public String getPassword() {
		return passwordField.getText();
	}
	public String getUsername() {
		return usernameField.getText();
	}
	public Model getModel(){
		return m;
	}
}
class Model{
	private String username;
	private String password;
	public Model() {
		super();
	}
	public Model(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
class Controller{
	View view;
	public Controller(View v){
		this.view=v;
	}
	public void act(){
		String u=view.getUsername();
		String v=view.getPassword();
		Model m=view.getModel();
		m.setUsername("Name:"+u);
		m.setPassword("Pass:"+v);
	}
}

