package testing.CalcBackend.CalcApiImpl;

import testing.CalcBackend.CalcApi.DisplayDataBuilder;
import testing.CalcBackend.DisplayData;

// класс-реализация строителя DTO. Реализует возможности паттерна проектирования: "Строитель".  
public class SimpleDisplayDataBuilder implements DisplayDataBuilder {
    private String str = "";
    private String resStr = "";
    private boolean btnEn = true;
    private boolean eqEn = false;
    private int k = 1;

    @Override
    public SimpleDisplayDataBuilder reset(){
        this.str = "";
        this.resStr = "";
        this.btnEn = true;
        this.eqEn = false;
        this.k = 1;
        return this;
    }

    @Override
    public DisplayDataBuilder set(DisplayData data){
        this.resStr = data.getResStr();
        this.str = data.getStr();
        this.k = data.getK();
        this.eqEn = data.getEqEnable();
        this.btnEn = data.getBtnEnable();
        return this;
    }

    @Override
    public DisplayDataBuilder putStr(String s) {
        str = s;
        return this;
    }

    @Override
    public DisplayDataBuilder putResStr(String rS) {
        resStr = rS;
        return this;
    }

    @Override
    public DisplayDataBuilder putBtnEn(boolean btnEn) {
        this.btnEn = btnEn;
        return this;
    }

    @Override
    public DisplayDataBuilder putEqEn(boolean eqEn) {
        this.eqEn = eqEn;
        return this;
    }

    @Override
    public DisplayDataBuilder putK(int k) {
        this.k = k;
        return this;
    }


    @Override
    public DisplayData build() {
        return new DisplayData(str, resStr, btnEn, eqEn, k);
    }
}
