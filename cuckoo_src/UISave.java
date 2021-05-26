import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
public class UISave extends generalPanel{
	private JPanel pane;
	private JLabel bg;
	protected JTextField field;
	protected JButton save, cancel, okay;
	protected int num;
	UISave(JPanel pane){
		this.pane=pane;
		pane.setOpaque(true);
		field=addTextField(pane, "", 370,301,220,40);
		save=addButton(pane, "img/exit/save.png", "img/exit/h_save.png",268,414);
		cancel=addButton(pane, "img/exit/cancel.png", "img/exit/h_cancel.png",425,413);
		bg=addLabel(pane,"img/bg/save.png",100,174);
		pane.setVisible(true);
		}
	protected void decompose(){
		pane.removeAll();
		field=null;
		save=cancel=okay=null;
		bg=null;
		}
	}