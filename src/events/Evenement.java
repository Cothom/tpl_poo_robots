package events;

public abstract class Evenement {
    private long date;

    public Evenement(long pDate) {
	if (pDate < 0)
	    throw new IllegalArgumentException("Argument invalide : date négative.");
	this.date = pDate;
    }

    public long getDate() {
	return this.date;
    }

    public abstract void execute();
}
	
