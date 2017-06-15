package HomeTaskPG;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;



public class Solution {


    public static class ObjectProperties implements Comparable<ObjectProperties> {

        private String objectName;//идентификатор объекта
        private ArrayList<String> parentName = new ArrayList<>();//список родительских объектов
        private HashMap<String, String> paramName = new HashMap<>();//список параметров

        public ObjectProperties() {
        }

        public ObjectProperties(String objectName, ArrayList<String> parentName, HashMap<String, String> paramName) {

            this.objectName = objectName;
            this.parentName = parentName;
            this.paramName = paramName;
        }


        public int compareTo(ObjectProperties o) {

            return this.objectName.compareTo(o.getObjectName());
        }


        //геттеры и сеттеры
        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public ArrayList<String> getParentName() {
            return parentName;
        }

        public void setParentName(ArrayList<String> parentName) {
            this.parentName = parentName;
        }

        public HashMap<String, String> getParamName() {
            return paramName;
        }

        public void setParamName(HashMap<String, String> paramName) {
            this.paramName = paramName;
        }
    }


    public static void main(String[] args) {

        String outputFile;//файл для записи

        ArrayList<String> inputArrayList;//список для чтения строк из файла

        //блок чтения строк из файла
        try {

             inputArrayList =  InputFromFileUtil.inputFromFile(args[0]);

        } catch (FileNotFoundException e) {

            System.out.println("Файл исходных данных не найден!");
            return;

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("Первый параметр программы - файл с исходными данными");
            System.out.println();
            return;

        } catch (IOException e) {

            System.out.println("Ошибка чтения файла");
            return;
        }




        try {
            //файл для записи
            outputFile = args[1];

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("Второй параметр программы - файл обработанных данных");
            System.out.println();
            return;
          }


        //список для хранения объектов
        ArrayList<ObjectProperties> objectPropertiesArrayList = DefineObjectsInitialDataUtil.defineObjectsInitialData(inputArrayList);

        //список объектов упорядочивается по идентификатору
        TreeSet<ObjectProperties> objectPropertiesTreeSetList = new TreeSet<>();

        for (ObjectProperties tempObject : objectPropertiesArrayList)
            objectPropertiesTreeSetList.add(tempObject);


        //список для хранения строк, для вывода в файл
        ArrayList<String> outputArrayList = null;
        outputArrayList = CreateFileContentUtil.createFileContent(DefineObjectsParamsUtil.defineObjectsParams(objectPropertiesTreeSetList));

        //вывод результатов в файл
        OutputToFileUtil.outputToFile(outputFile,outputArrayList);



    }//end main
}
