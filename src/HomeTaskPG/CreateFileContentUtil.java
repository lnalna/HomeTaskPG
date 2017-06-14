package HomeTaskPG;

/*
 В данном классе создается контент для сохранения в файл
 */
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
import HomeTaskPG.Solution.ObjectProperties;



public class CreateFileContentUtil {

    public static ArrayList<String> createFileContent(TreeSet<ObjectProperties> objectPropertiesTreeSetList){

        ArrayList<String> outputArayList = new ArrayList<>();//список для хранения строк, для вывода в файл

        for (ObjectProperties tempObject : objectPropertiesTreeSetList) {

            StringBuilder stringBuilder = new StringBuilder();//используется для формирования строки

            stringBuilder.append(tempObject.getObjectName());//добавляем имя объекта
            stringBuilder.append(" ");//добавляем пробел

            if (tempObject.getParentName().size() > 0) { //если есть объекты-родители

                stringBuilder.append("( ");//добавляем открывающую скобку с пробелом

                int i = 0;
                int n = tempObject.getParentName().size();

                for (String temp : tempObject.getParentName()){

                    i++;
                    stringBuilder.append(temp);//добавляем имя объекта-родителя

                    if (i < n )
                        stringBuilder.append(" , ");//добавляем запятую разделитель, если объект не последний
                }

                stringBuilder.append(" ) ");//добавляем закрывающую скобку

            }

            if (tempObject.getParamName().size() > 0) {//если есть параметры

                stringBuilder.append(" : ");

                int i = 0;
                int n = tempObject.getParamName().size();

                for(Map.Entry<String,String> map : tempObject.getParamName().entrySet()){

                    i++;

                    if ( map.getValue().contains("(")) { //если параметр унаследованный

                        stringBuilder.append(map.getValue());

                        if (i<n)
                            stringBuilder.append(" , ");//добавляем запятую, если параметр не последний

                    }
                    else {//если параметр собственный

                        stringBuilder.append(map.getKey());
                        stringBuilder.append("=");
                        stringBuilder.append(map.getValue());

                        if ( i < n)
                            stringBuilder.append(" , ");//добавляем запятую, если параметр не последний
                    }
                }

            }


            stringBuilder.append("\n");//добавляем перевод строки
            outputArayList.add(stringBuilder.toString());//сохранение итоговой строки в список

        }//for (ObjectProperties tempObject : objectPropertiesTreeSetList)

        return outputArayList;
    }
}
