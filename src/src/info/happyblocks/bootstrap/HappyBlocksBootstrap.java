//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE

package info.happyblocks.bootstrap;
import static fr.theshark34.swinger.Swinger.getResource;
import static fr.theshark34.swinger.Swinger.getTransparentWhite;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.sun.awt.AWTUtilities;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;

public class HappyBlocksBootstrap {
	
	private static final File HB_DIR = new File(GameDirGenerator.createGameDir("HappyBlocks"), ("launcher"));	
	static final CrashReporter HB_B_REPORTER = new CrashReporter("HappyBlocks Bootstrap", new File(HB_DIR, "launcher/crashes/"));
	
	private static RamSelector selector = new RamSelector(new File (HB_DIR, "ram.txt"));
	private static SplashScreen splash;
	private static SColoredBar bar;

	
	private static Thread barThread;
	private static JLabel infoLabel = new JLabel("Update...", SwingConstants.CENTER);
	public static void main(String[] args) {
		LanguageManager.setLang(LanguageManager.FRENCH);
		Swinger.setResourcePath("/info/happyblocks/bootstrap/resources/");
		displaySplash();
		try {
			

		doUpdate();
		} catch (Exception e) {
			if(barThread != null)
				barThread.interrupt();
			
			HB_B_REPORTER.catchError(e, "Impossible de mettre à jour le launcher !");
			
		}
		selector.setFrameClass(HappyBlocksRamSelectorWindows.class );
		selector.display();
		

	}
	
	private static void displaySplash() {
		
		splash = new SplashScreen("HappyBlocks Bootstrap", getResource("splash.png"));
		splash.setBackground(Swinger.TRANSPARENT);
		splash.setIconImage(Swinger.getResource("favicon.png"));
		splash.setLayout(null);
		AWTUtilities.setWindowOpacity(splash, 0.0F);
		
		bar = new SColoredBar(getTransparentWhite(75), getTransparentWhite(125));
		bar.setBounds(0, 520, 630, 40);
		splash.add(bar);
		
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setFont(infoLabel.getFont().deriveFont(20F));
		infoLabel.setBounds(0, 570, 630, 40);
		splash.add(infoLabel);
		
		splash.setVisible(true);
		Animator.fadeInFrame(splash, 1);
	}
	
	static void doUpdate() throws Exception {
		SUpdate su = new SUpdate("http://bootstrap.happyblocks.info", new File(HB_DIR, ""));
		barThread = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				while(!this.interrupted()) {
					bar.setValue((int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000));
					bar.setMaximum((int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000));
				}
				
			}
			
		};
		barThread.start();
		
		su.start();
		barThread.interrupt();
		
	}
			
		
	static void launchLauncher() throws LaunchException {
		
		ClasspathConstructor constructor = new ClasspathConstructor();
		ExploredDirectory gameDir = Explorer.dir(HB_DIR);
		constructor.add(gameDir.sub("launcher_lib").allRecursive().files().match("^(.*\\.((jar)$))*$"));
		constructor.add(gameDir.get("launcher.jar"));
		ExternalLaunchProfile profile = new ExternalLaunchProfile("info.happyblocks.launcher.LauncherFrame", constructor.make());
		profile.setVmArgs(Arrays.asList(selector.getRamArguments()));
		
		ExternalLauncher launcher = new ExternalLauncher(profile);
		
		Process p = launcher.launch();
		
		splash.setVisible(false);
		
		try {
		p.waitFor();
		} catch (InterruptedException ignored) {
   		}
		Animator.fadeOutFrame(splash, Animator.FAST);
		System.exit(0);
	}
	
}

//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE
