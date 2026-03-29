package testing.CalcBackend.CalcApi;

import testing.CalcBackend.DisplayData;

// интерфейс описывающий требования к ядру управления состоянием графического интерфейса - контроллера нажатий кнопок
public interface DisplayEngine {
    public DisplayData makeMove(String symbol); // метод предоставляющий реацию на нажатие определённой кнопки
                    // (реакция в виде DTO о новом состояниий графического интерфейса)
    public void setRadix(int r); // установка систмемы счисления в ядро из графического интерфейса предоставления функции
                    // выбора СисСчис результата
}
