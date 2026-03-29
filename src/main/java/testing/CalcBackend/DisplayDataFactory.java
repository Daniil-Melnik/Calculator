package testing.CalcBackend;

import lombok.NoArgsConstructor;
import testing.CalcBackend.CalcApi.DisplayDataBuilder;

// класс-фабрика для упрощения формирования DTO с конкретными частоиспользуемыми конфигурациями
// работает в взаимодействи со строителем DTO

@NoArgsConstructor
public class DisplayDataFactory {

    // получить начальный пустой экземпляр
    public static DisplayData createInitDataObject(DisplayDataBuilder builder){
        return builder.reset().build();
    }

    // получить экземпляр с характеристиками, характерными для момента после удачного применения равенства и получения равенства (сброс)
    public static DisplayData createAfterEqualObject(DisplayDataBuilder builder){
        return builder.putStr("").putResStr("").putEqEn(false).build();
    }

    // послучить экземпляр с характеристиками для момента после нажатия кнопки C
    public static DisplayData createAfterCancelObject(DisplayDataBuilder builder, String str, int k){
        return builder.putK(k).putStr(str).putBtnEn(true).putK(k).build();
    }

    // получить "полный" экземпляр с строкой выражения, строкой результата
    public static DisplayData createEqualObject(DisplayDataBuilder builder, String resStr, String str, int k){
        return builder.putResStr(resStr).putBtnEn(true).putStr(str).putK(k).build();
    }

    // получить экземпляр с обновлённой строкой выражения и пустой строкой результата
    public static DisplayData createSymbolAddedObject(DisplayDataBuilder builder, String str, int k){
        return builder.putStr(str).putK(k).build();
    }

    // получить обновленный экземпляр на основе существующего - с разрешением выполнения равенства
    public static DisplayData updateEqEnTrue(DisplayDataBuilder builder, DisplayData data){
        return builder.set(data).putEqEn(true).build();
    }

    // получить обновленный экземпляр на основе существующего - с запретом выполнения равенства
    public static DisplayData updateEqEnFalse(DisplayDataBuilder builder, DisplayData data){
        return builder.set(data).putEqEn(false).build();
    }

    // получить экземпляр с блокировкой нажатия кнопок
    public static DisplayData createBlockedObject(DisplayDataBuilder builder, String str){
        return builder.putBtnEn(false).putK(2).putStr(str).build();
    }

    // получить объект с активированным флагом ошибки (коэффициент уменьшения шрифта - отрицательное число)
    public static DisplayData createErrorObject(DisplayDataBuilder builder, String str){
        return builder.putK(-1).putStr(str).build();
    }
}
