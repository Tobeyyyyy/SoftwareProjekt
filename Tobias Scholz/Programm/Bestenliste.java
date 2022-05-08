import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Bestenliste {
	
	private ArrayList<Nutzer> nutzer;
	private FileReader reader;
	
	private int h�chstePunktzahl = 0;

    public Bestenliste()
	{
		nutzer = new ArrayList<Nutzer>();
		reader = new FileReader("bestenliste.txt");
		laden();
	}

    public void laden()
	{
    	try {
    		String output = reader.loadFile();
    		
    		if (output.length() != 0)
    		{
    		    String[] splitString1 = output.split(";");        
    	        
    	        for (int i = 0; i < splitString1.length; i++)
    	        {
    	            String[] splitString2 = splitString1[i].split(":");     
    	               
    	            if (Integer.valueOf(splitString2[1]) > h�chstePunktzahl)
    	            {
    	                h�chstePunktzahl = Integer.valueOf(splitString2[1]);
    	            }
    	            nutzer.add(new Nutzer(splitString2[0], Integer.valueOf(splitString2[1])));
    	        }   
    	        sortieren(); 
    		}	
    	} catch (Exception e)
    	{
    		reader.writeFile("");    		
    	}		
	}
    
    public void sortieren()
    {
        Collections.sort(nutzer);
    }
	
	public void speichern()
	{
	    String saveString = "";
	    
		for (Nutzer n : nutzer)
		{
		    saveString += n.getName() + ":" + n.getTopPunktZahl() + ";";
		}
		
		reader.writeFile(saveString);
	}
	
	public void addNutzer(Nutzer newNutzer)
	{
	    if (newNutzer.getTopPunktZahl() > h�chstePunktzahl)
	    {
	        h�chstePunktzahl = newNutzer.getTopPunktZahl();
	    }
	    nutzer.add(newNutzer);
	    sortieren();
	}
    
    public ArrayList<Nutzer> getNutzer()
    {
        return nutzer;
    }

    public void setNutzer(ArrayList<Nutzer> nutzer)
    {
        this.nutzer = nutzer;
    }
    
    public int getH�chstePunktzahl()
    {
        return h�chstePunktzahl;
    }

    public void setH�chstePunktzahl(int h�chstePunktzahl)
    {
        this.h�chstePunktzahl = h�chstePunktzahl;
    }
}
