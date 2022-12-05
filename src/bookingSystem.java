import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class bookingSystem {
    private JTextField textCustomerName;
    private JTextField textTotalKG;
    private JComboBox comboJenisLayanan;
    private JComboBox comboKategoriPaket;
    private JRadioButton jemputLangsungRadioButton;
    private JRadioButton antarKerumahRadioButton;
    private JTextField textCatatanKhusus;
    private JTable tableOutput;
    private JButton saveDataButton;
    private JPanel rootPanel;
    private JLabel labelJenisLayanan;
    private JLabel labelKategoriPaket;
    private LaundryBackend laundryBackend;
    private DefaultTableModel tableModel;
    private String calculateTotalAkhir;

    public void setLabelKategoriPaket(String hargaPaket) {
        labelKategoriPaket.setText("+Rp." + hargaPaket);
    }

    public void setLabelJenisLayanan(String hargaLayanan) {
        labelJenisLayanan.setText("Rp." + hargaLayanan + "/kg");
    }

    public bookingSystem() throws FileNotFoundException {

        this.laundryBackend = new LaundryBackend();
        this.initComponents();

        saveDataButton.addActionListener(e -> {

            if (textCustomerName.getText().isBlank()){
                JOptionPane.showMessageDialog(null, "Masukan Nama Kustomer Terlebih Dahulu!");
                return;
            } else if (comboJenisLayanan.getSelectedItem().toString().equals("-Pilih Layanan-")){
                JOptionPane.showMessageDialog(null, "Pilih Jenis Layanan Terlebih Dahulu!");
                return;
            } else if (comboKategoriPaket.getSelectedItem().toString().equals("-Pilih Kategori-")){
                JOptionPane.showMessageDialog(null, "Pilih Kategori Paket Terlebih Dahulu!");
                return;
            } else if (textCatatanKhusus.getText().isBlank()) {
                textCatatanKhusus.setText("Tidak Ada");
            }

            this.laundryBackend.setNamaKustomer(textCustomerName.getText());

            try {
                this.laundryBackend.setTotalKG(Integer.parseInt(textTotalKG.getText()));
            } catch (NumberFormatException ec){
                JOptionPane.showMessageDialog(rootPanel,"Masukan nilai total (Kg) berupa angka, bukan teks!","Perhatikan Field Input Total (Kg)",JOptionPane.WARNING_MESSAGE);
                this.textTotalKG.setText("");
                return;
            }

            if(antarKerumahRadioButton.isSelected()) {
                this.laundryBackend.setTipePengantaran("Antar Kerumah");
            }else{
                this.laundryBackend.setTipePengantaran("Jemput Langsung");
            }

            this.laundryBackend.setCatatanKhusus(textCatatanKhusus.getText());

            calculateTotalAkhir = this.laundryBackend.calculateTotalAkhir();

            tableModel.addRow(new Object[]{ this.laundryBackend.getNamaKustomer(),
                                            String.valueOf(this.laundryBackend.getTotalKG()) + " kg",
                                            this.laundryBackend.getJenisLayanan(),
                                            this.laundryBackend.getKategoriPaket(),
                                            this.laundryBackend.getTipePengantaran(),
                                            this.laundryBackend.getCatatanKhusus(),
                                            calculateTotalAkhir});

            String writingForResult  = String.format("Nama Kustomer : %s\nTotal (Kg) : %s\nJenis Layanan : %s\nKategori Paket : %s\nTipe Pengantaran : %s\nCatatan Khusus : %s\nTotal Harga : %s\n\n",
                                                    this.laundryBackend.getNamaKustomer(),
                                                    String.valueOf(this.laundryBackend.getTotalKG()) + " kg",
                                                    this.laundryBackend.getJenisLayanan(),
                                                    this.laundryBackend.getKategoriPaket(),
                                                    this.laundryBackend.getTipePengantaran(),
                                                    this.laundryBackend.getCatatanKhusus(),
                                                    calculateTotalAkhir);

            String stringTempArray = String.format("%s#%s#%s#%s#%s#%s#%s\n",
                                            this.laundryBackend.getNamaKustomer(),
                                            String.valueOf(this.laundryBackend.getTotalKG()) + " kg",
                                            this.laundryBackend.getJenisLayanan(),
                                            this.laundryBackend.getKategoriPaket(),
                                            this.laundryBackend.getTipePengantaran(),
                                            this.laundryBackend.getCatatanKhusus(),
                                            calculateTotalAkhir);

            try {
                new DataMechanism().insertingDataToFile(writingForResult);
                new DataMechanism().storedDataToTempFile(stringTempArray);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Data Berhasil Tersimpan!");

            textCustomerName.setText("");
            textTotalKG.setText("");
            comboJenisLayanan.setSelectedIndex(0);
            comboKategoriPaket.setSelectedIndex(0);
            textCatatanKhusus.setText("");

        });
        comboJenisLayanan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laundryBackend.setJenisLayanan(comboJenisLayanan.getSelectedItem().toString());
                setLabelJenisLayanan(laundryBackend.getHargaJenisLayananString());
            }
        });
        comboKategoriPaket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laundryBackend.setKategoriPaket(comboKategoriPaket.getSelectedItem().toString());
                setLabelKategoriPaket(laundryBackend.getHargaKategoriPaketString());
            }
        });
    }

    private void initComponents() throws FileNotFoundException {

        Object[] tableColumn = {"Nama Kustomer",
                                "Total (Kg)",
                                "Jenis Layanan",
                                "Kategori Paket",
                                "Tipe Pengantaran",
                                "Catatan Khusus",
                                "Total Biaya"};

        tableModel = new DefaultTableModel(new DataMechanism().getmObject(), tableColumn);
        tableOutput.setModel(tableModel);
        tableOutput.setAutoCreateRowSorter(true);

        for(int i = 0; i < 7; i++) {
            TableColumn col = tableOutput.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }

    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
