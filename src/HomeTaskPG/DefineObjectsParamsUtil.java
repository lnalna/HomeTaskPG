package HomeTaskPG;

/*
В данном классе происходит определение полного набора параметров всех объектов
 */
import HomeTaskPG.Solution.ObjectProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class DefineObjectsParamsUtil {

    public static TreeSet<ObjectProperties> defineObjectsParams(TreeSet<Solution.ObjectProperties> objectPropertiesTreeSetList) {

         /*
        В данном цикле происходит определение полного набора параметров всех объектов с учетом наследования
         */

        for (ObjectProperties currentObject : objectPropertiesTreeSetList) {

            if (currentObject.getParentName().size() > 0) {//список имен родительских объектов не пустой

                ArrayList<String> currentObjectParentsList = new ArrayList<>();//временный список для  хранения имен родителей
                HashMap<String,String> currentObjectParamsList = new HashMap<>();//временный список для хранения параметров объекта

                //получаем список имен родителей нашего объекта
                for (String tempString : currentObject.getParentName())
                    currentObjectParentsList.add(tempString);

                //получаем список параметров нашего объекта
                for (Map.Entry<String, String> map : currentObject.getParamName().entrySet())
                    currentObjectParamsList.put(map.getKey(),map.getValue());


                //начинаем проход с начала списка для поиска родителей текущено объекта
                for(ObjectProperties currentParentObject : objectPropertiesTreeSetList)

                    //если список имен родителей текущего объекта содержит имя  объекта ему предшествующего
                    //т.е предшествующий объект является родителем текущего объекта
                    if(currentObjectParentsList.contains(currentParentObject.getObjectName())) {


                        //получаем список параметров родительского класса
                        HashMap<String, String> tempHashParentParam = new HashMap<>();

                        for(Map.Entry<String, String> map : currentParentObject.getParamName().entrySet()){
                            tempHashParentParam.put(map.getKey(),map.getValue());

                        }


                        //тогда в список параметров нашего объекта необходимо добавит параметры родительского класса
                        for(Map.Entry<String, String> map : tempHashParentParam.entrySet()) {

                            String tempString = map.getValue();

                            if(!tempString.contains("("))//если значение родительского параметра не содержит скобку
                                //значит параметр не является унаследованным от какого-то объекта
                                currentObjectParamsList.put(map.getKey(), map.getKey() + "(" + currentParentObject.getObjectName() + ")=" + map.getValue());
                            else
                                currentObjectParamsList.put(map.getKey(),map.getValue());
                        }


                        //в наш текущий объект записываем список параметров
                        //в этом списке ключи, если они встретятся в следующих родителях, будут перекрываться
                        //значения будут формироваться исходя из имени объекта


                        for (Map.Entry<String , String > map : currentObjectParamsList.entrySet()){

                            //если наш объект содержит такой параметр
                            //проверяем, является ли параметр унаследованным от предка
                            //переписываем, если параметр унаследованный
                            if ( currentObject.getParamName().containsKey(map.getKey())){

                                String tempOurValue = currentObject.getParamName().get(map.getKey());

                                if (tempOurValue.contains("("))
                                    currentObject.getParamName().put(map.getKey(),map.getValue());

                            }

                            //если наш объект  не содержит такой параметр
                            //тогда добавляем параметр в объект
                            if ( !currentObject.getParamName().containsKey(map.getKey()))
                                currentObject.getParamName().put(map.getKey(),map.getValue());


                        }
                    }


            }

        }

        return objectPropertiesTreeSetList;
    }
}
