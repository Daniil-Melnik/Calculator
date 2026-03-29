package testing.CalcBackend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// класс, описывающий экземпляр данных, предназначенный для транспортировки от логической части программы к интерфейсу (DTO)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayData {
    private String str; // строка выражения
    private String resStr; // строка с результатом вычисления
    private boolean btnEnable; // флаг разрешения нажатия кнопок
    private boolean eqEnable; // флаг разрешения нажатия кнопки равенства
    private int k; // коэффициент уменьшения шрифта в строке выражения

    public boolean getBtnEnable(){ return btnEnable;} // отдельные геттеры, не реализуемые библиотекой Lombok
    public boolean getEqEnable() { return eqEnable;}

    @Override
    public String toString() { // служебный метод для определения строчного представления экземпляра DTO
        return String.format("str = %s; resStr = %s; btnEnable = %s; eqEnable = %s; k = %s;", str, resStr, btnEnable, eqEnable, k);
    }
}
