package framekwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SelectedFileReader {

    public static ArrayList<MailAccountMap> read(String filePath, String separator) {
        ArrayList<MailAccountMap> list = new ArrayList<>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = separator;
        if (cvsSplitBy.isEmpty()) {
            cvsSplitBy = ",";
        }

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] lineArray = line.split(cvsSplitBy);
                MailAccount from = new MailAccount(lineArray[0], lineArray[1], lineArray[2], lineArray[3], lineArray[4]);
                MailAccount to = new MailAccount(lineArray[5], lineArray[6], lineArray[7], lineArray[8], lineArray[9]);
                MailAccountMap map = new MailAccountMap(from, to);
                list.add(map);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public static ArrayList<MailAccountMap> read(File file, String separator, boolean headerRow) {
        System.out.println("Chosen file: " + file.getName() + " with separator: " + separator);

        ArrayList<MailAccountMap> list = new ArrayList<>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = separator;
        if (cvsSplitBy.isEmpty()) {
            cvsSplitBy = ",";
        }
        
        try {
            br = new BufferedReader(new FileReader(file));
            if (headerRow)
                br.readLine(); // this will read the first line
            
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] lineArray = line.split(cvsSplitBy);

                if (lineArray.length > 8) {
                    MailAccount from = new MailAccount(lineArray[0], lineArray[1], lineArray[2], lineArray[3], lineArray[4]);
                    MailAccount to = new MailAccount(lineArray[5], lineArray[6], lineArray[7], lineArray[8], lineArray[9]);
                    MailAccountMap map = new MailAccountMap(from, to);
                    list.add(map);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}
