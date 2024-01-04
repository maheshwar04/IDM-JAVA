import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    private static Scanner s;
	public static void main(String[] args) throws Exception {
    	System.out.println("               Welcome to IDM - Internet Download Manager");
    	System.out.println("---------------------------------MENU:------------------------------------");
    	System.out.println("1 : Download a new file");
    	System.out.println("2 : Show a list of currently ongoing/paused downloads");
    	System.out.println("3 : Pause a download");
    	System.out.println("4 : Resume a download");
    	System.out.println("5 : Cancel a download");
    	System.out.println("6 : Get detailed info of a download");
    	System.out.println("7 : Exit");
    	System.out.println("---------------------------------------------------------------------------");
    	
    	ArrayList<Download> downloads = new ArrayList<Download>();
    	
    	while(true) {
    		s = new Scanner(System.in);
    		System.out.print("Enter your choice: ");
    		int choice = s.nextInt();
    		switch(choice) {
    		case 1:
    			System.out.print("Enter valid download link [Ex:jpeg/png/zip/pdf] :");
    			s.nextLine();
    			String link = s.nextLine();
				Download d = new Download(new URL(link));
    	        while(d.getProgress()<=0) {}
    	        System.out.println("Download has started");
    	        downloads.add(d);
    			break;
    		case 2:
    			if(downloads.size()==0) {
    				System.out.println("There are currently no ongoing downloads!");
    				break;
    			}
    			for(int i = 0;i<downloads.size();i++) {
    				Download temp = downloads.get(i);
    				String status;
    				if(temp.getStatus()==1) status = "PAUSED";
    				else if(temp.getStatus()==0) status = "Downloading";
    				else status = "N/A";
    				if(temp.getStatus()!=2 && temp.getStatus()!=4) {
    					System.out.println(i+1 +" : " +temp.getFileName(new URL(temp.getUrl())) +" " +status);
    				}if(temp.getStatus()==2||temp.getStatus()==4) downloads.remove(i);
    			}
    			break;
    		case 3:
    			System.out.print("Enter the download number to pause: ");
    			int num = s.nextInt();
    			if(downloads.get(num-1).getStatus()==1) System.out.println("The download is already paused!");
    			else {
    				downloads.get(num-1).pause();
    				System.out.println("Download " +downloads.get(num-1).getFileName(new URL(downloads.get(num-1).getUrl())) +" paused");
    			}
    			break;
    		case 4:
    			System.out.print("Enter the download number to resume: ");
    			num = s.nextInt();
    			if(downloads.get(num-1).getStatus()==0) System.out.println("The file is already downloading!");
    			else {
    				downloads.get(num-1).resume();
    				System.out.println("Download  for " +downloads.get(num-1).getFileName(new URL(downloads.get(num-1).getUrl())) +" resumed");
    			}
    			break;
    		case 5:
    			System.out.print("Enter download number to cancel: ");
    			num = s.nextInt();
    			downloads.get(num-1).cancel();
    			System.out.println("Download  for " +downloads.get(num-1).getFileName(new URL(downloads.get(num-1).getUrl())) +" cancelled");
    			downloads.remove(num-1);
    			break;
    		case 6:
    			System.out.println("Enter download number to get detailed info: ");
    			num = s.nextInt();
    			Download temp = downloads.get(num-1);
    			
    			String name = temp.getFileName(new URL(temp.getUrl()));
    			String elapsedTime = temp.getElapsedTime();
    			float progress = temp.getProgress();
    			String remainingTime = temp.getRemainingTime();
    			float speed = temp.getSpeed();
    			long size = temp.getSize();
    			String status;
				if(temp.getStatus()==1) status = "PAUSED";
				else if(temp.getStatus()==0) status = "Downloading";
				else if(temp.getStatus()==2) status = "Complete";
				else status = "N/A";
				
				System.out.println("---------------------------------------------------------");
				System.out.println("File Name		: " +name);
				System.out.println("File Size		: " +size +" bytes");
				System.out.println("Status			: " +status);
				System.out.println("Progress		: " +progress +" %");
				System.out.println("Elapsed Time	: " +elapsedTime);
				System.out.println("Remaining Time	: " +remainingTime);
				System.out.println("Speed			: " +speed +" KB/S");
				System.out.println("---------------------------------------------------------");				
				
    			break;
    		case 7:
    			return;
    		default:
    			System.out.println("Please enter a valid option!");
    		}
    	}
    }
}