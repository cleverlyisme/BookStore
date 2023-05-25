package gui_design;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;


public class ImageIconHelper {
	public ImageIcon getIcon(URL url, int w, int h) {
		ImageIcon icon = new ImageIcon(url);
		icon = new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		return icon;
	}
}
