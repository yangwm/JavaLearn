package zip;

import java.io.*;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * javaqx.crypto包的CipherInputStream/CipherOutputStream
 * 
 * @author yangwm in Feb 12, 2009 12:21:24 AM
 */
public class Zip {
	private static Log logger = LogFactory.getLog(Zip.class);
	
    public Zip() {
        super();
    }

    public static void doZip(String path) throws FileNotFoundException,
            IOException {
        String zipPath = path.substring(0, path.lastIndexOf("/") + 1);
        String entryFileName = path.substring(path.lastIndexOf("/") + 1);
        String fileName = path.substring(path.lastIndexOf("/") + 1, path
                .lastIndexOf("."));

        String zipFileName = fileName + ".zip";
        String zipFullName = zipPath + zipFileName;

        byte[] data = new byte[1024 * 2];
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(zipFullName);
        ZipOutputStream zipOS = new ZipOutputStream(fos);
        zipOS.setMethod(ZipOutputStream.DEFLATED);
        zipOS.putNextEntry(new ZipEntry(entryFileName));
        int length = 0;
        while ((length = fis.read(data)) != -1) {
            zipOS.write(data, 0, length);
        }
        zipOS.finish();
        zipOS.close();
        fos.close();
        fis.close();
    }
    
    /**
     * file Encrypt and zip 
     * 
     * create by yangwm in Feb 10, 2009 6:06:22 PM
     * @param filePath      file name 
     * @param password      encrypt password
     * @throws IOException
     */
    public static void doEncript(String filePath, String password) throws IOException {
    	logger.debug("doEncript(" + filePath + ", " + password + ")");
    	String softPath = Zip.class.getResource("").getFile() + "encript/7z";
    	
    	String targetPath = filePath;
    	if (targetPath.lastIndexOf(".") != -1) {
    		targetPath = targetPath.substring(0, targetPath.lastIndexOf(".")) + ".zip";
    	}
        String cmd = softPath + " a -tzip \"" + targetPath + "\" \"" + filePath + "\"";
        if (password != null && !"".equals(password)) {
            cmd += " -p" + password;
        }
        
        logger.debug("doEncript cmd=" + cmd);
        Runtime.getRuntime().exec(cmd);
    }
    
    /**
     * file Decript and zip 
     * 
     * create by yangwm in Feb 10, 2009 6:06:22 PM
     * @param filePath      file name 
     * @param password      encrypt password
     * @throws IOException
     */
    public static void doDecript(String filePath, String password) throws IOException {
        logger.debug("doEncript(" + filePath + ", " + password + ")");
        String softPath = Zip.class.getResource("").getFile() + "encript/7z";
        
        String targetPath = filePath;
        if (targetPath.lastIndexOf("/") != -1) {
            targetPath = targetPath.substring(0, targetPath.lastIndexOf("/"));
        }
        String cmd = softPath + " x \"" + filePath + "\" -o\"" + targetPath + "\"";
        if (password != null && !"".equals(password)) {
            cmd += " -p" + password;
        }
        
        logger.debug("doEncript cmd=" + cmd);
        Runtime.getRuntime().exec(cmd);
    }


    public static List<String> unZip(String path) throws FileNotFoundException,
            IOException {
        path = path.replaceAll("\\\\", "/");
        String zipPath = path.substring(0, path.lastIndexOf("/") + 1);
        List<String> xmlFileNameList = new ArrayList<String>();
        byte[] data = new byte[1024 * 2];
        FileInputStream fis = new FileInputStream(path);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry = null;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                File file = new File(zipPath + entry.getName());
                file.mkdirs();
                continue;
            }
            FileOutputStream fos = new FileOutputStream(zipPath
                    + entry.getName());
            int length = 0;
            while ((length = zis.read(data)) != -1) {
                fos.write(data, 0, length);
            }
            zis.closeEntry();
            fos.flush();
            fos.close();
            xmlFileNameList.add(zipPath + entry.getName());
        }
        zis.close();
        fis.close();
        return xmlFileNameList;
    }

    public static void destroyFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {
        try {
            //Zip.doZip("d:/working/amslogs/ams.log_2009-01-14.log");
            Zip.doEncript("d:/working/amslogs/ams.log_2009-01-14.log", "123456");
        } catch (FileNotFoundException ex) {
        	logger.error(ex);
            //ex.printStackTrace();
        } catch (IOException ex) {
        	logger.error(ex);
            //ex.printStackTrace();
        }
    }
}
