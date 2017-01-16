/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import paths.Paths;

/**
 *
 * @author Fabian
 */
public class Prices {
    
    public static void read() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(new File(Paths.FILE_PRICES)));
        
        String line = reader.readLine();
        
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
    }
    
}
