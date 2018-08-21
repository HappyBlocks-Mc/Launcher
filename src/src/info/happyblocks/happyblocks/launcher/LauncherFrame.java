//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE

package info.happyblocks.happyblocks.launcher;
import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	private static CrashReporter crashReporter;

	
	public LauncherFrame() {
		this.setTitle("HappyBlocks Launcher");
		this.setSize(983, 577);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setIconImage(Swinger.getResource("favicon.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());

	
       WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
	    this.addMouseMotionListener(mover);
	   
		this.setVisible(true);
		
		
		
	}

	public static void main(String[] args) {
		LanguageManager.setLang(LanguageManager.FRENCH);
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/info/happyblocks/happyblocks/launcher/resources/");
		Launcher.HB_CRASHES_DIR.mkdirs();
		crashReporter = new CrashReporter("HappyBlocks Launcher", Launcher.HB_CRASHES_DIR);
		instance = new LauncherFrame();
	}

	public static  LauncherFrame getInstance() {
		return  instance;
		
	}
	public static CrashReporter getCrashReporter() {
		return  crashReporter;
		
	}
	public LauncherPanel getLauncherPanel() {
		return this.launcherPanel;
	}
	
}
//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE