package testing.CalcBackend.CalcApi;

// интерфейс, описывающий требования к ядру логики расчёта значения выражения по его строчному представлению
public interface CalcEngine {
    public int calculateExpr(String s) throws ArithmeticException;
}
