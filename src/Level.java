import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Level
{
    private static Image vergleichsStreifen;
    private static Color wand;
    private static Color spielerSpawn;
    private static Color gegnerSpawn;
    private static Color gegnerOnlyZugang;
    
    private double width;
    private double height;
    
    private Image kollisionsMap;
    private Image mapImage;
    
    private Vec2d spielerSpawnVec;
    private Vec2d gegnerSpawnVec;
    
    public static void init(String vergleichsStreifenURL)
    {
        vergleichsStreifen = ImageLoader.loadImage(vergleichsStreifenURL);
                
        spielerSpawn = getColor(vergleichsStreifen, 50, 50);
        wand = getColor(vergleichsStreifen, 150, 50);
        gegnerOnlyZugang = getColor(vergleichsStreifen, 250, 50);
        gegnerSpawn = getColor(vergleichsStreifen, 350, 50);
    }
    
    public static void colorToString(Color color)
    {
        System.out.print(color.getRed() + " " + color.getGreen() + " " + color.getBlue() + "\n");
    }
    
    public Level(String kollisionsMapURL, String mapImageURL)
    {
        kollisionsMap = ImageLoader.loadImage(kollisionsMapURL);
        mapImage = ImageLoader.loadImage(mapImageURL);
        width = kollisionsMap.getWidth();
        height = kollisionsMap.getHeight();
        
        for (double x = 0.0; x < width; x += 30)
        {
            for (double y = 0.0; y < height; y += 30)
            {
                if (getColorAufKollisionsMap(x + 15, y + 15).toString().equals(spielerSpawn.toString()))
                {
                    spielerSpawnVec = new Vec2d(x, y);
                }
                else if (getColorAufKollisionsMap(x + 15, y + 15).toString().equals(gegnerSpawn.toString()))
                {
                    gegnerSpawnVec = new Vec2d(x, y);
                }
            }
        }
    }
    
    public boolean istFeldFrei(double x, double y)
    {
        if (getColorAufKollisionsMap(x, y).toString().equals(wand.toString()) || getColorAufKollisionsMap(x, y).toString().equals(gegnerOnlyZugang.toString()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean istFeldFreiFürGeist(double x, double y)
    {
        if (getColorAufKollisionsMap(x, y).toString().equals(wand.toString()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public Color getColorAufKollisionsMap(double x, double y)
    {
        Color color;

        try 
        {
            color = kollisionsMap.getPixelReader().getColor((int) x, (int) y);
        } 
        catch (Exception e)
        {
            return null;
        }

        return color;
    }
    
    public static Color getColor(Image image, double x, double y)
    {
        Color color;

        try 
        {
            color = image.getPixelReader().getColor((int) x, (int) y);
        } 
        catch (Exception e)
        {
            return null;
        }

        return color;
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public Image getKollisionsMap()
    {
        return kollisionsMap;
    }

    public void setKollisionsMap(Image kollisionsMap)
    {
        this.kollisionsMap = kollisionsMap;
    }

    public Image getMapImage()
    {
        return mapImage;
    }

    public void setMapImage(Image mapImage)
    {
        this.mapImage = mapImage;
    }

    public Vec2d getSpielerSpawnVec()
    {
        return spielerSpawnVec;
    }

    public void setSpielerSpawnVec(Vec2d spielerSpawnVec)
    {
        this.spielerSpawnVec = spielerSpawnVec;
    }

    public Vec2d getGegnerSpawnVec()
    {
        return gegnerSpawnVec;
    }

    public void setGegnerSpawnVec(Vec2d gegnerSpawnVec)
    {
        this.gegnerSpawnVec = gegnerSpawnVec;
    }    
}
