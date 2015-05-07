package todaysmenu.pondar.org.todaysmenu;

/*
 * This class describes one item of food - used
 * for statistics. We want to sort them also - so implements the comparable interface.
 */
public class Item implements Comparable<Item> {
	private String name;
	private int freq;
	private int total;
	private float percent;
	
	public Item(String name, int freq, int total) {
		this.name = name;
		this.freq = freq;
		this.total = total;
		this.percent = 100f*((float) freq / (float) total);
	}

	public String getName()
	{
		return name;
	}
	
	public float getPercent() {
		return percent;
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public int getFreq() {
		return freq;
	}

    /*
        we sort the menu after which one was the most eaten dish, so we sort after
        the frequencies of the items.
     */
	@Override
	public int compareTo(Item other) {
		if (freq<other.getFreq())
			return 1;
		else if (freq==other.getFreq())
			return 0;
		else 
			return -1;
	}
}
