package FormulateDirectory;

import java.util.ArrayList;

public class Folder {

	private String data;
	private ArrayList<Folder> subFolders;
	private int depth;
	private boolean skip;
	
	public Folder (String data) {
		
		this.data = data;
		
		subFolders = new ArrayList<Folder>();
		
		skip = false;
		
	}
	
	public void setData (String data) {
		
		this.data = data;
		
	}
	
	public String getData () {
		
		return data;
		
	}
	
	public void addSubFolder (String data) {
		
		subFolders.add(new Folder(data));
		
	}
	
	public ArrayList<Folder> getSubFolders () {
		
		return subFolders;
		
	}
	
	public void setSkip (boolean value) {
		
		skip = value;
		
	}
	
	public boolean getSkip () {
		
		return skip;
		
	}
	
	public void setDepth (int depth) {
		
		this.depth = depth;
		
	}
	
	public int getDepth () {
		
		return depth;
		
	}
	
	public boolean isEmpty () {
		
		boolean returnValue = false;
		
		if (subFolders.size() == 0) {
			
			returnValue = true;
			
		}
		
		return returnValue;
		
	}
	
}
