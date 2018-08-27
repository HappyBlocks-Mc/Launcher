//HappyBlocks Server, MOJANG AB, FORGE, LITARVAN, VERSION OFFICIELLE

package info.happyblocks.launcher;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
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
		AWTUtilities.setWindowOpacity(this, 0.0F);
	
		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
	    this.addMouseMotionListener(mover);
	   
		this.setVisible(true);
		
		Animator.fadeInFrame(this, Animator.FAST);
	}

	public static void main(String[] args) {
		LanguageManager.setLang(LanguageManager.FRENCH);
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/info/happyblocks/launcher/resources/");
		Launcher.HB_CRASHES_DIR.mkdirs();
		crashReporter = new CrashReporter("HappyBlocks Launcher", Launcher.HB_CRASHES_DIR);
		instance = new LauncherFrame();
		//Discord RPC add
		DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "483343162568605706";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        //Start of RPC details
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = "Dans le launcher...";
        presence.smallImageKey = "bootstrap";
        presence.largeImageKey = "favicon";
        presence.smallImageText = "Dans le launcher...";
        //End of RPC details
        lib.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
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