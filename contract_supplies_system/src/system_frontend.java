import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class system_frontend {
    private JButton button1;

    main test = new main();
    private JPanel panel1;

    public system_frontend() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test.hi();
            }
        });
    }
}
