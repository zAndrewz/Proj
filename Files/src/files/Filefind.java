/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author lean
 */
public class Filefind {
    public static List<File> findFiles(String path) throws IOException {
        // sort by name
        List<File> filesInFol = Files.walk(Paths.get(path))
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .collect(Collectors.toList());
        Collections.sort(filesInFol);
        //       for (File s : filesInFol)
        //           System.out.println(s.getName());
        return filesInFol;
    }
}
