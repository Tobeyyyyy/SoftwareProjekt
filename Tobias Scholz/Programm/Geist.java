import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Geist extends Entity
{

    private ArrayList<Image> texturen;
    
    private PacmanController controllerRef;

    public Geist(double x, double y, PacmanController controllerRef, String... texturenURLs)
    {
        super(x, y, 1);
        
        this.controllerRef = controllerRef;

        texturen = new ArrayList<Image>();

        for (int i = 0; i < texturenURLs.length; i++)
        {
            this.texturen.add(ImageLoader.loadImage(texturenURLs[i]));
        }
    }

    public void tick()
    {
        if (position.getX() % 30 == 0 && position.getY() % 30 == 0)            
        {     
            int vorherigeRichtung = richtung;
            richtung = -1;
            
            ArrayList<Integer> bevorzugteRichtungen = new ArrayList<Integer>();
            
            if (!controllerRef.isGeisterAngreifbar())
            {
                if (controllerRef.getPlayer().getPosition().getX() > position.getX())
                {
                    bevorzugteRichtungen.add(1);
                    
                } else if (controllerRef.getPlayer().getPosition().getX() < position.getX())
                {
                    bevorzugteRichtungen.add(3);
                }
                if (controllerRef.getPlayer().getPosition().getY() > position.getY())
                {
                    bevorzugteRichtungen.add(2);
                    
                } else if (controllerRef.getPlayer().getPosition().getY() < position.getY())
                {
                    bevorzugteRichtungen.add(0);
                }
            }
            else
            {
                if (controllerRef.getPlayer().getPosition().getX() < position.getX())
                {
                    bevorzugteRichtungen.add(1);
                    
                } else if (controllerRef.getPlayer().getPosition().getX() > position.getX())
                {
                    bevorzugteRichtungen.add(3);
                }
                if (controllerRef.getPlayer().getPosition().getY() < position.getY())
                {
                    bevorzugteRichtungen.add(2);
                    
                } else if (controllerRef.getPlayer().getPosition().getY() > position.getY())
                {
                    bevorzugteRichtungen.add(0);
                }
            }           

            ArrayList<Integer> freieRichtungen = new ArrayList<Integer>();
            if (controllerRef.getLevel().istFeldFreiFürGeist(position.getX(), position.getY() - 30.0) && 0 != getGegenRichtung(vorherigeRichtung))
            {
                freieRichtungen.add(0);
            }
            if (controllerRef.getLevel().istFeldFreiFürGeist(position.getX() + 30.0, position.getY()) && 1 != getGegenRichtung(vorherigeRichtung))
            {
                freieRichtungen.add(1);
            }
            if (controllerRef.getLevel().istFeldFreiFürGeist(position.getX(), position.getY() + 30.0) && 2 != getGegenRichtung(vorherigeRichtung))
            {
                freieRichtungen.add(2);
            }
            if (controllerRef.getLevel().istFeldFreiFürGeist(position.getX() - 30.0, position.getY()) && 3 != getGegenRichtung(vorherigeRichtung))
            {
                freieRichtungen.add(3);
            }

            if ((int) (Math.random() * 100) % (controllerRef.getSchwierigkeitsGrad() == 0 ? 4 : controllerRef.getSchwierigkeitsGrad() == 1 ? 6 : 8) == 0)
            {
                Random r = new Random();
                richtung = freieRichtungen.get(r.nextInt(freieRichtungen.size()));
            } 
            else
            {
                for (int x = 0; x < bevorzugteRichtungen.size(); x++)
                {
                    for (int y = 0; y < freieRichtungen.size(); y++)
                    {
                        if (bevorzugteRichtungen.get(x) == freieRichtungen.get(y) && freieRichtungen.get(y) != getGegenRichtung(vorherigeRichtung))
                        {
                            richtung = freieRichtungen.get(y);
                        }
                    }
                }
                
                if (richtung == -1)
                {
                    for (int i = 0; i < freieRichtungen.size(); i++)
                    {
                        if (freieRichtungen.get(i) != getGegenRichtung(vorherigeRichtung))
                        {
                            richtung = freieRichtungen.get(i);
                        }
                    }
                    
                    if (richtung == -1 && freieRichtungen.size() >= 1)
                    {
                        richtung = freieRichtungen.get(0);
                    }
                }
            }
            
            if (richtung == -1)
            {
                richtung = getGegenRichtung(vorherigeRichtung);
            }
        }                
        
        switch (richtung)
        {
            case 0:
                position.setY(position.getY() - 1.0);
                break;
            case 1:
                position.setX(position.getX() + 1.0);
                break;
            case 2:
                position.setY(position.getY() + 1.0);
                break;
            case 3:
                position.setX(position.getX() - 1.0);
                break;
        }
    }
    
    public int getGegenRichtung(int richtung)
    {
        switch (richtung)
        {
            case 0: return 2; 
            case 1: return 3; 
            case 2: return 0; 
            case 3: return 1;
        }
        return -1;
    }

    public Image getMomentaneTextur()
    {
        return !controllerRef.isGeisterAngreifbar() ? texturen.get(0) : texturen.get(1);
    }
}
