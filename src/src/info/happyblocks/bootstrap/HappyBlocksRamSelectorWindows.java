package info.happyblocks.bootstrap;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.ramselector.AbstractOptionFrame;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;

public class HappyBlocksRamSelectorWindows extends AbstractOptionFrame implements ActionListener
{
	
	private JLabel ramLabel;
	private JComboBox<String> ramBox;
	private JButton ok;
	
	public HappyBlocksRamSelectorWindows(RamSelector selector)
	{
		super(selector);
		
		this.setTitle("Ram");
		this.setResizable(false);
		this.setSize(275, 100);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setIconImage(Swinger.getResource("favicon.png"));
		this.setUndecorated(false);
		this.setBackground(Color.ORANGE);
		this.getContentPane().setBackground(Color.ORANGE);
		this.setDefaultCloseOperation(1);

		ramLabel = new JLabel(LanguageManager.lang("Ram alloué :"));
		ramLabel.setBounds(8, 20, 90, 25);
		this.add(ramLabel);
		
		ramBox = new JComboBox<String>(RamSelector.RAM_ARRAY);
		ramBox.setBounds(80, 20, 125, 25);
		this.add(ramBox);
		
		ok = new JButton("Ok");
		ok.addActionListener(this);
		ok.setBounds(210, 20, 55, 25);
		this.add(ok);
		
	}

		@Override
		public void setSelectedIndex(int index) { ramBox.setSelectedIndex(index); }
		
		@Override
		public int getSelectedIndex() { return ramBox.getSelectedIndex(); }
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			this.setVisible(false);
			getSelector().save();
			try  {
				HappyBlocksBootstrap.launchLauncher();
				
				} 
			catch (LaunchException ex)
			{
					HappyBlocksBootstrap.HB_B_REPORTER.catchError(ex, "Impossible de lancer le launcher !");
				}
		}
}
