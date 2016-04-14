package travelexperts;

//Imports, including GUI tools, file input and sound.
import javax.swing.JWindow;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.applet.*;
import java.net.*;

public class Splash extends JWindow
{
	//Creates a background image for the splashscreen.
	Image img=Toolkit.getDefaultToolkit().getImage("./img/splash.png");
	ImageIcon imgicon=new ImageIcon(img);
	
	public static void main(String[]args)
	{
		Splash sp = new Splash();
	}
	
	//Constructor.
	public Splash()
	{
		try
		{
			setSize(960,540);
			setLocationRelativeTo(null);
			setVisible(true);;
			AudioClip otter = Applet.newAudioClip(
					new File("./audio/otter.wav").toURI().toURL());
					otter.play();
			Thread.sleep(6000);
			dispose();
			otter.stop();
			/*javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
			        null,"Welcome", "Welcome Screen:",
			        javax.swing.JOptionPane.DEFAULT_OPTION);	*/
		}
		catch (MalformedURLException murle) {
			System.out.println(murle);
		}
		catch(Exception exception)
		{
			javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
		               null,"Error"+exception.getMessage(), "Error:",
		               javax.swing.JOptionPane.DEFAULT_OPTION);
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(img,0,0,this);
	}
}
