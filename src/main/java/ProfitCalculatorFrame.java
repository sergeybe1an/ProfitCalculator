import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Date;

public class ProfitCalculatorFrame implements Constants {

    private JLabel calculateResultLabel;
    private JTextField amountOfEurosTF;
    private JSpinner timeSpinner;
    private JSpinner.DateEditor timeEditor;
    private JButton recalculateButton;

    private String calculateResult;

    public ProfitCalculatorFrame() {

        initMainFrame();
    }

    private void initMainFrame() {
        JFrame mainFrame = new JFrame("Расчет прибыли/убытка");

        mainFrame.setLayout(new MigLayout());
        mainFrame.setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timeSpinner = new JSpinner( new SpinnerDateModel() );
        timeEditor = new JSpinner.DateEditor(timeSpinner, "yyyy-MM-dd");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        JLabel timeLabel = new JLabel("Дата покупки:", JLabel.TRAILING);
        timeLabel.setLabelFor(timeSpinner);

        amountOfEurosTF = new JTextField(10);
        JLabel amountOfEurosLabel = new JLabel("Количество евро:", JLabel.TRAILING);
        amountOfEurosLabel.setLabelFor(amountOfEurosTF);

        recalculateButton = new JButton("Расчитать");
        recalculateButton.addActionListener(e -> {
            try {
                if (validateFields()) {
                    String partOfURLwithDate = simpleDateFormat.format(timeSpinner.getValue());
                    double amountOfEuros = Double.parseDouble(amountOfEurosTF.getText());

                    calculateResult = new CalculateDiffrence(amountOfEuros, partOfURLwithDate).getCalculateResult();
                    calculateResultLabel.setText(prefix + calculateResult + postfix);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        calculateResultLabel = new JLabel("");

        mainFrame.add(timeLabel);
        mainFrame.add(timeSpinner,"span, grow");
        mainFrame.add(amountOfEurosLabel);
        mainFrame.add(amountOfEurosTF, "wrap");
        mainFrame.add(recalculateButton,"span, grow");
        mainFrame.add(calculateResultLabel, "span, grow");

        mainFrame.setVisible(true);
    }

    private boolean validateFields() {
        String errors = "";

        try {
            Double.parseDouble(amountOfEurosTF.getText());
        } catch (NumberFormatException nfe) {
            errors += "Кол-во евро не указано или<br/> формат неверный<br/>";
        }

        calculateResultLabel.setText(prefix + errors + postfix);
        return errors.isEmpty() ? true : false;
    }
}
