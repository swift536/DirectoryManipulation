package FormulateDirectory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FileManipulation {
	
	private String inputFileName;
	private String outputFileName;
	
	private String intermediaryInputFileName;

	private File inputFile;
	private File outputFile;
	
	private BufferedReader is;
	private BufferedWriter os;
	
	private Folder rootFolder;
	private String newFolderStr;
	
	//Act as temp string for rest of program.
	String tempStr;
	
	public FileManipulation (String inputName, String outputName, String deleteSequence) throws IOException {
		
		rootFolder = new Folder("root");
		
		inputFileName = inputName;
		outputFileName = outputName;
		
		inputFile = new File(inputFileName);
		
		intermediaryInputFileName = "intermediary-" + inputFileName;
		
		outputFile = new File(intermediaryInputFileName);
		
		is = new BufferedReader(new FileReader(inputFile));
		
		os = new BufferedWriter(new FileWriter(outputFile));
		
		while ((tempStr = is.readLine()) != null) {
			
			tempStr = tempStr.replace(deleteSequence, "");
			
			if (tempStr.indexOf('\\') == -1) {
				
				rootFolder.addSubFolder(tempStr);
				
			} else {
				
				tempStr = tempStr + "\n";
				
				os.write(tempStr);
				
			}
			
		}
		
		is.close();
		os.close();
		
		addDepth();
		
		printDirectory(rootFolder);
		
		is.close();
		os.close();
		
	}
	
	private void addDepth () throws IOException {
		
		inputFile = new File(intermediaryInputFileName);
		
		outputFile = new File(outputFileName);
		
		is = new BufferedReader(new FileReader(inputFile));
		
		os = new BufferedWriter(new FileWriter(outputFile));
		
		while ((tempStr = is.readLine()) != null) {
			
			String[] navigationPath = tempStr.split("\\\\");
			
			if (!folderExists(rootFolder,navigationPath[0])) {
				
				rootFolder.addSubFolder(navigationPath[0]);
				
			}
			
			addToDirectory (navigationPath, rootFolder);
			
		}
		
	}
	//STACK OVERFLOW
	//Recursively prints folders and subfolder from given starting point
	//Stackoverflow due to directories being too deep
	/*private void recursivePrint (Folder folder, int depthLevel) throws IOException {
		
		//write current folder
		//recursively get the rest
		os.write(folder.getData());
		
		if (!folder.isEmpty()) {
			for (int i=0; i<folder.getSubFolders().size(); i++) {
				
				os.write("\n");
				
				for (int j=0; i<depthLevel; j++) {
					
					os.write("\t");
					
				}
				
				recursivePrint(folder, depthLevel++);
				
				return;
				
			}
		} else {
			
			return;
			
		}
		
	}*/
	
	//Prints the queue with tabs inserted as required
	private void printDirectory (Folder rootFolder) throws IOException {
		
		String tabString;
		
		String tab = "\t";
		
		LinkedList<Folder> printableQueue = organizePrint(rootFolder);
		
		for (Folder item: printableQueue) {
			
			tabString = "";
			
			for (int i=0; i<item.getDepth(); i++) {
				
				tabString += tab;
				
			}
			
			os.write(tabString + item.getData() + "\n");
			
		}
		
	}
	
	//Creates a queue with subfolders organized directly below their parent folders.
	private LinkedList<Folder> organizePrint (Folder folder) {
		
		LinkedList<Folder> currentQueue = new LinkedList<Folder>();
		
		LinkedList<Folder> newQueue;
		
		ArrayList<Folder> subFolders;
		
		Folder currentFolder;
		
		boolean done = false;
		
		int currentDepth = 0;
		
		//Initialize root	
		currentQueue.add(folder);
		folder.setDepth(currentDepth);
		
		while (!done) {
			
			newQueue = new LinkedList<Folder>();
			
			//For each item at current depth level
			for (int j=0; j<currentQueue.size(); j++) {
				
				currentFolder = currentQueue.get(j);
				
				newQueue.add(currentFolder);
				
				if (currentFolder.getDepth() < currentDepth) {
					//do nothing
				} else {
				
					subFolders = currentQueue.get(j).getSubFolders();
					
					if (subFolders.size() > 0) {
					
						done = true;
						
						for (int i=0; i<subFolders.size(); i++) {
							
							Folder item = subFolders.get(i);
							
							newQueue.add(item);
							item.setSkip(true);
							item.setDepth(currentDepth+1);
							
							if (done && (item.getSubFolders().size() > 0)) {
								
								done = false;
								
							}
							
						}
					
					}
				
				}
				
			}
			
			currentQueue = newQueue;
			
			currentDepth++;
			
		}
		
		return currentQueue;
		
	}
	
	private void addToDirectory (String[] navigationPath, Folder folder) {
		
		String[] pathToContainingFolder = Arrays.copyOfRange(navigationPath, 0, navigationPath.length-1);
		
		Folder directory = navigateToDirectory (pathToContainingFolder, folder);
		
		directory.addSubFolder(navigationPath[navigationPath.length-1]);
		
	}
	
	//Returns the desired directory within the folder.
	private Folder navigateToDirectory (String[] navigationPath, Folder folder) {
		
		//if the current folder is the destination return
		if (folder.getData().compareTo(navigationPath[navigationPath.length-1]) == 0) {
			
			return folder;
			
		//Navigate there
		} else {
			
			boolean found = false;
			
			Folder currentFolder = folder;
			
			int depth = 0;
			
			while (!found) {
				
				String nextNavigation = navigationPath[depth];
				
				ArrayList<Folder> subFolders = currentFolder.getSubFolders();
				
				for (int i=0; i<subFolders.size(); i++) {
					
					//If this is the next directory in path
					if (nextNavigation.compareTo(subFolders.get(i).getData()) == 0) {
						
						currentFolder = subFolders.get(i);
						
						break;
						
					}
					
				}
				
				if (depth == navigationPath.length-1) {
					
					found = true;
					
				} else {
					
					depth++;
					
				}
				
			}
			
			return currentFolder;
		}
		
	}
	
	private boolean folderExists (Folder folder, String comparedStr) {
		
		boolean returnValue = false;
		
		ArrayList<Folder> subFolders = folder.getSubFolders();
		
		for (int i=0; i < subFolders.size(); i++) {
			
			if (comparedStr.compareTo(subFolders.get(i).getData()) == 0) {
				
				returnValue = true;
				
			}
			
		}
		
		return returnValue;
		
	}
	
}
