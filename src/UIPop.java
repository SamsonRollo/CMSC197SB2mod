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
		if(size==6)
			sixButtons();
		else if(size==9)
			nineButtons();
		else if(size==16)
			sixteenButtons();
		else if(size==25)
			twentyfiveButtons();
		else
			twelveButtons();
		bg=addLabel(panel, "img/game control/"+size+"x"+size+".png",0,0);
		field.grabFocus();
		}
	private void sixButtons(){
		btn=new JButton[6];
		erase=addButton(panel, "img/box/misc/clear.png", 184,126);
		cancel=addButton(panel, "img/box/misc/cancel.png", 256,126);
		btn[0]=addButton(panel, "img/box/12x12/normal/1.png", 147,192);
		btn[1]=addButton(panel, "img/box/12x12/normal/2.png", 221,192);
		btn[2]=addButton(panel, "img/box/12x12/normal/3.png", 295,192);
		btn[3]=addButton(panel, "img/box/12x12/normal/4.png", 183,257);
		btn[4]=addButton(panel, "img/box/12x12/normal/5.png", 257,257);
		btn[5]=addButton(panel, "img/box/12x12/normal/6.png", 221,322);
		field=addTextField(panel, "", 221, 62,40,38);
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
	private void twelveButtons(){
		btn=new JButton[12];
		erase=addButton(panel, "img/box/misc/clear.png", 111,126);
		cancel=addButton(panel, "img/box/misc/cancel.png", 329,126);
		btn[0]=addButton(panel, "img/box/12x12/normal/1.png", 74,192);
		btn[1]=addButton(panel, "img/box/12x12/normal/2.png", 147,192);
		btn[2]=addButton(panel, "img/box/12x12/normal/3.png", 220,192);
		btn[3]=addButton(panel, "img/box/12x12/normal/4.png", 293,192);
		btn[4]=addButton(panel, "img/box/12x12/normal/5.png", 366,192);

		btn[5]=addButton(panel, "img/box/12x12/normal/6.png", 111,257);
		btn[6]=addButton(panel, "img/box/12x12/normal/7.png", 184,257);
		btn[7]=addButton(panel, "img/box/12x12/normal/8.png", 257,257);
		btn[8]=addButton(panel, "img/box/12x12/normal/9.png", 330,257);

		btn[9]=addButton(panel, "img/box/12x12/normal/10.png", 147,322);
		btn[10]=addButton(panel, "img/box/12x12/normal/11.png", 220,322);
		btn[11]=addButton(panel, "img/box/12x12/normal/12.png", 293,322);
		field=addTextField(panel, "", 200, 128,80,38);
		}
	private void sixteenButtons(){ //position this with care
		btn=new JButton[16];
		erase=addButton(panel, "img/box/misc/clear.png", 111,126);
		cancel=addButton(panel, "img/box/misc/cancel.png", 329,126);
		btn[0]=addButton(panel, "img/box/16x16/pop/1.png", 74,192);
		btn[1]=addButton(panel, "img/box/16x16/pop/2.png", 147,192);
		btn[2]=addButton(panel, "img/box/16x16/pop/3.png", 220,192);
		btn[3]=addButton(panel, "img/box/16x16/pop/4.png", 293,192);
		btn[4]=addButton(panel, "img/box/16x16/pop/5.png", 366,192);

		btn[5]=addButton(panel, "img/box/16x16/pop/6.png", 111,257);
		btn[6]=addButton(panel, "img/box/16x16/pop/7.png", 184,257);
		btn[7]=addButton(panel, "img/box/16x16/pop/8.png", 257,257);
		btn[8]=addButton(panel, "img/box/16x16/pop/9.png", 330,257);

		btn[9]=addButton(panel, "img/box/16x16/pop/10.png", 147,322);
		btn[10]=addButton(panel, "img/box/16x16/pop/11.png", 220,322);
		btn[11]=addButton(panel, "img/box/16x16/pop/12.png", 293,322);
		btn[12]=addButton(panel, "img/box/16x16/pop/13.png", 111,387);

		btn[13]=addButton(panel, "img/box/16x16/pop/14.png", 184,387);
		btn[14]=addButton(panel, "img/box/16x16/pop/15.png", 257,387);
		btn[15]=addButton(panel, "img/box/16x16/pop/16.png", 330,387);
		field=addTextField(panel, "", 200, 128,80,38);
		}
		//add for 25x25
	private void twentyfiveButtons(){
		btn=new JButton[25];
		erase=addButton(panel, "img/box/misc/clear.png", 121,102);
		cancel=addButton(panel, "img/box/misc/cancel.png", 334,102);
		btn[0]=addButton(panel, "img/box/25x25/pop/1.png", 23,171);
		btn[1]=addButton(panel, "img/box/25x25/pop/2.png", 93,171);
		btn[2]=addButton(panel, "img/box/25x25/pop/3.png", 162,171);
		btn[3]=addButton(panel, "img/box/25x25/pop/4.png", 228,171);
		btn[4]=addButton(panel, "img/box/25x25/pop/5.png", 300,171);
		btn[5]=addButton(panel, "img/box/25x25/pop/6.png", 368,171);
		btn[6]=addButton(panel, "img/box/25x25/pop/7.png", 439,171);

		btn[7]=addButton(panel, "img/box/25x25/pop/8.png", 58,236);
		btn[8]=addButton(panel, "img/box/25x25/pop/9.png", 127,236);
		btn[9]=addButton(panel, "img/box/25x25/pop/10.png", 195,236);
		btn[10]=addButton(panel, "img/box/25x25/pop/11.png", 264,236);
		btn[11]=addButton(panel, "img/box/25x25/pop/12.png", 334,236);
		btn[12]=addButton(panel, "img/box/25x25/pop/13.png", 403,236);

		btn[13]=addButton(panel, "img/box/25x25/pop/14.png", 93,301);
		btn[14]=addButton(panel, "img/box/25x25/pop/15.png", 162,301);
		btn[15]=addButton(panel, "img/box/25x25/pop/16.png", 230,301);
		btn[16]=addButton(panel, "img/box/25x25/pop/17.png", 300,301);
		btn[17]=addButton(panel, "img/box/25x25/pop/18.png", 368,301);

		btn[18]=addButton(panel, "img/box/25x25/pop/19.png", 127,366);
		btn[19]=addButton(panel, "img/box/25x25/pop/20.png", 195,366);
		btn[20]=addButton(panel, "img/box/25x25/pop/21.png", 264,366);
		btn[21]=addButton(panel, "img/box/25x25/pop/22.png", 334,366);

		btn[22]=addButton(panel, "img/box/25x25/pop/23.png", 162,427);
		btn[23]=addButton(panel, "img/box/25x25/pop/24.png", 230,427);
		btn[24]=addButton(panel, "img/box/25x25/pop/25.png", 300,427);
		
		field=addTextField(panel, "", 210, 102,80,38);
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