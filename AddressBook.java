package bg.sirma.adressbook;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class AddressBook {

	private static int id = 1;
	private static boolean isRunning = true;
	private static boolean isRemoved, isValidName, isValidPhone;
	
	private static final Comparator<Entry> SORT_BY_ID = new Comparator<Entry>() {
		@Override
		 public int compare(Entry left, Entry right) {
			 return Integer.compare(right.id, left.id);
		 }
	 };
	
	private static final Comparator<Entry> SORT_BY_NAME = new Comparator<Entry>() {
		@Override
		public int compare(Entry left, Entry right) {
				return left.name.compareTo(right.name);
			}
		 };
		 
	private static final Comparator<Entry> SORT_BY_PHONE = new Comparator<Entry>() {
			@Override
			public int compare(Entry left, Entry right) {
				return left.phoneNumber.compareTo(right.phoneNumber);
			}
		 };
	
	private static final Comparator<Entry> SORT_BY_NAME_BACK = new Comparator<Entry>() {
			@Override
			public int compare(Entry left, Entry right) {
				return right.name.compareTo(left.name);
			}
		 };
		 
	private static final Comparator<Entry> SORT_BY_PHONE_BACK = new Comparator<Entry>() {
			@Override
			public int compare(Entry left, Entry right) {
				return right.phoneNumber.compareTo(left.phoneNumber);
			}
		};
		
	//create new object
	private static Entry newEntry(String name, String phone){
		    Entry myEntry = new Entry(id, name, phone);
		    System.out.println("New record with ID " + id + " has been created!");
		    id++;
		    return myEntry;
		}	
	
	//read from console
	private static String readCommand(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    String text = null;
	    //reading from console
	    try{
	    	text = reader.readLine();
    	} catch (IOException e) {
            System.out.println("Please try again!");
	    }
	    return text;
	}
	
	private static String showMainMenu(){
		System.out.print("Please select action (N, R, L, Q): ");
		String read = readCommand();
		return read;
	}

	public static String insertName(List<Entry> entriesList){
		isValidName = true;
		System.out.print("Name: ");
		String tempName = readCommand();	//reading the name from the console
		if (tempName.length() == 0){
			System.out.println("Invalid name!");
			tempName = insertName(entriesList);
		}	
		Iterator<Entry> iterName = entriesList.iterator();	//check if name already exist
		while (iterName.hasNext()) {
			   Entry myEntry = iterName.next();
			   if((myEntry.name).equals(tempName)){
			       isValidName = false;
			   }
		}
		if (!isValidName){	
			System.out.println("Error: A record with such name already exists!");
			tempName = insertName(entriesList);
		}
		return tempName;
	}
	
	private static String insertPhone(List<Entry> entriesList){
		isValidPhone = true;
		System.out.print("Phone: ");
		String tempPhone = readCommand();	
		if (tempPhone.length() == 0){
			isValidPhone = false;
		}
		for (int i = 0; i<tempPhone.length(); i++){
			if (!Character.isDigit(tempPhone.charAt(i))){
				isValidPhone = false;
				break;
			}
		}
		if (!isValidPhone){
			System.out.println("Invalid phone number");
			tempPhone = insertPhone(entriesList);
		} else {
			Iterator<Entry> iterPhone = entriesList.iterator();
			while (iterPhone.hasNext()) {
			    Entry myEntry = iterPhone.next();
			    if((myEntry.phoneNumber).equals(tempPhone)){
			        isValidPhone = false;
			        System.out.println("Error: A record with such phone number already exists!");
			    }
			}
		}
		if (!isValidPhone){
			tempPhone = insertPhone(entriesList);
		}
		return tempPhone;
	}
	
	private static void removeEntry(List<Entry> entriesList){
		isRemoved = false;
		int tempId = -1;
		System.out.print("Record ID: ");
		String textId = readCommand();
		try{
			tempId = Integer.parseInt(textId);
		} catch (NumberFormatException e){
			 isRemoved = false;
			
		}
		
		Iterator<Entry> iter = entriesList.iterator();
		while (iter.hasNext()) {
		    Entry myEntry = iter.next();
		    if(tempId == myEntry.id){
		        iter.remove();
		        isRemoved = true;
		    }
		}
		if (isRemoved){
			System.out.println("Record with ID " + tempId + " has been removed!");
		} else {
			System.out.println("Invalid ID " + textId);
		}
	}
	
	//prints the sorted arraylist
	private static void showSortedList(List <Entry> myTempList){
		System.out.println("Records ("+ myTempList.size() + "):");
		 for (int i = 0; i < myTempList.size(); i++){
	        	System.out.println("[" + myTempList.get(i).id + "]" + " " + myTempList.get(i).phoneNumber + " - " +myTempList.get(i).name);
		 }
	}
	
	public static void main(String[] args) {
		String command = showMainMenu(); // wait for valid option
		List<Entry> allEntries = new ArrayList<Entry>();
		while (isRunning){
			switch (command){
			case "N": {
				String name = insertName(allEntries);
				String tempPhone = insertPhone(allEntries);
				Entry tempEntry = newEntry(name,tempPhone); //create new object and insert into arraylist
				allEntries.add(tempEntry);
				command = showMainMenu();
				break;
			}
			case "R": {
				removeEntry(allEntries);
				command = showMainMenu();
				break;
			}
			case "L": {
				Collections.sort(allEntries, SORT_BY_ID);
				showSortedList(allEntries);
				command = showMainMenu();
				break;
			}
			case "L:name": {
				Collections.sort(allEntries, SORT_BY_NAME);
				showSortedList(allEntries);
				command = showMainMenu();
				break;
			}
			case "L:phone": {
				Collections.sort(allEntries, SORT_BY_PHONE);
				showSortedList(allEntries);
				command = showMainMenu();
				break;
			}
			case "L:name!": {
				Collections.sort(allEntries, SORT_BY_NAME_BACK);
				showSortedList(allEntries);
				command = showMainMenu();
				break;
			}
			case "L:phone!": {
				Collections.sort(allEntries, SORT_BY_PHONE_BACK);
				showSortedList(allEntries);
				command = showMainMenu();
				break;
			}
			case "Q": {
				System.out.print("Bye!");
				isRunning = false;
				System.exit(0);
				break;
			}
			default: {
				System.out.println("Invalid action!");
				
				command = showMainMenu();
				break;
			}
			
			}
		}
	}

}
