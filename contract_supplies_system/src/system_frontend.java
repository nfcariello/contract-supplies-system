import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class system_frontend {
    private JTextArea textArea1;
    private JTextPane textPane1;
    private JButton button1;
    private JList contract_list;

    main test = new main();

    public system_frontend() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test.hi();
            }
        });
    }
}
