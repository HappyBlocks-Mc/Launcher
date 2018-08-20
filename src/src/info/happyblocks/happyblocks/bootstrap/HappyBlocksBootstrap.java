//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE

package info.happyblocks.happyblocks.bootstrap;
import static fr.theshark34.swinger.Swinger.getResource;
import static fr.theshark34.swinger.Swinger.getTransparentWhite;

import java.io.File;
import java.io.IOException;

import fr.theshark34.openlauncherlib.bootstrap.Bootstrap;
import fr.theshark34.openlauncherlib.bootstrap.LauncherClasspath;
import fr.theshark34.openlauncherlib.bootstrap.LauncherInfos;
import fr.theshark34.openlauncherlib.util.ErrorUtil;
import fr.theshark34.openlauncherlib.util.GameDir;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

public class HappyBlocksBootstrap {
	
	private static SplashScreen splash;
	private static SColoredBar bar;
	private static Thread barThread;

	private static final LauncherInfos HB_B_INFOS = new LauncherInfos("HappyBlocks Bootstrap", "info.happyblocks.happyblocks.launcher.LauncherFrame");
	private static final File HB_DIR = GameDir.createGameDir("HappyBlocks");
	private static final LauncherClasspath HB_B_CP = new LauncherClasspath(new File(HB_DIR, "launcher/launcher.jar"), new File(HB_DIR, "launcher/libs"));
	
	private static ErrorUtil errorUtil = new ErrorUtil(new File(HB_DIR, "launcher/crashes/"));
	
	public static void main(String[] args) {
		Swinger.setResourcePath("/info/happyblocks/happyblocks/bootstrap/resources/");
		displaySplash();
		try {
			

		doUpdate();
		} catch (Exception e) {
			errorUtil.catchError(e, "Impossible de mettre à jour le launcher !");
			barThread.interrupt();
			
		}
		
		try  {
		launchLauncher();
		} catch (IOException e) {
			errorUtil.catchError(e, "Impossible de lancer le launcher !");
		}
	}
	
	private static void displaySplash() {
		splash = new SplashScreen("HappyBlocks", getResource("splash.png"));
		splash.setBackground(Swinger.TRANSPARENT);
		splash.getContentPane().setBackground(Swinger.TRANSPARENT);
		
		bar = new SColoredBar(getTransparentWhite(75), getTransparentWhite(125));
		bar.setBounds(312, 312, 330, 70);
		splash.add(bar);
		
		splash.setVisible(true);
		splash.setIconImage(Swinger.getResource("favicon.png"));
		
	}
	
	private static void doUpdate() throws Exception {
		SUpdate su = new SUpdate("http://bootstrap.happyblocks.info", new File(HB_DIR, "launcher"));
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
			
		
	private static void launchLauncher() throws IOException {
		Bootstrap bootstrap = new Bootstrap(HB_B_CP, HB_B_INFOS);
		Process p = bootstrap.launch();
		
		splash.setVisible(false);
		
		try {
		p.waitFor();
		} catch (InterruptedException e) {
   		}
		System.exit(0);
	}
	
}

//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE
