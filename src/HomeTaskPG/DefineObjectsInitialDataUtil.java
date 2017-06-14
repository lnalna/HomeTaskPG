package HomeTaskPG;

/*
В данном классе происходит загрузка объектов из входного списка в список объектов
 */

import HomeTaskPG.Solution.ObjectProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class DefineObjectsInitialDataUtil {

    public static ArrayList<ObjectProperties> defineObjectsInitialData (ArrayList<String> inputArrayList) {

        HashMap<String,String> tempHashMap = new HashMap<>();//HashMap для параметров
        ArrayList<String> tempArrayList = new ArrayList<>();//ArrayList для классов-родителей
        ArrayList<ObjectProperties> objectPropertiesArrayList = new ArrayList<>();//список для хранения объектов


        /*
        В данном цикле происходит создание списка объектов objectPropertiesArrayList.

         */
        for(String tempString : inputArrayList) {

/*
            В цикле поочередно проверяется каждая строка из списка. По результатам проверки создается
            объект, который помещается в список объектов.
             */

            //временный объект
            ObjectProperties objectPropertiesTemp = new ObjectProperties();

            /*
                Подсчет количества символов определенного типа в строке
             */
            int countCommas =0;//количество запятых
            int countEqualSign=0;//количество знаков равно

            for(char element : tempString.toCharArray()){

                if (element==',') countCommas++;
                if (element=='=') countEqualSign++;
            }

            if(tempString.indexOf(":")>0) {//если исходная строка содержит двоеточие : значит объект с параметрами

                if ( !tempString.contains("(") && !tempString.contains(",")  && countEqualSign==1 ) {
                    /*
                    1.1. У объекта есть идентификатор, нет родителей, есть один параметр
                    это значит в строке нет скобки и нет запятой, есть один знак "="
                    Пример: obj1 : param1 = 1
                     */

                    String[] tempArray = tempString.split(":");//разбиваем на две строки до двоеточия и после
                    String objectIdent = tempArray[0].trim();//убираем лишние пробелы
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта

                    String[] tempArray2 = tempArray[1].split("=");//обрабатываем единственный параметр
                    tempHashMap.put(tempArray2[0].trim(),tempArray2[1].trim());

                    for(Map.Entry<String,String> map :tempHashMap.entrySet()){

                        objectPropertiesTemp.getParamName().put(map.getKey(),map.getValue());
                    }

                    //очищаем список
                    tempHashMap.clear();

                    //добавляем объект в список объектов
                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(),objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                    continue;

                }

                if ( !tempString.contains("(") && countCommas>=1  && countEqualSign>=2 ){
                    /*
                    1.2.У объекта есть идентификатор, нет родителей, есть более одного параметра
                        это значит в строке нет скобки, есть минимум одна запятая, есть больше или равно 2 знаков "="
                        Пример: obj1 : param1 = 1 , param2 = 2 , ... , paramN = N
                     */
                    String[] tempArray = tempString.split(":");//разбиваем на две строки до двоеточия и после
                    String objectIdent = tempArray[0].trim();//убираем лишние пробелы
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта

                    //обрабатываем параметры
                    for(String temp : tempArray[1].split(",")){

                        String[] tempArray2 = temp.split("=");
                        tempHashMap.put(tempArray2[0].trim(),tempArray2[1].trim());
                    }

                    for (Map.Entry<String,String> map :tempHashMap.entrySet()){

                        objectPropertiesTemp.getParamName().put(map.getKey(),map.getValue());
                    }

                    //очищаем список
                    tempHashMap.clear();

                    //добавляем объект в список объектов
                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(),objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                    continue;

                }

                if ( tempString.contains("(") && !tempString.contains(",")  && countEqualSign==1 ) {
                    /*
                    1.3. У объекта есть идентификатор, есть один  родитель, есть один параметр
                    это значит в строке есть скобка, нет запятой, есть один знак "="
                    Пример : obj1 ( obj2 ) : param1 = 1
                     */
                    String[] tempArray = tempString.split("\\(");//разбиваем по открывающей скобки объекта-родителя
                    String objectIdent = tempArray[0].trim();//убираем пробелы, получаем идентификатор объекта
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта

                    //находим объект-родитель
                    String[] tempArray2 = tempArray[1].split("\\)");//убираем закрывающую скобку
                    tempArrayList.add(tempArray2[0].trim());
                    objectPropertiesTemp.getParentName().add(tempArrayList.get(0));
                    //находим параметр
                    String[] tempArray3 = tempArray2[1].split("\\:");//убираем двоеточие, остается один параметр
                    String[] tempArray4 = tempArray3[1].split("=");
                    tempHashMap.put(tempArray4[0].trim(),tempArray4[1].trim());

                    for (Map.Entry<String, String> map : tempHashMap.entrySet()) {

                        objectPropertiesTemp.getParamName().put(map.getKey(), map.getValue());
                    }
                    //очищаем списки
                    tempArrayList.clear();
                    tempHashMap.clear();

                    //добавляем объект в список объектов
                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(), objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                    continue;

                }

                if ( tempString.contains("(") && countCommas>=1  && countEqualSign>=2 ) {
                    /*
                    1.4. У объекта есть идентификатор, есть один родитель, есть больше одного параметра
                    значит у объекта есть скобка, есть минимум одна запятая, знаков равно >=2
                    нет запятой в левой части строки между объектами-родителями

                    Пример : obj1 ( obj2 ) : param1 = 1, param2 = 2, ... , paramN = N
                     */
                    String[] tempArray = tempString.split("\\(");//разбиваем по открывающей скобке объекта-родителя
                    String objectIdent = tempArray[0].trim();//убираем пробелы, получаем идентификатор объекта
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта
                    //находим объект-родитель
                    String[] tempArray2 = tempArray[1].split("\\)");//убираем закрывающую скобку

                    if( !tempArray2[0].contains(",") ) {//нет запятой в левой части т.к. объект-родитель всего один


                        tempArrayList.add((tempArray2[0].trim()));
                        objectPropertiesTemp.getParentName().add((tempArrayList.get(0)));
                        //находи параметры
                        String[] tempArray3 = tempArray2[1].split("\\:");

                        for (String temp : tempArray3[1].split(",")) {//сохраняем параметры

                            String[] tempArray4 = temp.split("=");
                            tempHashMap.put(tempArray4[0].trim(), tempArray4[1].trim());
                        }

                        //сохраняем параметры во временный объект
                        for (Map.Entry<String, String> map : tempHashMap.entrySet()) {

                            objectPropertiesTemp.getParamName().put(map.getKey(), map.getValue());
                        }
                        //очищаем списки
                        tempArrayList.clear();
                        tempHashMap.clear();
                        //сохраняем объект в список
                        objectPropertiesArrayList.add(
                                new ObjectProperties(objectPropertiesTemp.getObjectName(), objectPropertiesTemp.getParentName(),
                                        objectPropertiesTemp.getParamName()));

                        objectPropertiesTemp = null;

                        continue;

                    }
                }

                if ( tempString.contains("(") && countCommas>=1  && countEqualSign==1 ) {
                    /*
                    1.5. У объекта есть идентификатор, есть больше одного родителя, есть один параметр
                    значит есть скобка,есть минимум одна запятая и один знак равно "="
                    Пример : obj1 ( obj2 , obj3 , ... , objN ) : param1 = 1
                     */
                    String[] tempArray = tempString.split("\\(");//разбиваем по открывающей скобке объектов-родителей
                    String objectIdent = tempArray[0].trim();//убираем пробелы, получаем идентификатор объекта
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта


                    String[] tempArray2 = tempArray[1].split("\\)");

                    //находим всех родителей класса
                    for(String temp : tempArray2[0].split(",")){

                        tempArrayList.add(temp.trim());
                    }

                    //сохраняем родителей в объект
                    for(String temp : tempArrayList){

                        objectPropertiesTemp.getParentName().add(temp);
                    }

                    //находим параметр
                    String[] tempArray3 = tempArray2[1].split("\\:");
                    String[] tempArray4 = tempArray3[1].split("=");
                    tempHashMap.put(tempArray4[0].trim(),tempArray4[1].trim());

                    for (Map.Entry<String, String> map : tempHashMap.entrySet()) {

                        objectPropertiesTemp.getParamName().put(map.getKey(), map.getValue());
                    }
                    //очищаем списки
                    tempArrayList.clear();
                    tempHashMap.clear();

                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(), objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                    continue;

                }

                if ( tempString.contains("(") && countCommas>=1  && countEqualSign>=2 ) {
                    /*
                    1.6. У объекта есть идентификатор, есть больше одного родителя, есть больше одного параметра
                    значит есть скобка, есть больше одной запятой, знаков равно >=2, есть запятая в левой части
                    строки между объектами-родителями

                    Пример : obj1 ( obj2 , obj3 , ... , objN ) : param1 = 1 , param2 = 2 , ... , paramN = N
                     */

                    String[] tempArray = tempString.split("\\(");
                    String objectIdent = tempArray[0].trim();//убираем пробелы, получаем идентификатор объекта
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта


                    String[] tempArray2 = tempArray[1].split("\\)");

                    if( tempArray2[0].contains(",") ) {//если у объекта несколько родителей, то в левой части есть запятая

                        //находим всех родителей класса
                        for (String temp : tempArray2[0].split(",")) {

                            tempArrayList.add(temp.trim());
                        }

                        //сохраняем родителей в объект
                        for (String temp : tempArrayList) {

                            objectPropertiesTemp.getParentName().add(temp);
                        }

                        String[] tempArray3 = tempArray2[1].split("\\:");


                        for (String temp : tempArray3[1].split(",")) {//сохраняем параметры

                            String[]  tempArray4 = temp.split("=");
                            tempHashMap.put(tempArray4[0].trim(), tempArray4[1].trim());
                        }

                        //сохраняем параметры во временный объект
                        for (Map.Entry<String, String> map : tempHashMap.entrySet()) {

                            objectPropertiesTemp.getParamName().put(map.getKey(), map.getValue());
                        }

                        //очищаем списки
                        tempArrayList.clear();
                        tempHashMap.clear();
                        //сохраняем объект в список
                        objectPropertiesArrayList.add(
                                new ObjectProperties(objectPropertiesTemp.getObjectName(), objectPropertiesTemp.getParentName(),
                                        objectPropertiesTemp.getParamName()));

                        objectPropertiesTemp = null;

                        continue;

                    }
                }



            } else { //исходная строка не содержит двоеточие: значит объект без параметров

                if ( !tempString.contains("(") && !tempString.contains(",")) {
                   /*в tempString нет скобки и нет запятой
                   2.1. У объекта есть идентификатор и все

                   Пример : obj1
                   */
                    String objectIdent = tempString.trim();
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта
                    //сохраняем объект в список
                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(),objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                    continue;

                }

                if ( tempString.contains("(") && !tempString.contains(",")) {
                    /*в tempString есть скобка и нет запятой
                      2.2. У объекта есть идентификатор и один родитель

                      Пример : obj1 ( obj2 )

                    */

                    String[] tempArray = tempString.split("\\("); //разбиваем по скобке
                    String objectIdent = tempArray[0].trim();
                    objectPropertiesTemp.setObjectName(objectIdent);//записываем идентификатор объекта

                    String[] tempArray2 = tempArray[1].split("\\)");//отделяем имя объекта-родителя

                    tempArrayList.add(tempArray2[0].trim());//записываем имя объекта-родителя

                    //сохраняем имя объекта-родителя в объект
                    objectPropertiesTemp.getParentName().add(tempArrayList.get(0));

                    //очищаем список
                    tempArrayList.clear();

                    //добавляем объект в список объектов
                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(),objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                    continue;

                }

                if ( tempString.contains("(") && tempString.contains(",")) {
                    /*
                       в tempString есть скобка и запятая
                       2.3. У объекта есть идентификатор и более одного родителя

                       Пример : obj1 ( obj2 , obj3, ... , objN )

                     */
                    String[] tempArray = tempString.split("\\(");//разбиваем по скобке
                    String objectIdent = tempArray[0].trim();//убираем пробелы, получаем идентификатор объекта
                    objectPropertiesTemp.setObjectName(objectIdent);

                    String[] tempArray2 = tempArray[1].split("\\)");

                    //находим всех родителей класса
                    for(String temp : tempArray2[0].split(",")){

                        tempArrayList.add(temp.trim());
                    }

                    //сохраняем родителей в объект
                    for(String temp : tempArrayList){

                        objectPropertiesTemp.getParentName().add(temp);
                    }

                    //очищаем список
                    tempArrayList.clear();

                    objectPropertiesArrayList.add(
                            new ObjectProperties(objectPropertiesTemp.getObjectName(),objectPropertiesTemp.getParentName(),
                                    objectPropertiesTemp.getParamName()));

                    objectPropertiesTemp = null;

                }


            }//else

        }//for(String tempString : arrayList)

        return objectPropertiesArrayList;
    }
}
