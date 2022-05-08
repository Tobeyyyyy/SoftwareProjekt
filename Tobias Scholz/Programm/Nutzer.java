
public class Nutzer implements Comparable<Nutzer> {
	
	private String name;
	private int topPunktZahl;
	
	public Nutzer(String name, int topPunktZahl) {
		super();
		this.name = name;
		this.topPunktZahl = topPunktZahl;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTopPunktZahl() {
		return topPunktZahl;
	}

	public void setTopPunktZahl(int topPunktZahl) {
		this.topPunktZahl = topPunktZahl;
	}

    @Override
    public int compareTo(Nutzer arg0)
    {
        if (topPunktZahl > arg0.topPunktZahl) return -1;
        else return 1;
    }
}
