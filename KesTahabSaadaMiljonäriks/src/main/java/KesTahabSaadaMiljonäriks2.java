import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;
import java.util.Random;
import java.util.Timer;

public class KesTahabSaadaMiljonäriks2 extends Application {
    String mängijaNimi;

    @Override
    public void start(Stage peaLava) throws Exception{
        music();
        StackPane juur = new StackPane();
        Scene stseen1 = new Scene(juur, 900, 600);

        Image img = new Image("file:pilt.png");
        ImageView imgView = new ImageView(img);

        stseen1.widthProperty().addListener((objekt, vanaLaius, uusLaius) -> {
            double laius = uusLaius.doubleValue();
            double kõrgus = peaLava.getHeight();
            imgView.setFitHeight(kõrgus);
            imgView.setFitWidth(laius);
        });
        imgView.fitWidthProperty().bind(stseen1.widthProperty());

        stseen1.heightProperty().addListener((observable, oldValue, newValue) -> {
            double laius = peaLava.getWidth();
            double kõrgus = newValue.doubleValue();
            imgView.setFitHeight(kõrgus);
            imgView.setFitWidth(laius);
        });
        imgView.fitHeightProperty().bind(stseen1.heightProperty());
        juur.getChildren().add(imgView);

        VBox tutvustus = new VBox();
        tutvustus.setAlignment(Pos.BASELINE_CENTER);
        tutvustus.setSpacing(10);
        tutvustus.setPadding(new Insets(10, 10, 10, 10));
        Label rida1 = new Label("Tere tulemast mängu \"kes tahab saada miljonäriks\".");
        rida1.setTextFill(Color.BROWN);
        rida1.setFont(new Font("Arial", 20));
        Label rida2 = new Label("Iga küsimuse jaoks on 1 minut aega, et sellele vastata.");
        rida2.setTextFill(Color.BROWN);
        rida2.setFont(new Font("Arial", 20));
        Label rida3 = new Label("Samuti on mäng läbi, kui sisestate vale vastuse.");
        rida3.setTextFill(Color.BROWN);
        rida3.setFont(new Font("Arial", 20));

        Label rida4 = new Label("Vastuseks sisestage üks valiku vastustest MITTE NUMBER.");
        rida4.setTextFill(Color.YELLOW);
        rida4.setFont(new Font("Arial", 20));
        Label rida5 = new Label("Palun sisestage oma nimi allolevasse kasti ja vajutage ENTER");
        rida5.setTextFill(Color.YELLOW);
        rida5.setFont(new Font("Arial", 20));
        Label rida6 = new Label("Kui ei jõua selle aja sees vastata on Teie jaoks mäng läbi.");
        rida6.setTextFill(Color.YELLOW);
        rida6.setFont(new Font("Arial", 20));
        TextField nimi3 = new TextField();
        nimi3.setMaxWidth(300);
        Button alustamine = new Button("Alustan mänguga");
        tutvustus.getChildren().addAll(rida1, rida2, rida6, rida3, rida4, rida5, nimi3, alustamine);
        juur.getChildren().add(tutvustus);

        nimi3.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                mängijaNimi = nimi3.getText();
            }
        });
        Mängija mängija = new Mängija(mängijaNimi, 0); // Luuakse mängija.

        Andmed informatsioon = küsimusteAndmed("küsimused1.txt");

        // Massiivid/maatriks oma vastava sisuga.
        int[] summa = informatsioon.getSumma();
        String[] küsimused = informatsioon.getKüsimused();
        String[][] vastused = informatsioon.getVastused();
        String[] vihjed = informatsioon.getVihjed();
        String[] õiged = informatsioon.getÕigedVastused();

        // Eraldi failis asuva lisainfo massiivi lisamine.
        String lisa[] = lisaTekst("1");

        alustamine.setOnMousePressed(mouseEvent -> { // Kui nupule vajutatakse teeb järgnevat.
                alustamine.setOnMouseClicked(me -> {
                    BorderPane juur1 = new BorderPane();

                    Text mängijaNimi3 = new Text("Mängija: " + mängijaNimi + ". Summa: " + mängija.getVõidusumma());

                    //juur1.getChildren().add(mängijaNimi);
                    juur1.setTop(mängijaNimi3);

                    VBox küsimusteNupud = new VBox(); // Luuakse vertikaalne paigutus iga küsimuse nupu jaoks.
                    //küsimusteNupud.setAlignment(Pos.TOP_CENTER);
                    küsimusteNupud.setPadding(new Insets(2, 2, 2, 2));
                    küsimusteNupud.setSpacing(5);

                    VBox rahasummad = new VBox();
                    rahasummad.setPadding(new Insets(2, 2, 2, 10));
                    rahasummad.setSpacing(15);

                    Label võidusumma1 = new Label(Integer.toString(summa[0]));
                    Label võidusumma2 = new Label(Integer.toString(summa[1]));
                    Label võidusumma3 = new Label(Integer.toString(summa[2]));
                    Label võidusumma4 = new Label(Integer.toString(summa[3]));
                    Label võidusumma5 = new Label(Integer.toString(summa[4]));
                    Label võidusumma6 = new Label(Integer.toString(summa[5]));
                    Label võidusumma7 = new Label(Integer.toString(summa[6]));
                    Label võidusumma8 = new Label(Integer.toString(summa[7]));
                    Label võidusumma9 = new Label(Integer.toString(summa[8]));
                    Label võidusumma10 = new Label(Integer.toString(summa[9]));
                    Label võidusumma11 = new Label(Integer.toString(summa[10]));
                    Label võidusumma12 = new Label(Integer.toString(summa[11]));
                    Label võidusumma13 = new Label(Integer.toString(summa[12]));
                    Label võidusumma14 = new Label(Integer.toString(summa[13]));
                    Label võidusumma15= new Label(Integer.toString(summa[14]));
                    rahasummad.getChildren().addAll(võidusumma1, võidusumma2, võidusumma3,võidusumma4,võidusumma5,võidusumma6,võidusumma7,võidusumma8,võidusumma9,võidusumma10,võidusumma11,võidusumma12,võidusumma13,võidusumma14,võidusumma15);
                    HBox paigutus = new HBox(küsimusteNupud, rahasummad);

                    Button esimeneKüsimus = new Button("Küsimus 1"); // Esimese küsimuse nupp.
                    Button teineKüsimus = new Button("Küsimus 2"); // Teise küsimuse nupp.
                    teineKüsimus.setDisable(true);
                    Button kolmasKüsimus = new Button("Küsimus 3"); // Kolmanda küsimuse nupp.
                    kolmasKüsimus.setDisable(true);
                    Button neljasKüsimus = new Button("Küsimus 4"); // Kolmanda küsimuse nupp.
                    neljasKüsimus.setDisable(true);
                    Button viiesKüsimus = new Button("Küsimus 5"); // Kolmanda küsimuse nupp.
                    viiesKüsimus.setDisable(true);
                    Button kuuesKüsimus = new Button("Küsimus 6"); // Kolmanda küsimuse nupp.
                    kuuesKüsimus.setDisable(true);
                    Button seitsmesKüsimus = new Button("Küsimus 7"); // Kolmanda küsimuse nupp.
                    seitsmesKüsimus.setDisable(true);
                    Button kaheksasKüsimus = new Button("Küsimus 8"); // Kolmanda küsimuse nupp.
                    kaheksasKüsimus.setDisable(true);
                    Button üheksasKüsimus = new Button("Küsimus 9"); // Kolmanda küsimuse nupp.
                    üheksasKüsimus.setDisable(true);
                    Button kümnesKüsimus = new Button("Küsimus 10"); // Kolmanda küsimuse nupp.
                    kümnesKüsimus.setDisable(true);
                    Button üheteistkümnesKüsimus = new Button("Küsimus 11"); // Kolmanda küsimuse nupp.
                    üheteistkümnesKüsimus.setDisable(true);
                    Button kaheteistkümnesKüsimus = new Button("Küsimus 12"); // Kolmanda küsimuse nupp.
                    kaheteistkümnesKüsimus.setDisable(true);
                    Button kolmeteistkümnesKüsimus = new Button("Küsimus 13"); // Kolmanda küsimuse nupp.
                    kolmeteistkümnesKüsimus.setDisable(true);
                    Button neljateistkümnesKüsimus = new Button("Küsimus 14"); // Kolmanda küsimuse nupp.
                    neljateistkümnesKüsimus.setDisable(true);
                    Button viieteistkümnesKüsimus = new Button("Küsimus 15"); // Kolmanda küsimuse nupp.
                    viieteistkümnesKüsimus.setDisable(true);

                    // Esimese küsimuse nupule vajutamisel väljastatakse küsimus koos valikvastustega.
                    esimeneKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[0], vastused[0][0], vastused[0][1], vastused[0][2], vastused[0][3], õiged[0]);
                        teineKüsimus.setDisable(false);
                    });

                    // Teise küsimuse nupule vajutamisel väljastatakse küsimus koos valikvastustega.
                    teineKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[1], vastused[1][0], vastused[1][1], vastused[1][2], vastused[1][3], õiged[1]);
                        kolmasKüsimus.setDisable(false);
                    });

                    kolmasKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[2], vastused[2][0], vastused[2][1], vastused[2][2], vastused[2][3], õiged[2]);
                        neljasKüsimus.setDisable(false);
                    });

                    neljasKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[3], vastused[3][0], vastused[3][1], vastused[3][2], vastused[3][3], õiged[3]);
                        viiesKüsimus.setDisable(false);
                    });

                    viiesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[4], vastused[4][0], vastused[4][1], vastused[4][2], vastused[4][3], õiged[4]);
                        kuuesKüsimus.setDisable(false);
                    });

                    kuuesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[5], vastused[5][0], vastused[5][1], vastused[5][2], vastused[5][3], õiged[5]);
                        seitsmesKüsimus.setDisable(false);
                    });

                    seitsmesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[6], vastused[6][0], vastused[6][1], vastused[6][2], vastused[6][3], õiged[6]);
                        kaheksasKüsimus.setDisable(false);
                    });

                    kaheksasKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[7], vastused[7][0], vastused[7][1], vastused[7][2], vastused[7][3], õiged[7]);
                        üheksasKüsimus.setDisable(false);
                    });

                    üheksasKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[8], vastused[8][0], vastused[8][1], vastused[8][2], vastused[8][3], õiged[8]);
                        kümnesKüsimus.setDisable(false);
                    });

                    kümnesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[9], vastused[9][0], vastused[9][1], vastused[9][2], vastused[9][3], õiged[9]);
                        üheteistkümnesKüsimus.setDisable(false);
                    });

                    üheteistkümnesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[10], vastused[10][0], vastused[10][1], vastused[10][2], vastused[10][3], õiged[10]);
                        kaheteistkümnesKüsimus.setDisable(false);
                    });

                    kaheteistkümnesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[11], vastused[11][0], vastused[11][1], vastused[11][2], vastused[11][3], õiged[11]);
                        kolmeteistkümnesKüsimus.setDisable(false);
                    });

                    kolmeteistkümnesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[12], vastused[12][0], vastused[12][1], vastused[12][2], vastused[12][3], õiged[12]);
                        neljateistkümnesKüsimus.setDisable(false);
                    });

                    neljateistkümnesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[13], vastused[13][0], vastused[13][1], vastused[13][2], vastused[13][3], õiged[13]);
                        viieteistkümnesKüsimus.setDisable(false);
                    });

                    viieteistkümnesKüsimus.setOnMouseClicked(me1 -> { küsimus(küsimused[14], vastused[14][0], vastused[14][1], vastused[14][2], vastused[14][3], õiged[14]);
                    });

                    küsimusteNupud.getChildren().addAll(esimeneKüsimus, teineKüsimus, kolmasKüsimus, neljasKüsimus, viiesKüsimus, kuuesKüsimus, seitsmesKüsimus, kaheksasKüsimus, üheksasKüsimus, kümnesKüsimus, üheteistkümnesKüsimus, kaheteistkümnesKüsimus, kolmeteistkümnesKüsimus, neljateistkümnesKüsimus, viieteistkümnesKüsimus); // Nuppude lisamine.
                    küsimusteNupud.setAlignment(Pos.TOP_LEFT);
                    //juur1.setLeft(küsimusteNupud);
                    juur1.setLeft(paigutus);

                    Scene stseen21 = new Scene(juur1, 400, 500, Color.SNOW); // Stseeni loomine.
                    peaLava.setTitle("Küsimused"); // Tiitli loomine.
                    peaLava.setResizable(true);
                    peaLava.setScene(stseen21);
                    peaLava.show();
                });
        });
        peaLava.setTitle("Kes tahab saada miljonäriks?"); // Tiitli loomine.
        peaLava.setResizable(true);
        peaLava.setScene(stseen1);
        peaLava.show();
    }

    MediaPlayer mediaPlayer;
    public void music() {
        String s = "kahoot.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer  = new MediaPlayer(h);
        mediaPlayer.play();
    }

    // Meetod, kus luuakse küsimus. vv1 = valikvastus1, vv2 = valikvastus2 jne, õige = õige vastus.
    public static void küsimus(String küsimus1, String vv1, String vv2, String vv3, String vv4, String õige) {
        Stage lava = new Stage(); // Lava loomine.
        BorderPane juur = new BorderPane(); // Juure loomine.
        final String[] vastuseValik = new String[1]; // Siia salvestatakse valitud vastus.
        Label küsimus = new Label(küsimus1); // Küsimuse tekst.
        juur.setTop(küsimus);

        // Valikvastuste listi loomine ja lisamine juurele (keskele).
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(vv1, vv2, vv3, vv4);
        list.setItems(items);
        juur.setCenter(list);

        // Valitud valikvastuse salvestamine.
        list.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            vastuseValik[0] = newValue; // Salvestatakse valitud vastusevariant.
        });

        // Teine juur, mis pannakse esimese juure alla.
        BorderPane juur2 = new BorderPane();
        Button nupp = new Button("Esitan vastuse!"); // Lisatakse nupp vastuse esitamiseks.
        juur2.setCenter(nupp);
        juur.setBottom(juur2);

        nupp.setOnMousePressed(mouseEvent -> { // Kui nupule vajutatakse teeb järgnevat.
            if (vastuseValik[0].equals(õige)) { // Kui vastus on õige, väljastatakse ekraan õige informatsiooniga.
                // Aknasündmuse lisamine.
                nupp.setOnMouseClicked(me -> {
                    Stage kusimus = new Stage(); // Teise lava loomine.
                    Label label = new Label("Tubli! Vastasid õigesti."); // Teate loomine.
                    Button väljumiseNupp = new Button("Väljun!"); // Väljumisenupu loomine.

                    // Sündmuse lisamine nupule väljumiseNupp.
                    väljumiseNupp.setOnAction(event -> {
                        kusimus.hide();
                        lava.hide();
                    });

                    // Nupu grupeerimine.
                    FlowPane pane = new FlowPane(5, 5);
                    pane.setAlignment(Pos.CENTER);
                    pane.getChildren().add(väljumiseNupp);

                    // Teate ja nupu gruppi paigutamine.
                    VBox vBox = new VBox(10);
                    vBox.setAlignment(Pos.CENTER);
                    vBox.getChildren().addAll(label, pane);

                    // Stseeni loomine ja näitamine.
                    Scene stseen2 = new Scene(vBox);
                    kusimus.setScene(stseen2);
                    kusimus.show();
                });
            }
            else {
                // Aknasündmuse lisamine.
                nupp.setOnMouseClicked(me -> {
                    Stage kusimus = new Stage(); // Teise lava loomine.
                    Label label = new Label("Vale vastus!"); // Teate loomine.
                    Button väljumiseNupp = new Button("Väljun!"); // Nupu loomine.
                    Button uueKatseNupp = new Button("Proovin uuesti!"); // Nupu loomine.

                    // Sündmuse lisamine nupule väljumiseNupp.
                    väljumiseNupp.setOnAction(event -> {
                        kusimus.hide();
                        lava.hide();
                    });

                    // Sündmuse lisamine nupule uueKatseNupp.
                    uueKatseNupp.setOnAction(event -> {
                        lava.show();
                        kusimus.hide();
                    });

                    // Nuppude grupeerimine.
                    FlowPane pane = new FlowPane(10, 10);
                    pane.setAlignment(Pos.CENTER);
                    pane.getChildren().addAll(väljumiseNupp, uueKatseNupp);

                    // Teate ja nuppude gruppi paigutamine.
                    VBox vBox = new VBox(10);
                    vBox.setAlignment(Pos.CENTER);
                    vBox.getChildren().addAll(label, pane);

                    // Stseeni loomine ja näitamine.
                    Scene stseen2 = new Scene(vBox);
                    kusimus.setScene(stseen2);
                    kusimus.show();
                });
            }
        });
        Scene stseen1 = new Scene(juur, 500, 150, Color.SNOW); // Stseeni loomine.
        lava.setTitle("Küsimus"); // Tiitli loomine.
        lava.setResizable(false);
        lava.setScene(stseen1);
        lava.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Isendiväljad taimeri jaoks.
    static int interval;
    static Timer timer;

    static Andmed küsimusteAndmed(String failiteekond) throws Exception{

        // Luuakse massiivid ja maatriks, kuhu salvestatakse info iga küsimuse kohta.
        int[] võidetavSumma = new int[15];
        String[] küsimused = new String[15];
        String[][] vastused = new String[15][4];
        String[] vihjed = new String[15];
        String[] õigedVastused = new String[15];

        // Loetakse failist informatsioon ja salvestatakse igasse massiivi/maatriksi.
        java.io.File fail = new java.io.File(failiteekond);
        try (java.util.Scanner sc = new java.util.Scanner(fail, "UTF-8")) {
            int indeks = 0;
            while (sc.hasNextLine()) { // Tsükkel, kus salvestatakse igasse massiivi/maatriksi õige informatsioon.
                String rida = sc.nextLine();
                if (rida.startsWith("Summa: ")) {
                    String[] sõnad = rida.split(" ");
                    võidetavSumma[indeks] = Integer.parseInt(sõnad[1]);
                }
                else if (rida.startsWith("a) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][0] = sõnad[1];
                }
                else if (rida.startsWith("b) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][1] = sõnad[1];
                }
                else if (rida.startsWith("c) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][2] = sõnad[1];
                }
                else if (rida.startsWith("d) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][3] = sõnad[1];
                }
                else if (rida.startsWith("VIHJE: ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vihjed[indeks] = sõnad[1];
                }
                else if (rida.startsWith("õige: ")) {
                    String[] sõnad = rida.split(" ", 2);
                    õigedVastused[indeks] = sõnad[1];
                    indeks++;
                }
                else {
                    küsimused[indeks] = rida;
                }
            }
        }
        return new Andmed(võidetavSumma, küsimused, vastused, vihjed, õigedVastused);
    }
    // Meetod, kus pannakse vastusevariandid random järjekorda.
    public static String[] randomVastused(String [] variandid) {

        // Tehakse indeksite list.
        int[] vastused= {0,1,2};
        Random random = new Random();

        // Pannakse indeksid listis random järjekorda.
        for (int i = 0; i < vastused.length; i++) {
            int suvalineIndeks = random.nextInt(vastused.length);
            int suvaline= vastused[suvalineIndeks];
            vastused[suvalineIndeks] = vastused[i];
            vastused[i] = suvaline;}

        // Eelnevalt random indeksite listile pannakse vastavusse elemendid.
        String[] randomVariandid=new String[4];
        for (int i=0;i<3;i++) {
            randomVariandid[i] = variandid[vastused[i]];
        }
        randomVariandid[3]=variandid[3];
        return randomVariandid;
    }

    // Meetod taimeri jaoks.
    private static int setInterval() {
        if (interval == 1)
            timer.cancel(); // Lõpetatakse taimer.
        return --interval; // Tagastatakse ühe võrra väiksema arv.
    }

    // Meetod lisateksti faili lugemise ja massiivi panemise jaoks.
    public static String[] lisaTekst(String set) throws Exception{
        String failiteekond= "lisatekst"+set+".txt";
        java.io.File fail= new java.io.File(failiteekond);
        String[] lisa= new String[15];
        int i=0;
        try (java.util.Scanner sc = new java.util.Scanner(fail, "UTF-8")){
            while (sc.hasNextLine()) {
                String rida= sc.nextLine();
                lisa[i]=rida;
                i++;
            }
        }
        return lisa;
    }
}
