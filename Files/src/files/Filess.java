/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.*;
import java.util.*;

/**
 *
 * @author lean
 */
public class Filess{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Filefind filefind = new Filefind();
        FileWrite filewrite = new FileWrite();
        FileRead fileread = new FileRead();
        
        List<File> result = new ArrayList<>();
        String directory = "A:\\TestFiles";
        String save = "A://TOR/Result.txt";
        result = filefind.findFiles(directory);
        String text = fileread.textLooker(result);
        filewrite.writte(text, result, save);

    }
 
}


 /* 
        // Sort by path
        String [] reslt = Files.walk(Paths.get(di))
                .filter(Files::isRegularFile)
      //          .map(Path::toFile)
                .collect(Collectors.toList())
                .toString()
                .replaceAll("\\[|\\]", " ")
                .split(",");
        
        Arrays.sort(reslt);
        
        String fi = new String("");
        for (String s : reslt) {
        fi = fi + s + "\n";
        }
       System.out.println(fi);
   */