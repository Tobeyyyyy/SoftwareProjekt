
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class Gui extends Application
{

    private Canvas           canvas1                  = new Canvas();
    private ImageButton      SPWRButton               = new ImageButton("StartButton.png", this, 0);
    private ImageButton      schwierigkeitsgradButton = new ImageButton("EinfachButton.png", this, 1);
    private ImageButton      scoreBoardButton         = new ImageButton("ScoreBoardButton.png", this, 2);
    private ImageButton      okButton                 = new ImageButton("okButton.png", this, 3);
    private ImageButton      okButton2                = new ImageButton("okButton.png", this, 4);

    private ImageView        hintergrundBild          = new ImageView("Hintergrund.png");
    private ImageView        titel                    = new ImageView("Titel.png");
    private ImageView        endScreen                = new ImageView("Endscreen.png");
    private ImageView        scoreboard               = new ImageView("Scoreboard.png");

    private ArrayList<Label> scoreboardLabels         = new ArrayList<Label>();

    private Label            schwierigkeitsAnzeige    = new Label();
    private Label            scoreLinks               = new Label();
    private Label            scoreRechts              = new Label();
    private Label            endScreenScore           = new Label();

    private TextField        textField                = new TextField();

    private GraphicsContext  gc                       = canvas1.getGraphicsContext2D();

    private final int        SPIELFELD_WIDTH          = 960;
    private final int        SPIELFELD_HEIGHT         = 720;
    private final int        WINDOW_WIDTH             = 1250;
    private final int        WINDOW_HEIGHT            = 710;
    private final String     FENSTER_NAME             = "Pacman 2.0";

    private PacmanController controller               = new PacmanController(this);

    private boolean          needsUpdate              = false;

    private Bestenliste      list                     = new Bestenliste();

    private Pane             root;

    public void start(Stage primaryStage)
    {
        root = new Pane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setResizable(false);

        root.getChildren().add(hintergrundBild);
        root.getChildren().add(titel);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setColor(Color.BLACK);

        schwierigkeitsAnzeige.setFont(new Font("04b_19", 36));
        schwierigkeitsAnzeige.setLayoutX(1039);
        schwierigkeitsAnzeige.setLayoutY(285);
        schwierigkeitsAnzeige.setText("EINFACH");
        schwierigkeitsAnzeige.setTextFill(new Color(1, 1, 1, 1.0));
        schwierigkeitsAnzeige.setEffect(shadow);

        scoreLinks.setFont(new Font("04b_19", 36));
        scoreLinks.setLayoutX(970);
        scoreLinks.setLayoutY(431);
        scoreLinks.setText("SCORE: ");
        scoreLinks.setTextFill(new Color(0.9, 0.9, 0.9, 1.0));
        scoreLinks.setEffect(shadow);

        scoreRechts.setFont(new Font("04b_19", 43));
        scoreRechts.setLayoutX(1100);
        scoreRechts.setLayoutY(427);
        scoreRechts.setText("");
        scoreRechts.setTextFill(new Color(0.9, 0.9, 0.3, 1.0));
        scoreRechts.setEffect(shadow);

        root.getChildren().add(schwierigkeitsAnzeige);
        root.getChildren().add(scoreLinks);
        root.getChildren().add(scoreRechts);

        titel.setLayoutX(962);
        titel.setLayoutY(-20);

        canvas1.setLayoutX(0);
        canvas1.setLayoutY(0);
        root.getChildren().add(canvas1);
        canvas1.setWidth(SPIELFELD_WIDTH);
        canvas1.setHeight(SPIELFELD_HEIGHT);

        endScreen.setLayoutX(265);
        endScreen.setLayoutY(200);

        root.getChildren().add(endScreen);
        root.getChildren().add(okButton);

        scoreboard.setLayoutX(960);
        scoreboard.setLayoutY(150);

        okButton.setLayoutX(525);
        okButton.setLayoutY(400);

        endScreenScore.setFont(new Font("04b_19", 43));
        endScreenScore.setLayoutX(460);
        endScreenScore.setLayoutY(330);
        endScreenScore.setText("");
        endScreenScore.setTextFill(new Color(0.9, 0.9, 0.3, 1.0));
        endScreenScore.setEffect(shadow);

        SPWRButton.setLayoutX(1025);
        SPWRButton.setLayoutY(200);

        scoreBoardButton.setLayoutX(1025);
        scoreBoardButton.setLayoutY(550);

        schwierigkeitsgradButton.setLayoutX(1025);
        schwierigkeitsgradButton.setLayoutY(280);

        root.getChildren().add(SPWRButton);
        root.getChildren().add(schwierigkeitsgradButton);
        root.getChildren().add(scoreBoardButton);
        root.getChildren().add(endScreenScore);
        root.getChildren().add(textField);

        textField.setLayoutX(330);
        textField.setLayoutY(415);
        textField.setMinWidth(100);
        textField.setVisible(false);

        okButton2.setLayoutX(1065);
        okButton2.setLayoutY(555);

        root.getChildren().add(scoreboard);
        root.getChildren().add(okButton2);

        hideScoreboard();
        hideEndscreen();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event)
            {
                controller.keyPressed(event);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event)
            {
                controller.keyReleased(event);
            }
        });

        Thread mainThread = new Thread(new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    controller.mainLoop();
                }
            }
        });
        mainThread.start();

        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setTitle(FENSTER_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void refresh()
    {
        switch (controller.getStatus())
        {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                SPWRButton.setVisible(false);
                showEndscreen();
                break;
        }
    }

    public void showScoreboard()
    {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setColor(Color.BLACK);

        int y = 245;

        for (Nutzer n : list.getNutzer())
        {
            Label l = new Label();
            scoreboardLabels.add(l);

            l.setFont(new Font("04b_19", 25));
            l.setLayoutX(1040);
            l.setLayoutY(y);
            l.setText(n.getName() + ":  " + n.getTopPunktZahl());
            l.setTextFill(new Color(0.9, 0.9, 0.9, 1.0));
            l.setEffect(shadow);
            root.getChildren().add(l);
            y += 30;
        }

        scoreboard.setVisible(true);
        okButton2.setVisible(true);
    }

    public void hideScoreboard()
    {
        scoreboard.setVisible(false);
        okButton2.setVisible(false);

        for (Label l : scoreboardLabels)
        {
            root.getChildren().remove(l);
        }

        scoreboardLabels.clear();
    }

    public void hideEndscreen()
    {
        endScreen.setVisible(false);
        endScreenScore.setVisible(false);
        okButton.setVisible(false);
    }

    public void showEndscreen()
    {
        if (controller.getPunkteStand() > list.getHöchstePunktzahl())
        {
            textField.setVisible(true);
        }

        endScreenScore.setText(Integer.toString(controller.getPunkteStand()));
        endScreen.setVisible(true);
        endScreenScore.setVisible(true);
        okButton.setVisible(true);
    }

    public void buttonPressed(int number)
    {
        switch (number)
        {
            case 0:
                switch (controller.getStatus())
                {
                    case 0:
                        controller.restart();
                        schwierigkeitsgradButton.setVisible(false);
                        SPWRButton.setImage("PauseButton.png");
                        break;
                    case 1:
                        controller.setStatus(2);
                        break;
                    case 2:
                        controller.setStatus(1);
                        SPWRButton.setImage("PauseButton.png");
                        break;
                    case 3:
                        controller.restart();
                        schwierigkeitsgradButton.setVisible(false);
                        SPWRButton.setImage("PauseButton.png");
                        break;
                }
                break;
            case 1:
                switch (controller.getSchwierigkeitsGrad())
                {
                    case 0:
                        schwierigkeitsgradButton.setImage("MittelButton.png");
                        schwierigkeitsAnzeige.setText("MITTEL");
                        controller.setSchwierigkeitsGrad(1);
                        break;
                    case 1:
                        schwierigkeitsgradButton.setImage("SchwerButton.png");
                        schwierigkeitsAnzeige.setText("SCHWER");
                        controller.setSchwierigkeitsGrad(2);
                        break;
                    case 2:
                        schwierigkeitsgradButton.setImage("EinfachButton.png");
                        schwierigkeitsAnzeige.setText("EINFACH");
                        controller.setSchwierigkeitsGrad(0);
                        break;
                }

                break;
            case 2:
                showScoreboard();
                break;
            case 3:
                SPWRButton.setImage("RestartButton.png");
                schwierigkeitsgradButton.setVisible(true);
                SPWRButton.setVisible(true);
                if (textField.isVisible() && textField.getText().trim().length() != 0
                        && textField.getText().matches("[a-zA-Z]*"))
                {
                    list.addNutzer(new Nutzer(textField.getText(), controller.getPunkteStand()));
                    list.speichern();
                    textField.setVisible(false);
                } else if (textField.isVisible())
                {
                    textField.setVisible(false);
                }
                hideEndscreen();
                break;
            case 4:
                hideScoreboard();
                break;
        }
    }

    public boolean isNeedsUpdate()
    {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate)
    {
        this.needsUpdate = needsUpdate;
    }

    public Label getScoreRechts()
    {
        return scoreRechts;
    }

    public void setScoreRechts(Label scoreRechts)
    {
        this.scoreRechts = scoreRechts;
    }

    public int getSPIELFELD_HEIGHT()
    {
        return SPIELFELD_HEIGHT;
    }

    public int getSPIELFELD_WIDTH()
    {
        return SPIELFELD_WIDTH;
    }

    public GraphicsContext getGc()
    {
        return gc;
    }

    public void setGc(GraphicsContext gc)
    {
        this.gc = gc;
    }
}
