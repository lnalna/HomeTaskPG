package HomeTaskPG;

/*
 класс вывода результатов  в файл
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputToFileUtil {

    public static void outputToFile(String outputFile, ArrayList<String> outputArayList) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(outputFile);
            bufferedWriter = new BufferedWriter(fileWriter);

            for ( int i = 0; i< outputArayList.size(); i++) {

                bufferedWriter.write(outputArayList.get(i));
            }


        } catch (IOException e) {

            e.printStackTrace();
        }
        finally {

            try {
                bufferedWriter.close();
                fileWriter.close();

            }catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
