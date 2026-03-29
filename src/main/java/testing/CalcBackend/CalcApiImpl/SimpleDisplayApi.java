package testing.CalcBackend.CalcApiImpl;

import lombok.Setter;
import testing.CalcBackend.CalcApi.CalcEngine;
import testing.CalcBackend.CalcApi.DisplayEngine;
import testing.CalcBackend.DisplayData;
import testing.CalcBackend.DisplayDataFactory;

import java.util.Arrays;

// класс-реализация описывающий логику контроллера на нажатие кнопок в GUI, реализует соответсвующий интерфейс
public class SimpleDisplayApi implements DisplayEngine {

    private static final String[] BTN_SG = {"=", "/" , "-", "*", "+"}; // операции

    private String str = "";
    private String resStr = "";

    private final int symbolPerStr;
    @Setter
    private int radix;
    private boolean eqOp = false;
    private boolean lastInSGwM = false;

    public SimpleDisplayApi(int r, int sPS){
        symbolPerStr = sPS;
        radix = r;
    }


    @Override
    public DisplayData makeMove(String symbol) {
        int k;

        DisplayData data = new DisplayData();

        CalcEngine calcEngine = new SimpleCalcApi();

        k = str.length() / symbolPerStr + 1;

        if (eqOp) {
            System.out.println("QMQ1");
            eqOp = false;
            str = "";
            resStr = "";
            k = 1;
            data = DisplayDataFactory.createAfterEqualObject(new SimpleDisplayDataBuilder());
        } else {
            if ((symbol.equals("C")) && (!str.isEmpty())) {
                System.out.println("QMQ2");
                if (k == 3) k = 2;
                str = str.substring(0, str.length() - 1);
                data = DisplayDataFactory.createAfterCancelObject(new SimpleDisplayDataBuilder(), str, k);
            } else if ((k == 3) && !symbol.equals("=")) {
                System.out.println("QMQ3");
                data = DisplayDataFactory.createBlockedObject(new SimpleDisplayDataBuilder(), str);
            } else if (symbol.equals("=") && !str.isEmpty()) {
                System.out.println("QMQ4");
                if (k == 3) k = 2;
                try {
                    switch (radix) {
                        case 2:
                            resStr = "=b" + Integer.toBinaryString(calcEngine.calculateExpr(str));
                            break;
                        case 8:
                            resStr = "=o" + Integer.toOctalString(calcEngine.calculateExpr(str));
                            break;
                        case 10:
                            resStr = "=" + calcEngine.calculateExpr(str);
                            break;
                        case 16:
                            resStr = "=h" + Integer.toHexString(calcEngine.calculateExpr(str));
                            break;
                    }
                    eqOp = true;
                    data = DisplayDataFactory.createEqualObject(new SimpleDisplayDataBuilder(), resStr, str, k);
                } catch (IndexOutOfBoundsException | ArithmeticException e) {
                    data = DisplayDataFactory.createErrorObject(new SimpleDisplayDataBuilder(), str);
                }

            } else if ((!symbol.equals("C")) && (str.isEmpty()) && (!Arrays.asList(BTN_SG).contains(symbol))) {
                str += symbol;
                data = DisplayDataFactory.createSymbolAddedObject(new SimpleDisplayDataBuilder(), str, k);
                lastInSGwM = Arrays.asList(BTN_SG).contains(symbol);
            } else if ((!symbol.equals("C")) && ((!str.isEmpty())) && !(lastInSGwM && Arrays.asList(BTN_SG).contains(symbol))) {
                str += symbol;
                data = DisplayDataFactory.createSymbolAddedObject(new SimpleDisplayDataBuilder(), str, k);
                lastInSGwM = Arrays.asList(BTN_SG).contains(symbol);
            }
            if (str.length() == 0) {
                data = DisplayDataFactory.updateEqEnFalse(new SimpleDisplayDataBuilder(), data);
            } else data = DisplayDataFactory.updateEqEnTrue(new SimpleDisplayDataBuilder(), data);
       }
        return data;
    }
}
