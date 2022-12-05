import java.text.NumberFormat;
import java.util.Locale;

public class LaundryBackend {
    private String namaKustomer;
    private int totalKG;
    private String jenisLayanan;
    private String kategoriPaket;
    private String tipePengantaran;
    private String catatanKhusus;
    private int totalHarga;
    private int hargaJenisLayanan;
    private int hargaKategoriPaket;

    private NumberFormat nf;

    public LaundryBackend(){
        nf = NumberFormat.getInstance(Locale.US);
    }

    public int getHargaJenisLayanan() {
        return hargaJenisLayanan;
    }

    public String getHargaJenisLayananString(){
        return nf.format(hargaJenisLayanan);
    }

    public void setHargaJenisLayanan(int hargaJenisLayanan) {
        this.hargaJenisLayanan = hargaJenisLayanan;
        setHarga(this.hargaJenisLayanan);
    }

    public int getHargaKategoriPaket() {
        return hargaKategoriPaket;
    }

    public String getHargaKategoriPaketString(){
        return nf.format(hargaKategoriPaket);
    }

    public void setHargaKategoriPaket(int hargaKategoriPaket) {
        this.hargaKategoriPaket = hargaKategoriPaket;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setHarga(int harga) {
        this.totalHarga += harga;
    }

    public String getNamaKustomer() {
        return namaKustomer;
    }

    public void setNamaKustomer(String namaKustomer) {
        this.namaKustomer = namaKustomer;
    }

    public int getTotalKG() {
        return totalKG;
    }

    public void setTotalKG(int totalKG) {
        this.totalKG = totalKG;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
        switch (jenisLayanan){
            case "Cuci dan Gosok":
                setHargaJenisLayanan(7000);
                break;
            case "Gosok Saja":
                setHargaJenisLayanan(3000);
                break;
            case "Cuci Saja":
                setHargaJenisLayanan(5000);
                break;
        }
    }

    public String getKategoriPaket() {
        return kategoriPaket;
    }

    public void setKategoriPaket(String kategoriPaket) {
        this.kategoriPaket = kategoriPaket;
        switch (kategoriPaket){
            case "Reguler":
                setHargaKategoriPaket(2000);
                break;
            case "Kilat One Day":
                setHargaKategoriPaket(5000);
                break;
        }
    }

    public String getTipePengantaran() {
        return tipePengantaran;
    }

    public void setTipePengantaran(String tipePengantaran) {
        this.tipePengantaran = tipePengantaran;
        switch (tipePengantaran) {
            case "Antar Kerumah":
                setHarga(5000);
                break;
            case "Jemput Langsung":
                setHarga(0);
                break;
        }
    }

    public String getCatatanKhusus() {
        return catatanKhusus;
    }

    public void setCatatanKhusus(String kodePromo) {
        this.catatanKhusus = kodePromo;
    }

    public String calculateTotalAkhir(){
        int totalHargaFinal = this.totalHarga*getTotalKG()+getHargaKategoriPaket();
        restartTotalHarga();
        return "Rp."+nf.format(totalHargaFinal);
    }

    public void restartTotalHarga(){
        this.totalHarga = 0;
        setHargaJenisLayanan(0);
        setHargaKategoriPaket(0);
    }
}
