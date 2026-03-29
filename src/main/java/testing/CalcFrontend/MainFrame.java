package testing.CalcFrontend;

/*
2. Освоить средства граничной компоновки, создать простой оконный калькулятор
- сформировать фрейм, поделить его на 2-е части: экран калькулятора и сетка кнопок
- присвоить каждой кнопке в сетке действие по наполнению строки выражения
- продублировать эти же действия на нажатия клавиш
- проработать экран калькулятора на отрисовку строки
- реализовать очистку экрана и вывод операции
- при недопустимых операциях - вывод окна с ошибкой
*/

import testing.CalcBackend.CalcApi.DisplayEngine;
import testing.CalcBackend.CalcApiImpl.SimpleDisplayApi;
import testing.CalcBackend.DisplayData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

// класс, описывающий интерфейс приложения: содержит в себе панель с кнопками, панель с дисплеем и строку меню

public class MainFrame extends JFrame {
    private static BtnPanel btnPanel;   // панель с кнопками (сеточная компоновка)
    private static CalcPanel calcPanel; // панель с компонентом отрисовки выражения и результата
    private static ScreenComponent screenComponent; // компонент отрисовки выражения и результата


    private static final int FRAME_W = 300; // размеры панельных компонентов интерфейса
    private static final int FRAME_H = 295;

    private static final int BTN_PANEL_W = 300;
    private static final int BTN_PANEL_H = 140;

    private static final int CALC_PANEL_W = 300;
    private static final int CALC_PANEL_H = 90;

    private static final String[] BTN_S = {"1", "2" , "3", "+", "4", "5", "6", "-", // массив
            "7", "8", "9", "/" , "0", "C", "=", "*"}; // символов для кнопок и действий

    private static final String[] KEYSTROKES = {"NUMPAD1", "NUMPAD2", "NUMPAD3", "ADD", "NUMPAD4", "NUMPAD5",
            "NUMPAD6", "SUBTRACT", "NUMPAD7", "NUMPAD8", "NUMPAD9",
            "DIVIDE", "NUMPAD0", "BACK_SPACE", "ENTER", "MULTIPLY"
    }; // коды нажимаемых клавиш для формирования KeyStroke
    // под ActionMap и InputMap

    private static final int SYMBOLS_PER_STR = 13; // кол-во символов выражения в строке при 32-м кегле

    // экземпляр объекта формирования данных для отображения в интерфейсе: блокировки кнопок, строки выражения и строки результата
    // вложен по паттерну проектирования "Мост" в виде интерфейсной переменной
    private static DisplayEngine displayEngine = new SimpleDisplayApi(10, SYMBOLS_PER_STR);

        public MainFrame(){
            setLayout(new BorderLayout());
            setSize(FRAME_W, FRAME_H);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setTitle("Калькулятор");
            setIconImage(
                    new ImageIcon(
                            Objects.requireNonNull(this.getClass().getResource("/calculator.png"))
                    ).getImage());

            btnPanel = new BtnPanel(); // введение панели с кнопками
            add(btnPanel, BorderLayout.SOUTH); // внизу

            calcPanel = new CalcPanel(); // введение панели-экрана
            add(calcPanel, BorderLayout.NORTH); // вверху
            setJMenuBar(new MainMenuBar()); // установка строки меню
        }

    private static class BtnPanel extends JPanel{ // класс панели с кнопками
        private static JButton[] btns = new JButton[16]; // 16 кнопок (цифры, операции)
        private static AbstractAction[] actions = new BtnAction[16]; // 16 действий (для кнопок и клавиш)

        public BtnPanel(){
            setLayout(new GridLayout(4, 4)); // установка сеточной компоновки 4*4 секции
            int i = 0;

            for (String s : BTN_S) { // создание кнопок
                actions[i] = new BtnAction(s);
                btns[i] = new JButton(actions[i]);
                btns[i].setFont(new Font("Courier New", Font.BOLD, 16));
                add(btns[i]); // последовательная набивка сетки кнопками
                i++;
            }
            createKeyIAMap(); // установка соответсвия клавишам действий
            setEnableEqual(false); // логика
        }

        public void setEnableEqual(boolean b){ // логика-блокировка кнопки и действия равенства
            btns[14].setEnabled(b);
            actions[14].putValue("enabled", b);
        }

        public void setEnableBtns(boolean b){ // блокировка/разблокировка всех кроме равенства и очистки
            for (int i = 0; i < 16; i++){
                btns[i].setEnabled(b);
                actions[i].putValue("enabled", b);
            }
            btns[13].setEnabled(true);
            btns[14].setEnabled(true);

            actions[13].putValue("enabled", true); // отдельный ключ для "блокировки" - enabled
            actions[14].putValue("enabled", true);
        }

        private void createKeyIAMap(){ // метод формирования отображения действие-ключ-клавиша
            ActionMap amap = this.getActionMap();
            InputMap imap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);

            for (int i = 0; i < 16; i++){
                imap.put(KeyStroke.getKeyStroke(KEYSTROKES[i]), BTN_S[i]);
                amap.put(BTN_S[i], actions[i]);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(BTN_PANEL_W, BTN_PANEL_H);
        }
    }

    private static class CalcPanel extends JPanel{ // панель-экран вычислений
        public CalcPanel(){
            setLayout(new BorderLayout()); // граничная компоновка
            setBackground(Color.WHITE); // фон - белый
            screenComponent = new ScreenComponent(); // добаление компонента отрисовки выражения и результата
            add(screenComponent);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CALC_PANEL_W, CALC_PANEL_H);
        }
    }

    private static class BtnAction extends AbstractAction{ // действие для кнопки
        public BtnAction(String s){
            putValue(Action.NAME, s);
            putValue(Action.SHORT_DESCRIPTION, s);
            putValue("enabled", true); // отдельный ключ для "блокированности"
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            DisplayData data = displayEngine.makeMove(this.getValue(Action.NAME).toString()); // метод DisplayEngine
            // предназначенный для получения DTO с новыми установками для интерфейса от бэкенда
            if (data.getK() > 0) screenComponent.repaintComponent(data); // если в DTO нет флага ошибки, то перерисовать интерфейс по новым условиям из бекенда
            else JOptionPane.showMessageDialog(calcPanel, "Некорректное выражение или возникло деление на ноль",
                    "ОШИБКА", JOptionPane.ERROR_MESSAGE); // если вернулся флаг ошибки (коэффициент уменшения k = -1), то выдать сообщение
        }
    }

    private static class ScreenComponent extends JComponent{ // компонент отрисовки цифр и зопераций
        private String str = ""; // строка выражения
        private String strRes = ""; // строка результата
        private int k = 1; // делитель кегля для кол-ва символов

        @Override
        protected void paintComponent(Graphics g) { // отрисовка компонента
            g.setFont(new Font("Courier New", Font.BOLD, 32 / k)); // отрисовка выражения
            g.drawString(str, 0, 25);
            g.setFont(new Font("Courier New", Font.BOLD, 26)); // отрисовка результата
            g.drawString(strRes, 5, 65);
        }

        public void repaintComponent(DisplayData data){ // метод для перерисовки интерфейса на основании экземпляра данных об интерфейсе
            str = data.getStr();
            strRes = data.getResStr();
            btnPanel.setEnableBtns(data.getBtnEnable());
            btnPanel.setEnableEqual(data.getEqEnable());
            k = data.getK();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CALC_PANEL_H, CALC_PANEL_W);
        }
    }

    private static class MainMenuBar extends JMenuBar{ // полоса меню

        private final int[] items = {2, 8, 10, 16};

        public MainMenuBar(){
            JMenuItem item = null;
            JRadioButtonMenuItem radioItem = null;
            ButtonGroup group = new ButtonGroup(); // группа пунктов меню - радио-кнопок

            JMenu fileMenu = new JMenu("Файл (F)");
            JMenu radixMenu = new JMenu("СисСчис (R)"); // меню для радио-кнопок

            fileMenu.setMnemonic(KeyEvent.VK_F);
            radixMenu.setMnemonic(KeyEvent.VK_R);

            JMenuItem exitItem = new JMenuItem("Закрыть (E)", 'E');
            JMenuItem aboutItem = new JMenuItem("О программе (A)", 'A');

            for (int i : items){

                radioItem = new JRadioButtonMenuItem(Integer.toString(i)); // наполнение меню и группы пунктами-радиокнопками
                group.add(radioItem);
                if (i == 10) radioItem.setSelected(true);

                radioItem.addActionListener((e) -> {
                    displayEngine.setRadix(i);
                });
                radixMenu.add(radioItem);
            }

            exitItem.addActionListener((e) -> {
                    System.exit(0);
            });

            aboutItem.addActionListener((e) -> {
                JOptionPane.showMessageDialog(calcPanel,
                        "Демонстрация применения граничной компоновки на примере калькулятора с на" +
                                "выполненни арифметических операций в очередном порядке",
                        "О программе", JOptionPane.INFORMATION_MESSAGE);
            });

            fileMenu.add(exitItem);
            fileMenu.add(aboutItem);

            add(fileMenu);
            add(radixMenu);
        }
    }
}