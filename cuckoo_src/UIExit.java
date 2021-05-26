import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
public class UIExit extends generalPanel{
	private JPanel pane;
	private JLabel bg;
	private String back[]={"exit","back2menu","new", "corrupt","invalid", "win", "saveError", "error", "solved", "filename", "reset", "exceed", "done"};
	protected JButton yes, no, okay;
	protected int num;
	UIExit(JPanel pane, int num){
		this.pane=pane;
		this.num=num;
		pane.setOpaque(true);
		yes=addButton(pane, "img/exit/yes.png", "img/exit/h_yes.png",276,336);
		no=addButton(pane, "img/exit/no.png", "img/exit/h_no.png",457,337);
		if(num!=6) //invalid sudoku
			okay=addButton(pane, "img/exit/okay.png", "img/exit/h_okay.png",354,337);
		else
			okay=addButton(pane, "img/exit/okay.png", "img/exit/h_okay.png",353,416);
		if(num!=7 && num!=8)
			bg=addLabel(pane,"img/bg/"+back[num]+".png",100,174);
		else{
			okay.setLocation(352,340);
			bg=addLabel(pane,"img/bg/"+back[num]+".png",100,100);
			}
		yes.setVisible(num<3 || num==9 || num==10);
		no.setVisible(yes.isVisible());
		okay.setVisible(!yes.isVisible());
		pane.setVisible(true);
		}
	protected void decompose(){
		pane.removeAll();
		bg=null;
		yes=no=okay=null;
		}
	}