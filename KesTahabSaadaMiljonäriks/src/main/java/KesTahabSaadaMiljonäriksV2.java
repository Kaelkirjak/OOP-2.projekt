import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Paths;
import java.util.Random;

public class KesTahabSaadaMiljonäriksV2 extends Application{

    String mängijaNimi; // Siia salvestatakse mängija nimi, mis sisestatakse kõige esimesena avaneval ekraanil.
    static int vihjeteArv = 3; // Näitab, mitu korda saab kasutaja veel kasutada vihjeid.
    MediaPlayer mediaPlayer; // Kasutatakse muusika mängimiseks programmi taustal.

    @Override
    public void start(Stage peaLava) throws Exception {

        music(); // Pannakse ulme bängertümm mängima.
        StackPane juur = new StackPane();
        Scene stseen1 = new Scene(juur, 900, 600);

        Image img = new Image("file:pilt.png"); // Võetakse pilt.
        ImageView imgView = new ImageView(img);
        juur.getChildren().add(imgView); // Lisatakse taustaks kasutas olev pilt..

        stseen1.widthProperty().addListener((objekt, vanaLaius, uusLaius) -> { // Kuular, mis kuulab, kas on stseeni akna laiust muudetud.
            double laius = uusLaius.doubleValue();
            double kõrgus = peaLava.getHeight();
            imgView.setFitHeight(kõrgus);
            imgView.setFitWidth(laius);
        });
        imgView.fitWidthProperty().bind(stseen1.widthProperty()); // Muudetakse pildi laius sõltuvaks stseeni laiusega.

        stseen1.heightProperty().addListener((observable, oldValue, newValue) -> { // Kuular, mis kuulab, kas on stseeni akna kõrgust muudetud.
            double laius = peaLava.getWidth();
            double kõrgus = newValue.doubleValue();
            imgView.setFitHeight(kõrgus);
            imgView.setFitWidth(laius);
        });
        imgView.fitHeightProperty().bind(stseen1.heightProperty()); // Muudetakse pildi kõrgus sõltuvaks stseeni kõrgusega.

        VBox tutvustus = new VBox(); // Tehakse VBox, kuhu tuleb kõik tutvustav sisu, mis on esimeses aknas.
        tutvustus.setAlignment(Pos.BASELINE_CENTER); // VBoxi joondamine.
        tutvustus.setSpacing(10); // Vahede lisamine.
        tutvustus.setPadding(new Insets(10, 10, 10, 10)); // "Polster" tekstile.

        // Tutvustav tekst koos oma sättetega(värv, font ja suurus jne).
        Text rida1 = new Text("Tere tulemast mängu \"kes tahab saada miljonäriks\"."); // Tutvustava teksti esimene rida.
        rida1.setFont(Font.font("Arial", 30));
        rida1.setFill(Color.YELLOW);
        rida1.setStrokeWidth(1);
        rida1.setStroke(Color.PURPLE);
        Text rida2 = new Text("Iga küsimuse jaoks on 1 minut aega, et sellele vastata."); // Tutvustava teksti teine rida.
        rida2.setFont(Font.font("Arial", 30));
        rida2.setFill(Color.YELLOW);
        rida2.setStrokeWidth(1);
        rida2.setStroke(Color.PURPLE);
        Text rida3 = new Text("Samuti on mäng läbi, kui valite vale vastuse."); // Tutvustava teksti kolmas rida.
        rida3.setFont(Font.font("Arial", 30));
        rida3.setFill(Color.YELLOW);
        rida3.setStrokeWidth(1);
        rida3.setStroke(Color.PURPLE);
        Text rida4 = new Text("Vastuseks valige üks valikvastustest."); // Tutvustava teksti neljas rida.
        rida4.setFont(Font.font("Arial", 30));
        rida4.setFill(Color.YELLOW);
        rida4.setStrokeWidth(1);
        rida4.setStroke(Color.PURPLE);
        Text rida5 = new Text("Palun sisestage oma nimi allolevasse kasti ja vajutage ENTER."); // Tutvustava teksti viies rida.
        rida5.setFont(Font.font("Arial", 30));
        rida5.setFill(Color.YELLOW);
        rida5.setStrokeWidth(1);
        rida5.setStroke(Color.PURPLE);
        Text rida6 = new Text("Kui ei jõua selle aja sees vastata on Teie jaoks mäng läbi."); // Tutvustava teksti kuues rida.
        rida6.setFont(Font.font("Arial", 30));
        rida6.setFill(Color.YELLOW);
        rida6.setStrokeWidth(1);
        rida6.setStroke(Color.PURPLE);

        TextField mängijaTäisnimi = new TextField(); // Tekstilahter mängija täisnime jaoks.
        mängijaTäisnimi.setMaxWidth(300); // Tekstilahtri maksimaalne suurus.

        Button alustamine = new Button("Alustan mänguga"); // Nupp millele vajutades avatakse uus aken, kus on küsimused.
        tutvustus.getChildren().addAll(rida1, rida2, rida6, rida3, rida4, rida5, mängijaTäisnimi, alustamine); // Kõigi ridadade, tekstivälja ja nupu lisamine.
        juur.getChildren().add(tutvustus); // VBoxi lisamine juurele.

        mängijaTäisnimi.setOnKeyPressed(nupuSündmus -> { // Kui vajutatakse ENTER klahvile, siis võetakse tekstiväljalt nimi ja salvestatakse saadud nimi.
            if (nupuSündmus.getCode().equals(KeyCode.ENTER)) { // Töötab ainult, siis kui vajutati ENTER klahvile.
                mängijaNimi = mängijaTäisnimi.getText(); // Salvestatakse mängija nimi.
            }
        });

        Mängija mängija = new Mängija(mängijaNimi, 0); // Luuakse mängija.

        Andmed informatsioon = küsimusteAndmed("küsimused1.txt"); // Kasutatakse klassi, et lugeda sisse küsimuste andmed.

        // Massiivid/maatriks oma vastava sisuga.
        int[] summa = informatsioon.getSumma();
        String[] küsimused = informatsioon.getKüsimused();
        String[][] vastused = informatsioon.getVastused();
        String[] vihjed = informatsioon.getVihjed();
        String[] õiged = informatsioon.getÕigedVastused();

        // Eraldi failis asuva lisainfo massiivi lisamine.
        String lisa[] = lisaTekst("1");

        alustamine.setOnMousePressed(mouseEvent -> { // Vajutades nupule tuleb uus aken, kus on küsimused.
            alustamine.setOnMouseClicked(me -> {
                BorderPane juur1 = new BorderPane(); // Uus paigutus.
                Stage küsimusteLava = new Stage();

                Label isikuNimi = new Label(); // Mängija nimi.
                isikuNimi.setText("Mängija: " + mängijaNimi + ". Võidetav summa: " + mängija.getVõidusumma()); // Tekstiks määratakse nimi ja võidetav summa.
                isikuNimi.setPadding(new Insets(5)); // "Polster" tekstile.

                juur1.setTop(isikuNimi); // Lisatakse juurele ülesse.

                VBox küsimusteNupud = new VBox(); // Luuakse vertikaalne paigutus küsimuste nuppude jaoks.
                küsimusteNupud.setPadding(new Insets(2, 2, 2, 2)); // "Polster" VBoxile.
                küsimusteNupud.setSpacing(5); // Vahede suurus.

                // Tekstid võidusummade kohta, mis saadakse küsimuse korrektsel vastamisel.
                Label võidusumma1 = new Label("Võidusumma: " + summa[0]);
                Label võidusumma2 = new Label("Võidusumma: " + summa[1]);
                Label võidusumma3 = new Label("Võidusumma: " + summa[2]);
                Label võidusumma4 = new Label("Võidusumma: " + summa[3]);
                Label võidusumma5 = new Label("Võidusumma: " + summa[4]);
                Label võidusumma6 = new Label("Võidusumma: " + summa[5]);
                Label võidusumma7 = new Label("Võidusumma: " + summa[6]);
                Label võidusumma8 = new Label("Võidusumma: " + summa[7]);
                Label võidusumma9 = new Label("Võidusumma: " + summa[8]);
                Label võidusumma10 = new Label("Võidusumma: " + summa[9]);
                Label võidusumma11 = new Label("Võidusumma: " + summa[10]);
                Label võidusumma12 = new Label("Võidusumma: " + summa[11]);
                Label võidusumma13 = new Label("Võidusumma: " + summa[12]);
                Label võidusumma14 = new Label("Võidusumma: " + summa[13]);
                Label võidusumma15 = new Label("Võidusumma: " + summa[14]);

                // Küsimuste nupud ja alguses ei saa nuppudele vajutada v.a esimene nupp.
                Button esimeneKüsimus = new Button("Küsimus 1");
                Button teineKüsimus = new Button("Küsimus 2");
                teineKüsimus.setDisable(true);
                Button kolmasKüsimus = new Button("Küsimus 3");
                kolmasKüsimus.setDisable(true);
                Button neljasKüsimus = new Button("Küsimus 4");
                neljasKüsimus.setDisable(true);
                Button viiesKüsimus = new Button("Küsimus 5");
                viiesKüsimus.setDisable(true);
                Button kuuesKüsimus = new Button("Küsimus 6");
                kuuesKüsimus.setDisable(true);
                Button seitsmesKüsimus = new Button("Küsimus 7");
                seitsmesKüsimus.setDisable(true);
                Button kaheksasKüsimus = new Button("Küsimus 8");
                kaheksasKüsimus.setDisable(true);
                Button üheksasKüsimus = new Button("Küsimus 9");
                üheksasKüsimus.setDisable(true);
                Button kümnesKüsimus = new Button("Küsimus 10");
                kümnesKüsimus.setDisable(true);
                Button üheteistkümnesKüsimus = new Button("Küsimus 11");
                üheteistkümnesKüsimus.setDisable(true);
                Button kaheteistkümnesKüsimus = new Button("Küsimus 12");
                kaheteistkümnesKüsimus.setDisable(true);
                Button kolmeteistkümnesKüsimus = new Button("Küsimus 13");
                kolmeteistkümnesKüsimus.setDisable(true);
                Button neljateistkümnesKüsimus = new Button("Küsimus 14");
                neljateistkümnesKüsimus.setDisable(true);
                Button viieteistkümnesKüsimus = new Button("Küsimus 15");
                viieteistkümnesKüsimus.setDisable(true);

                // Pannakse kõik valikvastused suvalisse järjekorda.
                String[] suvalineJärj1 = randomVastused(vastused[0]);
                String[] suvalineJärj2 = randomVastused(vastused[1]);
                String[] suvalineJärj3 = randomVastused(vastused[2]);
                String[] suvalineJärj4 = randomVastused(vastused[3]);
                String[] suvalineJärj5 = randomVastused(vastused[4]);
                String[] suvalineJärj6 = randomVastused(vastused[5]);
                String[] suvalineJärj7 = randomVastused(vastused[6]);
                String[] suvalineJärj8 = randomVastused(vastused[7]);
                String[] suvalineJärj9 = randomVastused(vastused[8]);
                String[] suvalineJärj10 = randomVastused(vastused[9]);
                String[] suvalineJärj11 = randomVastused(vastused[10]);
                String[] suvalineJärj12 = randomVastused(vastused[11]);
                String[] suvalineJärj13 = randomVastused(vastused[12]);
                String[] suvalineJärj14 = randomVastused(vastused[13]);
                String[] suvalineJärj15 = randomVastused(vastused[14]);

                // Nupule vajutades avaneb uus aken koos küsimusega ja valikvastustega. Lisaks avaneb järgmine nupp, millele saab vajutada.
                esimeneKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[0], suvalineJärj1[0], suvalineJärj1[1], suvalineJärj1[2], suvalineJärj1[3], õiged[0], mängija, summa[0], isikuNimi, mängijaNimi, vihjed[0]);
                    teineKüsimus.setDisable(false);
                });
                teineKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[1], suvalineJärj2[0], suvalineJärj2[1], suvalineJärj2[2], suvalineJärj2[3], õiged[1], mängija, summa[1], isikuNimi, mängijaNimi, vihjed[1]);
                    kolmasKüsimus.setDisable(false);
                });
                kolmasKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[2], suvalineJärj3[0], suvalineJärj3[1], suvalineJärj3[2], suvalineJärj3[3], õiged[2], mängija, summa[2], isikuNimi, mängijaNimi, vihjed[2]);
                    neljasKüsimus.setDisable(false);
                });
                neljasKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[3], suvalineJärj4[0], suvalineJärj4[1], suvalineJärj4[2], suvalineJärj4[3], õiged[3], mängija, summa[3], isikuNimi, mängijaNimi, vihjed[3]);
                    viiesKüsimus.setDisable(false);
                });
                viiesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[4], suvalineJärj5[0], suvalineJärj5[1], suvalineJärj5[2], suvalineJärj5[3], õiged[4], mängija, summa[4], isikuNimi, mängijaNimi, vihjed[4]);
                    kuuesKüsimus.setDisable(false);
                });
                kuuesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[5], suvalineJärj6[0], suvalineJärj6[1], suvalineJärj6[2], suvalineJärj6[3], õiged[5], mängija, summa[5], isikuNimi, mängijaNimi, vihjed[5]);
                    seitsmesKüsimus.setDisable(false);
                });
                seitsmesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[6], suvalineJärj7[0], suvalineJärj7[1], suvalineJärj7[2], suvalineJärj7[3], õiged[6], mängija, summa[6], isikuNimi, mängijaNimi, vihjed[6]);
                    kaheksasKüsimus.setDisable(false);
                });
                kaheksasKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[7], suvalineJärj8[0], suvalineJärj8[1], suvalineJärj8[2], suvalineJärj8[3], õiged[7], mängija, summa[7], isikuNimi, mängijaNimi, vihjed[7]);
                    üheksasKüsimus.setDisable(false);
                });
                üheksasKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[8], suvalineJärj9[0], suvalineJärj9[1], suvalineJärj9[2], suvalineJärj9[3], õiged[8], mängija, summa[8], isikuNimi, mängijaNimi, vihjed[8]);
                    kümnesKüsimus.setDisable(false);
                });
                kümnesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[9], suvalineJärj10[0], suvalineJärj10[1], suvalineJärj10[2], suvalineJärj10[3], õiged[9], mängija, summa[9], isikuNimi, mängijaNimi, vihjed[9]);
                    üheteistkümnesKüsimus.setDisable(false);
                });
                üheteistkümnesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[10], suvalineJärj11[0], suvalineJärj11[1], suvalineJärj11[2], suvalineJärj11[3], õiged[10], mängija, summa[10], isikuNimi, mängijaNimi, vihjed[10]);
                    kaheteistkümnesKüsimus.setDisable(false);
                });
                kaheteistkümnesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[11], suvalineJärj12[0], suvalineJärj12[1], suvalineJärj12[2], suvalineJärj12[3], õiged[11], mängija, summa[11], isikuNimi, mängijaNimi, vihjed[11]);
                    kolmeteistkümnesKüsimus.setDisable(false);
                });
                kolmeteistkümnesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[12], suvalineJärj13[0], suvalineJärj13[1], suvalineJärj13[2], suvalineJärj13[3], õiged[12], mängija, summa[12], isikuNimi, mängijaNimi, vihjed[12]);
                    neljateistkümnesKüsimus.setDisable(false);
                });
                neljateistkümnesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[13], suvalineJärj14[0], suvalineJärj14[1], suvalineJärj14[2], suvalineJärj14[3], õiged[13], mängija, summa[13], isikuNimi, mängijaNimi, vihjed[13]);
                    viieteistkümnesKüsimus.setDisable(false);
                });
                viieteistkümnesKüsimus.setOnMouseClicked(me1 -> {
                    küsimus(küsimused[14], suvalineJärj15[0], suvalineJärj15[1], suvalineJärj15[2], suvalineJärj15[3], õiged[14], mängija, summa[14], isikuNimi, mängijaNimi, vihjed[14]);
                });

                // Pannakse küsimuse nupp ja sellele küsimusele vastav võidusumma ühte HBoxi üksteise järele.
                HBox r1 = new HBox(esimeneKüsimus, võidusumma1);
                r1.setAlignment(Pos.BASELINE_LEFT);
                r1.setSpacing(4);
                r1.setPadding(new Insets(2, 2, 2, 2));
                HBox r2 = new HBox(teineKüsimus, võidusumma2);
                r2.setAlignment(Pos.BASELINE_LEFT);
                r2.setSpacing(4);
                r2.setPadding(new Insets(2, 2, 2, 2));
                HBox r3 = new HBox(kolmasKüsimus, võidusumma3);
                r3.setAlignment(Pos.BASELINE_LEFT);
                r3.setSpacing(4);
                r3.setPadding(new Insets(2, 2, 2, 2));
                HBox r4 = new HBox(neljasKüsimus, võidusumma4);
                r4.setAlignment(Pos.BASELINE_LEFT);
                r4.setSpacing(4);
                r4.setPadding(new Insets(2, 2, 2, 2));
                HBox r5 = new HBox(viiesKüsimus, võidusumma5);
                r5.setAlignment(Pos.BASELINE_LEFT);
                r5.setSpacing(4);
                r5.setPadding(new Insets(2, 2, 2, 2));
                HBox r6 = new HBox(kuuesKüsimus, võidusumma6);
                r6.setAlignment(Pos.BASELINE_LEFT);
                r6.setSpacing(4);
                r6.setPadding(new Insets(2, 2, 2, 2));
                HBox r7 = new HBox(seitsmesKüsimus, võidusumma7);
                r7.setAlignment(Pos.BASELINE_LEFT);
                r7.setSpacing(4);
                r7.setPadding(new Insets(2, 2, 2, 2));
                HBox r8 = new HBox(kaheksasKüsimus, võidusumma8);
                r8.setAlignment(Pos.BASELINE_LEFT);
                r8.setSpacing(4);
                r8.setPadding(new Insets(2, 2, 2, 2));
                HBox r9 = new HBox(üheksasKüsimus, võidusumma9);
                r9.setAlignment(Pos.BASELINE_LEFT);
                r9.setSpacing(4);
                r9.setPadding(new Insets(2, 2, 2, 2));
                HBox r10 = new HBox(kümnesKüsimus, võidusumma10);
                r10.setAlignment(Pos.BASELINE_LEFT);
                r10.setSpacing(4);
                r10.setPadding(new Insets(2, 2, 2, 2));
                HBox r11 = new HBox(üheteistkümnesKüsimus, võidusumma11);
                r11.setAlignment(Pos.BASELINE_LEFT);
                r11.setSpacing(4);
                r11.setPadding(new Insets(2, 2, 2, 2));
                HBox r12 = new HBox(kaheteistkümnesKüsimus, võidusumma12);
                r12.setAlignment(Pos.BASELINE_LEFT);
                r12.setSpacing(4);
                r12.setPadding(new Insets(2, 2, 2, 2));
                HBox r13 = new HBox(kolmeteistkümnesKüsimus, võidusumma13);
                r13.setAlignment(Pos.BASELINE_LEFT);
                r13.setSpacing(4);
                r13.setPadding(new Insets(2, 2, 2, 2));
                HBox r14 = new HBox(neljateistkümnesKüsimus, võidusumma14);
                r14.setAlignment(Pos.BASELINE_LEFT);
                r14.setSpacing(4);
                r14.setPadding(new Insets(2, 2, 2, 2));
                HBox r15 = new HBox(viieteistkümnesKüsimus, võidusumma15);
                r15.setAlignment(Pos.BASELINE_LEFT);
                r15.setSpacing(4);
                r15.setPadding(new Insets(2, 2, 2, 2));

                küsimusteNupud.getChildren().addAll(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15); // Nuppude lisamine.
                küsimusteNupud.setAlignment(Pos.TOP_LEFT); // Nuppude ja võidusummade positsioon.
                juur1.setLeft(küsimusteNupud); // Lisatakse juurele.

                Scene küsimusteStseen = new Scene(juur1, 300, 560); // Stseeni loomine.
                küsimusteLava.setTitle("Küsimused"); // Tiitli loomine.
                küsimusteLava.setResizable(true);
                küsimusteLava.setScene(küsimusteStseen);
                küsimusteLava.show();
            });
        });

        peaLava.setTitle("Kes tahab saada miljonäriks?"); // Tiitli loomine.
        peaLava.setResizable(true);
        peaLava.setScene(stseen1);
        peaLava.show();
    }

    /**
     * Meetod, mida kasutatakse muusika mängimiseks.
     */
    public void music() {
        String s = "kahoot.mp3"; // Mängitava faili nimi.
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    /**
     * Meetod, kus väljastatakse küsimus koos kõige muuga.
     * @param küsimus1 Antud küsimus.
     * @param vv1 Valikvastus 1.
     * @param vv2 Valikvastus 2.
     * @param vv3 Valikvastus 3.
     * @param vv4 Valikvastus 4.
     * @param õige Õige vastus.
     * @param mängija Klassi mängija isend.
     * @param summa Summa, mis on võimalik võita antud küsimuse õigesti vastamisel.
     * @param label2 Silt, kus näidatakse mängija nime ja tema rahalist seisu.
     * @param nimi Mängija nimi.
     * @param vihjeTekst Vihje, mis käib antud küsimuse kohta.
     */
    public static void küsimus(String küsimus1, String vv1, String vv2, String vv3, String vv4, String õige, Mängija mängija, int summa, Label label2, String nimi, String vihjeTekst) {
        Stage lava = new Stage(); // Lava loomine.
        BorderPane juur = new BorderPane(); // Juure loomine.
        final String[] vastuseValik = new String[1]; // Siia salvestatakse valitud vastus.

        Text text = new Text(); // Tekstiväli taimeri jaoks, kus näidatakse sekundeid.
        final int[] countDown = {60}; // Näitab, kui palju on aega vastamiseks. Antud juhul on selleks 1 minut ehk 60 sekundit.

        Label küsimus = new Label(küsimus1); // Küsimuse tekst.

        VBox timerJaKüsimus = new VBox(küsimus, text); // Luuakse VBox, kuhu pannakse küsimus ja tekstiväli taimeri jaoks.
        juur.setTop(timerJaKüsimus); // Lisatakse juurele.

        Timeline loendamine = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if (countDown[0] > 0) { // Kui loendur pole veel nulli jõudnud.
                countDown[0]--; // Lahutatakse maha üks sekund.
                text.setText("Taimer: " + countDown[0]); // Määratakse tekstiväljale, et mängija näeks palju tal veel aega on vastamiseks.
            } else { // Kui sekundid on jõudnud nullini, siis lõpetatakse mäng ja väljastatakse vastav teade.
                Stage aegLäbiLava = new Stage(); // Lava loomine.
                Label aegLäbiTeade = new Label("Jäite vastamisega hiljaks! Teie jaoks on mäng läbi."); // Teate loomine.
                Button väljumiseNupp = new Button("Lahkun"); // Nupu loomine.

                // Sündmuse lisamine nupule väljumiseNupp.
                väljumiseNupp.setOnAction(event -> { // Kui vajutatakse lõpetatakse programmi töö.
                    aegLäbiLava.hide();
                    lava.hide();
                    System.exit(0);
                });

                // Nuppude grupeerimine.
                FlowPane pane = new FlowPane(10, 10);
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().add(väljumiseNupp);

                // Teate ja nupu gruppi paigutamine.
                VBox vBox = new VBox(10);
                //vBox.setBackground(Background.EMPTY);
                //String style = "-fx-background-color: rgba(255, 8, 8, 0.5);";
                //vBox.setStyle(style);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(aegLäbiTeade, pane);

                // Stseeni loomine ja näitamine.
                Scene stseen2 = new Scene(vBox);
                aegLäbiLava.setScene(stseen2);
                aegLäbiLava.setTitle("Aeg läbi!");
                aegLäbiLava.show();
            }
        }));

        loendamine.setCycleCount(countDown[0] + 1); // Antakse ette arv, kui palju loetakse maha. Antud juhul 60 - (60 + 1).
        loendamine.play(); // Pannakse loendur tööle.


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
        Button nupp = new Button("Kinnitan valiku"); // Lisatakse nupp vastuse esitamiseks.
        if (vihjeteArv > 0) { // Kui vihjete arv pole veel null.
            Button vihje = new Button("Vihje"); // Luuakse nupp vihje jaoks.
            juur2.setLeft(vihje); // Asetatakse vasakule
            juur.setBottom(juur2); // Asetatakse alla.

            vihje.setOnMousePressed(mouseEvent -> { // Kui nupule vajutatakse väljastab vihje.
                // Aknasündmuse lisamine.
                vihje.setOnMouseClicked(me -> {
                    vihjeteArv = vihjeteArv - 1; // Vähendatakse vihjete arvu ühe võrra. NB, kui mitu korda ühe sama küsimuse ajal vajutada nupule, siis väheneb ikkagi vihjete arv.
                    Stage vihjePilt = new Stage(); // Lava loomine.
                    Label label = new Label(vihjeTekst); // Teate loomine.
                    Button väljumiseNupp = new Button("Selge"); // Väljumisenupu loomine.

                    // Sündmuse lisamine nupule väljumiseNupp.
                    väljumiseNupp.setOnAction(event -> {
                        vihjePilt.hide(); // Peidetakse vihje.
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
                    vihjePilt.setTitle("Vihje");
                    vihjePilt.setScene(stseen2);
                    vihjePilt.show();
                });
            });
        }
        juur2.setCenter(nupp); // Vastuse esitamise nupu asetamine.
        juur.setBottom(juur2);

        nupp.setOnMousePressed(mouseEvent -> { // Kui nupule vajutatakse teeb järgnevat.
            loendamine.stop(); // Pannakse loendur kinni.
            if (vastuseValik[0].equals(õige)) { // Kui vastus on õige, väljastatakse ekraan õige informatsiooniga.
                // Aknasündmuse lisamine.
                nupp.setOnMouseClicked(me -> {
                    mängija.setVõidusumma(summa); // Uuendatakse mängija võidusummat.
                    label2.setText("Mängija: " + nimi + ". Summa: " + mängija.getVõidusumma()); // Uuendatakse teksti.
                    Stage kusimus = new Stage(); // Lava loomine.
                    Label label = new Label("Tubli! Vastasid õigesti."); // Teate loomine.
                    Button väljumiseNupp = new Button("Jätkan"); // Väljumisenupu loomine.

                    // Sündmuse lisamine nupule väljumiseNupp.
                    väljumiseNupp.setOnAction(event -> {
                        kusimus.hide(); // Peidetakse küsimus.
                        lava.hide(); // Peidetakse teade.
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
            } else if (vastuseValik[0].startsWith("Ei ")) { // Kui valiti vastuseks saatest lahkumin vastava summaga.
                // Aknasündmuse lisamine.
                nupp.setOnMouseClicked(me -> {
                    Stage kusimus = new Stage(); // Lava loomine.
                    Label label = new Label("Mängija " + nimi + " lahkub saatest " + mängija.getVõidusumma() + " euroga."); // Teate loomine.
                    Button väljumiseNupp = new Button("Lahkun"); // Väljumisenupu loomine.

                    // Sündmuse lisamine nupule väljumiseNupp.
                    väljumiseNupp.setOnAction(event -> {
                        kusimus.hide(); // Peidetakse küsimus.
                        lava.hide(); // Peidetakse teade.
                        System.exit(0); // Lõpetatakse programmi töö.
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
            } else {
                // Aknasündmuse lisamine, kui valitud vastus oli vale.
                nupp.setOnMouseClicked(me -> {
                    Stage kusimus = new Stage(); // Lava loomine.
                    Label label = new Label("Vale vastus! Teie jaoks on mäng läbi."); // Teate loomine.
                    Button väljumiseNupp = new Button("Lahkun"); // Nupu loomine.

                    // Sündmuse lisamine nupule väljumiseNupp.
                    väljumiseNupp.setOnAction(event -> {
                        kusimus.hide();
                        lava.hide();
                        System.exit(0);
                    });

                    // Nuppude grupeerimine.
                    FlowPane pane = new FlowPane(10, 10);
                    pane.setAlignment(Pos.CENTER);
                    pane.getChildren().addAll(väljumiseNupp);

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

        Scene stseen1 = new Scene(juur, 1050, 153); // Stseeni loomine.
        lava.setTitle("Küsimus"); // Tiitli loomine.
        lava.setResizable(true);
        lava.setScene(stseen1);
        lava.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Meetod, kus toimub failist andmete lugemine ja nende salvestamine massiividesse ja maatriksisse.
     *
     * @param failiteekond Antud failiteekond.
     * @return Isend Andmed, mille abil saadakse andmed kätte.
     * @throws Exception
     */
    static Andmed küsimusteAndmed(String failiteekond) throws Exception {

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
                } else if (rida.startsWith("a) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][0] = sõnad[1];
                } else if (rida.startsWith("b) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][1] = sõnad[1];
                } else if (rida.startsWith("c) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][2] = sõnad[1];
                } else if (rida.startsWith("d) ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vastused[indeks][3] = sõnad[1];
                } else if (rida.startsWith("VIHJE: ")) {
                    String[] sõnad = rida.split(" ", 2);
                    vihjed[indeks] = sõnad[1];
                } else if (rida.startsWith("õige: ")) {
                    String[] sõnad = rida.split(" ", 2);
                    õigedVastused[indeks] = sõnad[1];
                    indeks++;
                } else {
                    küsimused[indeks] = rida;
                }
            }
        }
        return new Andmed(võidetavSumma, küsimused, vastused, vihjed, õigedVastused);
    }

    /**
     * Meetod, kus pannakse vastusevariandid random järjekorda.
     *
     * @param variandid Antud massiiv, kus on vastusevariandid.
     * @return Massiiv, kus on kõik vastusevariandid pandud suvalisse järjekorda.
     */
    public static String[] randomVastused(String[] variandid) {

        // Tehakse indeksite list.
        int[] vastused = {0, 1, 2};
        Random random = new Random();

        // Pannakse indeksid listis random järjekorda.
        for (int i = 0; i < vastused.length; i++) {
            int suvalineIndeks = random.nextInt(vastused.length);
            int suvaline = vastused[suvalineIndeks];
            vastused[suvalineIndeks] = vastused[i];
            vastused[i] = suvaline;
        }

        // Eelnevalt random indeksite listile pannakse vastavusse elemendid.
        String[] randomVariandid = new String[4];
        for (int i = 0; i < 3; i++) {
            randomVariandid[i] = variandid[vastused[i]];
        }
        randomVariandid[3] = variandid[3];
        return randomVariandid;
    }


    /**
     * Meetod lisateksti faili lugemise ja massiivi panemise jaoks.
     *
     * @param set Lisateksti number.
     * @return Massiiv, kuhu on salvestatud iga küsimuse kohta vastuse lisatekst.
     * @throws Exception
     */
    public static String[] lisaTekst(String set) throws Exception {
        String failiteekond = "lisatekst" + set + ".txt";
        java.io.File fail = new java.io.File(failiteekond);
        String[] lisa = new String[15];
        int i = 0;
        try (java.util.Scanner sc = new java.util.Scanner(fail, "UTF-8")) {
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                lisa[i] = rida;
                i++;
            }
        }
        return lisa;
    }
}