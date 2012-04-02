package basics.essential.ios;
// args:newYwmFile.txt

import java.io.File;
import java.io.IOException;
import static java.lang.System.out;

public class FileStuff {
    public static void main(String args[]) throws IOException {

        out.print("File system roots: ");
        for (File root : File.listRoots()) {
            out.format("%s ", root);
        }
        out.println();
            
        for (String fileName : args) {
            listFile(fileName);
        }
    }
    
    public static File listFile(String fileName) throws IOException {
        out.format("%n------%nnew File(%s)%n", fileName);
        File f = new File(fileName);
        listFile(f);
        return f;
    }
    
    public static File listFile(File f) throws IOException {
        out.format("%n------%n");
        out.format("toString(): %s%n", f);
        out.format("exists(): %b%n", f.exists());
        out.format("lastModified(): %tc%n", f.lastModified());
        out.format("isFile(): %b%n", f.isFile());
        out.format("isDirectory(): %b%n", f.isDirectory());
        out.format("isHidden(): %b%n", f.isHidden());
        out.format("canRead(): %b%n", f.canRead());
        out.format("canWrite(): %b%n", f.canWrite());
        out.format("canExecute(): %b%n", f.canExecute());
        out.format("isAbsolute(): %b%n", f.isAbsolute());
        out.format("length(): %d%n", f.length());
        out.format("getName(): %s%n", f.getName());
        out.format("getPath(): %s%n", f.getPath());
        out.format("getAbsolutePath(): %s%n", f.getAbsolutePath());
        out.format("getCanonicalPath(): %s%n", f.getCanonicalPath());
        out.format("getParent(): %s%n", f.getParent());
        out.format("toURI: %s%n", f.toURI());
        return f;
    }
}

