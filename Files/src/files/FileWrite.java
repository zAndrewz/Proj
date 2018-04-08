/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author lean
 */
public class FileWrite {

    public static void writte(String text, List<File> files, String tofile) throws IOException {
        String listfile = new String();
        File myFile = new File(tofile);
        String[] arrtext = text.split("\\n");
        for (File s : files) {
            listfile += s.getName() + "\n";
        }
        String[] arrfile = listfile.split("\\n");
        //     System.out.println(arrfile.length);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));
            writer.append("List of files > \r\n");
            for (int i = 0; i < arrfile.length; i++) {
                writer.write(arrfile[i]);
                writer.append("\r\n");
            }
            writer.append("Text from files > \r\n");
            for (int i = 0; i < arrtext.length; i++) {
                writer.write(arrtext[i]);
                writer.append("\r\n");
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
