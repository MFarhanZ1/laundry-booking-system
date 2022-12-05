import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        JFrame jFrame = new JFrame("Laundry Booking System");
        jFrame.setContentPane(new bookingSystem().getRootPanel());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1200, 260);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
