import java.awt.Rectangle;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class PacmanController
{
    private int                     status                  = 0;                                // 0: vor Spielstart
                                                                                                // 1: laufendes Spiel
                                                                                                // 2: pause
                                                                                                // 3: nach Spielende

    private static long             startNanoTime1;
    private static long             startNanoTime2;
    private static long             startNanoTime3;
    private static int              fpsZähler               = 0;

    private Player                  player;
    private double                  playerTPS               = 60.0;

    private Level                   level;

    private ArrayList<Geist>        geister;
    private boolean                 geisterAngreifbar       = false;

    private ArrayList<SpeedPowerUp> powerUps                = new ArrayList<SpeedPowerUp>();
    private boolean                 speedAktiviert          = false;
    private float                   speedPowerUpTimer       = 0;

    private int                     angreifbarTimer         = 0;
    private int                     gegnerNochNichtGespawnt = 6;
    private float                   spawnStartZeit          = System.nanoTime();

    private ArrayList<Coin>         coins;

    private int                     requestedDirection      = -1;

    Gui                             guiRef;
    private boolean                 guiRefreshed            = false;

    private int                     schwierigkeitsGrad      = 0;                                // 0: Einfach
                                                                                                // 1: Mittel
                                                                                                // 2: Schwer
    
    private int punkteStand = 0;

    PacmanController(Gui guiRef)
    {
        this.guiRef = guiRef;

        startNanoTime1 = System.nanoTime();
        startNanoTime2 = System.nanoTime();

        init();
    }

    public void mainLoop()
    {
        if (startNanoTime3 + 1000000000.0 / playerTPS < System.nanoTime())
        {
            playerTick();
            startNanoTime3 = System.nanoTime();
        }
        if (startNanoTime1 + 1000000000.0 / 60.0 < System.nanoTime())
        {
            render(guiRef.getGc());
            tick();
            fpsZähler++;
            startNanoTime1 = System.nanoTime();
        }
        if (startNanoTime2 + 1000000000.0 < System.nanoTime())
        {
            System.out.println(fpsZähler);
            startNanoTime2 = System.nanoTime();
            fpsZähler = 0;
        }
    }

    public void keyPressed(KeyEvent event)
    {
        if (event.getCode() == KeyCode.W)
        {
            requestedDirection = 0;
        } else if (event.getCode() == KeyCode.A)
        {
            requestedDirection = 3;
        } else if (event.getCode() == KeyCode.S)
        {
            requestedDirection = 2;
        } else if (event.getCode() == KeyCode.D)
        {
            requestedDirection = 1;
        }
    }

    public void keyReleased(KeyEvent event)
    {
        
    }

    public void init()
    {
        Coin.setTextur(ImageLoader.loadImage("Coin.png"));
        
        Level.init("VergleichsStreifen.png");
        level = new Level("Background.png", "Background2.png");

        player = new Player(level.getSpielerSpawnVec().getX(), level.getSpielerSpawnVec().getY(), 30, "Pacman.png",
                "Pacman2.png");

        geister = new ArrayList<Geist>();
        coins = new ArrayList<Coin>();

        geisterAngreifbar = false;
    }

    public void restart()
    {
        coins.clear();
        powerUps.clear();
        
        setPunkteStand(0);
        
        switch (schwierigkeitsGrad)
        {
            case 0:
                gegnerNochNichtGespawnt = 6;
                break;
            case 1:
                gegnerNochNichtGespawnt = 7;
                break;
            case 2:
                gegnerNochNichtGespawnt = 40;
                break;
        }

        speedAktiviert = false;

        geister = new ArrayList<Geist>();
        geisterAngreifbar = false;
        coins = new ArrayList<Coin>();

        level = new Level("Background.png", "Background2.png");

        player = new Player(level.getSpielerSpawnVec().getX(), level.getSpielerSpawnVec().getY(), 30, "Pacman.png",
                "Pacman2.png");
        player.setRichtung(1);

        for (int x = 0; x < guiRef.getSPIELFELD_WIDTH(); x += 30)
        {
            for (int y = 0; y < guiRef.getSPIELFELD_HEIGHT(); y += 30)
            {
                if (level.getColorAufKollisionsMap(x + 5, y + 5).toString().equals(Color.WHITE.toString()))
                {
                    if ((int) (Math.random() * 100) % 2 == 0)
                    {
                        coins.add(new Coin(x, y));
                    } else if ((int) (Math.random() * 100) % 15 == 0)
                    {
                        powerUps.add(new SpeedPowerUp(x, y, "SpeedPowerUp.png"));
                    }
                }
            }
        }

        status = 1;
    }

    private void render(GraphicsContext gc)
    {
        gc.drawImage(level.getMapImage(), 0, 0);

        for (int i = 0; i < coins.size(); i++)
        {
            gc.drawImage(Coin.getTextur(), coins.get(i).getPosition().getX(), coins.get(i).getPosition().getY());
        }

        for (int i = 0; i < powerUps.size(); i++)
        {
            gc.drawImage(powerUps.get(i).getTextur(), powerUps.get(i).getPosition().getX(),
                    powerUps.get(i).getPosition().getY());
        }
        gc.drawImage(player.getMomentaneTextur(), player.getPosition().getX(), player.getPosition().getY());
        
        for (int i = 0; i < geister.size(); i++)
        {
            gc.drawImage(geister.get(i).getMomentaneTextur(), geister.get(i).getPosition().getX(),
                    geister.get(i).getPosition().getY());
        }
    }

    public void playerTick()
    {
        if (status == 1)
        {
            player.tick(this);

            Rectangle playerRect = new Rectangle((int) player.getPosition().getX(), (int) player.getPosition().getY(), 30, 30);

            for (int i = 0; i < coins.size(); i++)
            {
                if (playerRect.intersects(new Rectangle((int) coins.get(i).getPosition().getX(),
                        (int) coins.get(i).getPosition().getY(), 30, 30)))
                {

                    punkteStand++;
                    coins.remove(i);
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            guiRef.getScoreRechts().setText(Integer.toString(punkteStand));
                        }
                    });
                }
            }

            for (int i = 0; i < powerUps.size(); i++)
            {
                if (playerRect.intersects(new Rectangle((int) powerUps.get(i).getPosition().getX(),
                        (int) powerUps.get(i).getPosition().getY(), 30, 30)))
                {
                    powerUps.remove(i);
                    playerTPS = 90.0;
                    speedAktiviert = true;
                    speedPowerUpTimer = 60 * 5;
                }
            }

            if (speedPowerUpTimer > 0)
            {
                speedPowerUpTimer--;
            }
            if (speedPowerUpTimer == 1)
            {
                speedAktiviert = false;
                playerTPS = 60.0;
            }

            if (punkteStand % 20 == 0 && angreifbarTimer == 0 && punkteStand != 0)
            {
                System.out.println(punkteStand);
                angreifbarTimer = 60 * 10;
                geisterAngreifbar = true;
                playerTPS = 90.0;

                for (int i = 0; i < geister.size(); i++)
                {
                    geister.get(i).setRichtung(geister.get(i).getGegenRichtung(geister.get(i).getRichtung()));
                }
            }

            if (angreifbarTimer > 0)
            {
                angreifbarTimer--;
            } else if (angreifbarTimer == 0)
            {
                geisterAngreifbar = false;
                if (!speedAktiviert)
                {
                    playerTPS = 60.0;
                }
            }

            for (int i = 0; i < geister.size(); i++)
            {
                if (playerRect.intersects(new Rectangle((int) geister.get(i).getPosition().getX(),
                        (int) geister.get(i).getPosition().getY(), 30, 30)))
                {
                    if (geisterAngreifbar)
                    {
                        geister.remove(i);
                        i--;
                        gegnerNochNichtGespawnt++;
                    } else
                    {
                        if (status != 3)
                        {
                            setStatus(3);
                        }
                        if (!guiRefreshed)
                        {
                            guiRefreshed = true;
                            guiRef.setNeedsUpdate(true);
                            Platform.runLater(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    guiRef.refresh();
                                }
                            });
                        }
                        gameOver(0);
                    }
                }
            }
            if (coins.size() == 0)
            {
                gameOver(1);
            }
        }
    }

    public void tick()
    {
        if (status == 1)
        {
            if (System.nanoTime() > spawnStartZeit + 1000000000 && gegnerNochNichtGespawnt >= 1)
            {
                geister.add(new Geist(level.getGegnerSpawnVec().getX(), level.getGegnerSpawnVec().getY(), this,
                        "Ghost.png", "Ghost2.png"));
                gegnerNochNichtGespawnt--;
                spawnStartZeit = System.nanoTime();
            }

            for (int i = 0; i < geister.size(); i++)
            {
                geister.get(i).tick();
            }
        }
    }

    public void gameOver(int end)
    {
        if (end == 0)
        {
            System.err.println("Player touched by Enemy");
        } else
        {
            System.err.println("Player collected all coins");
        }
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public int getRequestedDirection()
    {
        return requestedDirection;
    }

    public void setRequestedDirection(int requestedDirection)
    {
        this.requestedDirection = requestedDirection;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        guiRefreshed = false;
        this.status = status;
    }

    public int getSchwierigkeitsGrad()
    {
        return schwierigkeitsGrad;
    }

    public void setSchwierigkeitsGrad(int schwierigkeitsGrad)
    {
        this.schwierigkeitsGrad = schwierigkeitsGrad;
    }

    public boolean isGeisterAngreifbar()
    {
        return geisterAngreifbar;
    }

    public void setGeisterAngreifbar(boolean geisterAngreifbar)
    {
        this.geisterAngreifbar = geisterAngreifbar;
    }

    public int getPunkteStand()
    {
        return punkteStand;
    }
    
    public void setPunkteStand(int punkteStand)
    {
        this.punkteStand = punkteStand;
    }
}
