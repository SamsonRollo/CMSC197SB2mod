import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Cursor;
public class UIPop extends generalPanel{
	private JPanel pane;
	private JPanel panel;
	private JLabel bg;
	protected int size, btnX, btnY;
	protected JButton cancel, erase;
	protected JButton btn[];
	protected JTextField field;
	UIPop(int size, JPanel pane){
		this.pane=pane;
		this.size=size;
		panel=addPanel(pane, 5, 84, 500,500);
		panel.setOpaque(true);
		if(size==9)
			nineButtons();
		else if(size==16)
			sixteenButtons();
		else 
			twentyfiveButtons();
		bg=addLabel(panel, "img/game control/"+size+"x"+size+".png",0,0);
		field.grabFocus();
		}

	private void nineButtons(){
		btn=new JButton[9];
		erase=addButton(panel, "img/box/misc/clear.png", 146,126);
		cancel=addButton(panel, "img/box/misc/cancel.png", 293,126);
		btn[0]=addButton(panel, "img/box/9x9/pop/1.png", 111,192);
		btn[1]=addButton(panel, "img/box/9x9/pop/2.png", 184,192);
		btn[2]=addButton(panel, "img/box/9x9/pop/3.png", 257,192);
		btn[3]=addButton(panel, "img/box/9x9/pop/4.png", 330,192);
		btn[4]=addButton(panel, "img/box/9x9/pop/5.png", 147,257);
		btn[5]=addButton(panel, "img/box/9x9/pop/6.png", 220,257);
		btn[6]=addButton(panel, "img/box/9x9/pop/7.png", 293,257);
		btn[7]=addButton(panel, "img/box/9x9/pop/8.png", 184,322);
		btn[8]=addButton(panel, "img/box/9x9/pop/9.png", 257,322);
		field=addTextField(panel, "", 220, 128,40,38);
		}
	private void sixteenButtons(){ //position this with care
		btn=new JButton[16];
		erase=addButton(panel, "img/box/misc/clear.png", 111,81);
		cancel=addButton(panel, "img/box/misc/cancel.png", 329,81);
		btn[0]=addButton(panel, "img/box/16x16/pop/1.png", 74,147);
		btn[1]=addButton(panel, "img/box/16x16/pop/2.png", 147,147);
		btn[2]=addButton(panel, "img/box/16x16/pop/3.png", 220,147);
		btn[3]=addButton(panel, "img/box/16x16/pop/4.png", 293,147);
		btn[4]=addButton(panel, "img/box/16x16/pop/5.png", 366,147);

		btn[5]=addButton(panel, "img/box/16x16/pop/6.png", 111,212);
		btn[6]=addButton(panel, "img/box/16x16/pop/7.png", 184,212);
		btn[7]=addButton(panel, "img/box/16x16/pop/8.png", 257,212);
		btn[8]=addButton(panel, "img/box/16x16/pop/9.png", 330,212);

		btn[9]=addButton(panel, "img/box/16x16/pop/10.png", 147,277);
		btn[10]=addButton(panel, "img/box/16x16/pop/11.png", 220,277);
		btn[11]=addButton(panel, "img/box/16x16/pop/12.png", 293,277);
		btn[12]=addButton(panel, "img/box/16x16/pop/13.png", 111,342);

		btn[13]=addButton(panel, "img/box/16x16/pop/14.png", 184,342);
		btn[14]=addButton(panel, "img/box/16x16/pop/15.png", 257,342);
		btn[15]=addButton(panel, "img/box/16x16/pop/16.png", 330,342);
		field=addTextField(panel, "", 200, 71,80,38);
		}
		//add for 25x25
	private void twentyfiveButtons(){
		btn=new JButton[25];
		erase=addButton(panel, "img/box/misc/clear.png", 121,87);
		cancel=addButton(panel, "img/box/misc/cancel.png", 334,87);
		btn[0]=addButton(panel, "img/box/25x25/pop/1.png", 23,156);
		btn[1]=addButton(panel, "img/box/25x25/pop/2.png", 93,156);
		btn[2]=addButton(panel, "img/box/25x25/pop/3.png", 162,156);
		btn[3]=addButton(panel, "img/box/25x25/pop/4.png", 228,156);
		btn[4]=addButton(panel, "img/box/25x25/pop/5.png", 300,156);
		btn[5]=addButton(panel, "img/box/25x25/pop/6.png", 368,156);
		btn[6]=addButton(panel, "img/box/25x25/pop/7.png", 439,156);

		btn[7]=addButton(panel, "img/box/25x25/pop/8.png", 58,221);
		btn[8]=addButton(panel, "img/box/25x25/pop/9.png", 127,221);
		btn[9]=addButton(panel, "img/box/25x25/pop/10.png", 195,221);
		btn[10]=addButton(panel, "img/box/25x25/pop/11.png", 264,221);
		btn[11]=addButton(panel, "img/box/25x25/pop/12.png", 334,221);
		btn[12]=addButton(panel, "img/box/25x25/pop/13.png", 403,221);

		btn[13]=addButton(panel, "img/box/25x25/pop/14.png", 93,286);
		btn[14]=addButton(panel, "img/box/25x25/pop/15.png", 162,286);
		btn[15]=addButton(panel, "img/box/25x25/pop/16.png", 230,286);
		btn[16]=addButton(panel, "img/box/25x25/pop/17.png", 300,286);
		btn[17]=addButton(panel, "img/box/25x25/pop/18.png", 368,286);

		btn[18]=addButton(panel, "img/box/25x25/pop/19.png", 127,351);
		btn[19]=addButton(panel, "img/box/25x25/pop/20.png", 195,351);
		btn[20]=addButton(panel, "img/box/25x25/pop/21.png", 264,351);
		btn[21]=addButton(panel, "img/box/25x25/pop/22.png", 334,351);

		btn[22]=addButton(panel, "img/box/25x25/pop/23.png", 162,412);
		btn[23]=addButton(panel, "img/box/25x25/pop/24.png", 230,412);
		btn[24]=addButton(panel, "img/box/25x25/pop/25.png", 300,412);
		
		field=addTextField(panel, "", 210, 77,80,38);
	}
	protected void setVisible(boolean isVisible, int btnX, int btnY, int num){
		pane.setVisible(isVisible);
		if(num==0)
			field.setText("");
		else
			field.setText(num+"");
		field.grabFocus();
		this.btnX=btnX;
		this.btnY=btnY;
		}
	protected void decompose(){
		pane.removeAll();
		panel=null;
		bg=null;
		cancel=erase=null;
		for(int ctr=0; ctr<size; ctr++)
			btn[ctr]=null;
		}
	}