package basics.deployment.applications;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Resources {
    
    public void loadIcon() {
     // Get current classloader
        ClassLoader cl = this.getClass().getClassLoader();
        // Create icons
        Icon saveIcon  = new ImageIcon(cl.getResource("images/save.gif"));
        Icon cutIcon   = new ImageIcon(cl.getResource("images/cut.gif"));
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Resources resource = new Resources();
        resource.loadIcon();
    }

}
