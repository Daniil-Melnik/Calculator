package testing.CalcBackend.CalcApi;

import testing.CalcBackend.DisplayData;

// интерфейс, описывающий требования к классу строителя DTO от бэкенда до графического интерфейса
// паттерн - строитель
public interface DisplayDataBuilder {
    DisplayDataBuilder putStr(String str); // установка строки выражения
    DisplayDataBuilder putResStr(String str); // установка строки с результатом выражения
    DisplayDataBuilder putBtnEn(boolean btnEn); // установка флага разрешения нажатия кнопок
    DisplayDataBuilder putEqEn(boolean eqEn); // установка флага разрешения нажатия кнопки равенства
    DisplayDataBuilder putK(int k); // установка коэффициента уменьшения строки выражения
    DisplayDataBuilder reset(); // сброс строителя в начальное состояние
    DisplayData build(); // постройка из установленных "деталей"
    DisplayDataBuilder set(DisplayData data); // установка в строителя существующего экземпляра DTO для его досборки
}
