package testing.CalcBackend.CalcApiImpl;

import testing.CalcBackend.CalcApi.CalcEngine;

import java.util.ArrayList;
import java.util.List;

// класс, описывающий ядро логики расчёта значения выражения по его строчному представлению
// реализует соответсвующий интерфейс
public class SimpleCalcApi implements CalcEngine {

    // разбивка выражения по знакам арифметических операций
    private ArrayList<String> getSplitExpression(String str){
        String [] splittedStr = str.split("(?<=[-*/+])"); // разбивка по регулярному выражению 25+,7-,5 ~ 25+7-5
        return new ArrayList<>(List.of(splittedStr)); // формироание выходного списочного массива
    }

    // логика подсчёта значения выражения с учетом приоритетов операции в отсутсвие скобок
    // на базе сокращения массива значений-операций до одного элемента - результата
    public int calculateExpr(String str) throws ArithmeticException{
        ArrayList<String> sA = getSplitExpression(str);
        String current = "";
        String next = "";
        String nextOp = "";

        int currentI = 0;
        int nextI = 0;
        int res = 0;

        String regex = ".*[-+*/]";
        int l = sA.size();
        int i = 0;
        while (i < l){
            current = sA.get(i);
            char lastChar = current.charAt(current.length()-1);
            if (lastChar == '*' || lastChar == '/'){ // выполнение приоритетных * и /
                next = sA.get(i+1);
                currentI = Integer.parseInt(current.substring(0, current.length()-1));
                nextI = Integer.parseInt(next.matches(regex) ? next.substring(0, next.length()-1) : next);
                nextOp = (next.matches(regex) ? next.charAt(next.length()-1) + "" : "");
                sA.remove(i+1);
            }
            switch (lastChar){
                case '*':
                    res = currentI * nextI;
                    sA.set(i, res + nextOp);
                    l--;
                    i=0;
                    break;
                case '/':
                    res = currentI / nextI;
                    sA.set(i, res + nextOp);
                    l--;
                    i=0;
                default:
                    i++;
            }
        }

        i = 0;
        l = sA.size();
        while (i < l){
            current = sA.get(i);
            char lastChar = current.charAt(current.length()-1);
            if (lastChar == '-' || lastChar == '+'){ // выполнение оставшихся - и +
                next = sA.get(i+1);
                currentI = Integer.parseInt(current.substring(0, current.length()-1));
                nextI = Integer.parseInt(next.matches(regex) ? next.substring(0, next.length()-1) : next);
                nextOp = (next.matches(regex) ? next.charAt(next.length()-1) + "" : "");
                sA.remove(i+1);
            }
            switch (current.charAt(current.length()-1)){
                case '-':
                    res = currentI - nextI;
                    sA.set(i, res + nextOp);
                    l--;
                    i=0;
                    break;
                case '+':
                    res = currentI + nextI;
                    sA.set(i, res + nextOp);
                    l--;
                    i=0;
                    break;
                default:
                    i++;
            }
        }
        return Integer.parseInt(sA.get(0));
    }
}
