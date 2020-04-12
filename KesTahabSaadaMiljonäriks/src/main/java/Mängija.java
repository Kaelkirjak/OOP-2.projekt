public class Mängija {
    // Isendiväljad.
    private String nimi;
    private int võidusumma;

    // Konstruktor.
    public Mängija(String nimi, int võidusumma) {
        this.nimi = nimi;
        this.võidusumma = võidusumma;
    }

    // Get meetodid.
    public String getNimi() {
        return nimi;
    }

    public int getVõidusumma() {
        return võidusumma;
    }
    // Set meetod, kus kirjutatakse võidusumma üle, kui mängija kaotas või võitis raha.
    public void setVõidusumma(int võidusumma) {
        this.võidusumma = võidusumma;
    }
}