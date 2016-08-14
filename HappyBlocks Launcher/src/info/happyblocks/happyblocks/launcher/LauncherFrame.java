//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE

package info.happyblocks.happyblocks.launcher;
import static fr.theshark34.swinger.Swinger.getResource;

import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	private static SplashScreen splash;
	
	public LauncherFrame() {
		this.setTitle("HappyBlocks Launcher");
		this.setSize(983, 577);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setIconImage(Swinger.getResource("favicon.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());

	
       WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
	    this.addMouseMotionListener(mover);
	   
		this.setVisible(true);
		
		
		
	}

	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/info/happyblocks/happyblocks/launcher/resources/");
		Launcher.HB_CRASHES_DIR.mkdirs();
		
		instance = new LauncherFrame();
	}

	public static  LauncherFrame getInstance() {
		return  instance;
		
	}
	public LauncherPanel getLauncherPanel() {
		return this.launcherPanel;
	}
	
}
//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE