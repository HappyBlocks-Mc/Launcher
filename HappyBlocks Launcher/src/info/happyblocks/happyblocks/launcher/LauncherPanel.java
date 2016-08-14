//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE

package info.happyblocks.happyblocks.launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.launcher.util.UsernameSaver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener {
	
	private Image background  = Swinger.getResource("Background.png");
	
	private UsernameSaver saver = new UsernameSaver(Launcher.HB_INFOS);
	private JTextField usernameField = new JTextField(saver.getUsername(""));
	private JPasswordField passwordField = new JPasswordField();	
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
	private STexturedButton hideButton = new STexturedButton(Swinger.getResource("quit.png"));
	private STexturedButton quitButton = new STexturedButton(Swinger.getResource("reduce.png"));

	
	private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentWhite(175));
	private JLabel infoLabel = new JLabel("Cliquez sur jouer !", SwingConstants.CENTER);
	
	
	public LauncherPanel() {
		this.setLayout(null);
		
		usernameField.setForeground(Color.WHITE);
		usernameField.setFont(usernameField.getFont().deriveFont(20F));
		usernameField.setCaretColor(Color.WHITE);
		usernameField.setBorder(null);
		usernameField.setOpaque(false);
		usernameField.setBounds(591, 188, 245, 50);
		this.add(usernameField);
		
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(usernameField.getFont());
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setBounds(592, 300, 241, 50);
		this.add(passwordField);
		
		playButton.setBounds(625, 391);
		playButton.addEventListener(this);
		this.add(playButton);
		
		progressBar.setBounds(0, 534, 978, 18);
		this.add(progressBar);
		
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setFont(usernameField.getFont());
		infoLabel.setBounds(0, 512, 976, 22);
		this.add(infoLabel);
		
		quitButton.setBounds(925, 18);
		quitButton.setSize(25, 25);
		//this.add(quitButton);
		
		hideButton.setBounds(950, 18);
		hideButton.setSize(25, 25);
		//this.add(hideButton);


	}
	
	@SuppressWarnings("deprecation")
	@Override
   public void onEvent(SwingerEvent e) {
	   if(e.getSource() == playButton) {
			System.out.println("Bouton 'Jouer' cliquer");

			
			setFieldsEnabled(false);
			
			if(usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() ==0) {	
				JOptionPane.showMessageDialog(this, "Erreur, veuiller entrer un pseudo et un mot de passe valides.", "Erreur 01", JOptionPane.ERROR_MESSAGE);
				setFieldsEnabled(true);
				return;
			}
			
            Thread t = new Thread() {
                @Override
                public void run() {
                	try {
	                Launcher.auth(usernameField.getText(), passwordField.getText());
                	} catch (AuthenticationException e) {
                		JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de se connecter :" + e.getErrorModel().getErrorMessage(), "Erreur 02", JOptionPane.ERROR_MESSAGE);
        				setFieldsEnabled(true);
        				return;
        				
                	}
                	
                	saver.setUsername(usernameField.getText());
                	
                	try {
    	                Launcher.update();
                    	} catch (Exception e) {
            				Launcher.interruptThread();
            				Launcher.getErrorUtil().catchError(e, "Impossible de mettre a jour le launcher !");
                    		
                    	}
                	
                	try {
    	                Launcher.launch();
                    	} catch (IOException e) {
                    		Launcher.getErrorUtil().catchError(e, "Impossible de lancer le jeu !");
                    	}
                	
                	System.out.println("Connection r�ussi");
                }
            };
            t.start();
		}
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	private void setFieldsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		playButton.setEnabled(enabled);
	}
	
	public SColoredBar getProgressBar() {	
		return progressBar;
	}
	
	public void setInfoText(String text) {
		
		infoLabel.setText(text);
	}
}

//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE