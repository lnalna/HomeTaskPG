package HomeTaskPG;

/*
 класс для чтения строк из входного файла
 */
import java.io.*;
import java.util.ArrayList;



public class InputFromFileUtil {

    public static ArrayList<String> inputFromFile(String fileName) throws IOException, ArrayIndexOutOfBoundsException {


        File inputFile = new File(fileName);//файл для чтения

        ArrayList<String> inputArrayList = new ArrayList<>(); //список для хранения строк входного файла  - inputFile

        FileReader fileReader = new FileReader(inputFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine())!=null) {

               inputArrayList.add(line);
        }

         bufferedReader.close();
         fileReader.close();


        return inputArrayList;
    }
}
