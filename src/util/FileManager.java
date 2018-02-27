package util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 *
 * @author rajitha
 */
public class FileManager {

    public String chooseFile() {
        JFrame frame = new JFrame("Crypi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String userhome = System.getProperty("user.home");
        JFileChooser chooser = new JFileChooser(userhome);
        chooser.setDialogTitle("Select Text File");
        chooser.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return (String) chooser.getSelectedFile().getPath(); //return selected folder destination
        } else {
            return null; //No selection
        }

    }

    public String selectFolder() {
        JFrame frame = new JFrame("Crypi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String userhome = System.getProperty("user.home");
        JFileChooser chooser = new JFileChooser(userhome);
        chooser.setDialogTitle("Select Folder");
        chooser.setApproveButtonText("Select");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return (String) chooser.getSelectedFile().getPath(); //return selected folder destination
        } else {
            return null; //No selection
        }
    }

    public BufferedReader readTextFile(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader textBuffer = new BufferedReader(fileReader);

        return textBuffer;
    }

    public void writeTextFile(String fileName, String content, boolean append) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, append);
        PrintWriter printLine = new PrintWriter(fileWriter);
        printLine.print(content);
        printLine.close();
    }
}
