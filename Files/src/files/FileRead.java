/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author lean
 */
public class FileRead {

    public static String textLooker(List<File> files) throws IOException {
        String inside = "";
        BufferedReader br;
        String s = "";
        try {
            for (File f : files) {
                FileInputStream fstream = new FileInputStream(f.getAbsolutePath());
                br = new BufferedReader(new InputStreamReader(fstream, "Cp1251"));
                while ((s = br.readLine()) != null) {
                    inside += s + "\n";
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //       System.out.println(inside);
        return inside;
        /**/
    }
    
}
