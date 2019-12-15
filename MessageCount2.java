/*
 * MessageCount.java
 * Cool, updated to use facebooks data dump
 * So things Totally aren't sorted at the moment, but sorting it would be a huge pain
 * I'm going to play around with the calendar object and then maybe update this to sort
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageCount
{
	
	// I forget why this exists but i call it somewhere
	public static int numberOfOccurances(String s,String pattern){
		int index = s.indexOf(pattern);
		int count = 0;
		while (index != -1) {
			count++;
			s = s.substring(index + 1);
			index = s.indexOf(pattern);
		}
		return count;
	}

	
	// This is a note to myself to remember how the .htm is organized
	/*
	 * Theres only 1 </div> before the messages begin so ignore that
	 * Next comes the overarching bucket
	 * 		<div class = "contents"> - this div includes ALL of the messages (its not closed until the end of the doc)
	 * Then begins the first thread
	 * 		<div>
	 * 		<div class="thread">INSERT PEOPLE'S NAMES DELIMED BY ","
	 * 		<div class="message">
	 * 		<div class="message_header">
	 * 		<span class="user">INSERT NAME OF PERSON WHO SENT MESSAGE
	 * 			<\span>- this closes the user
	 * 		<span class="meta">{Day of WEEK}, {Month Text} {[D]D}, {YYYY} at [H]H:MM[a,p]m {Time Zone}
	 * 			<\span>- this closes the meta 
	 * 			<\div>- this closes the message_header
	 * 			<\div>- this closes the message
	 * 		<p> INSERT MESSAGE TEXT
	 * 			<\p> End Message Text
	 * 
	 */
	
	//THis message breaks that GIANT doc into a bunch of smaller ones, one per conversation
	public static void createIndividualTextDocuments(File completeDocument){
		try{
			// Open a scanner on the text file
			// Set the delimtter to /div
			Scanner scComplete = new Scanner(completeDocument).useDelimiter("</div>");

			// Create string for current piece of document
			// Skip the first value of sc.next() beacuse thats all head crap
			@SuppressWarnings("unused")
			String currentDiv = scComplete.next() + scComplete.delimiter();
			currentDiv = scComplete.next() + scComplete.delimiter();

			String title = "";
			int divsOpen = 0;
			// While there are still things to do
			Boolean areMoreThreads = true;
			// If this is the first file
			Boolean isFirst = true;
			// If this is the first thread in an empty div
			Boolean isNewEmptyDiv = true;

			while(areMoreThreads){
				//Regex to find who's in the conversation

				Pattern pattern = Pattern.compile("<div class=\"thread\">");
				Matcher matcher = pattern.matcher(currentDiv);
				matcher.find();
				int startInd = matcher.end();
				pattern = Pattern.compile("<div class=\"message\">");
				matcher.usePattern(pattern);
				matcher.find();
				int endInd = matcher.start();
				String pplInConvo = (currentDiv.substring(startInd,endInd));
				pplInConvo = pplInConvo.replace(" ", "_").replace(",","");

				// title of file to write
				title = pplInConvo + ".txt";
				String me = "Rohan_Kadambi_";
				if(title.indexOf(me) == 0){
					title = pplInConvo.substring(me.length()) + "_" + me.substring(0,me.length()-1) + ".txt";
				}
				if(title.length() > 255){
					title = title.substring(0,245);
				}
				
				// Create a File Writer, this will automatically open the same file if there's a repeat
				FileWriter fw = new FileWriter("/Users/Rohan_Kadambi/Desktop/messages/"+title, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter filePrinter = new PrintWriter(bw);


				// The -1 is because theres a </div> at the end of this line of text (which closed message header)
				// Currently open: contents, "", thread, message
				divsOpen += numberOfOccurances(currentDiv,"<div")-1;

				if(isFirst){
					// This removes the div (only from the text its already been counted above) from {<div class="contents"><h1>Rohan Kadambi</h1>}
					currentDiv = currentDiv.substring(44);
					isFirst = false;
				}
				if(isNewEmptyDiv){
					// This removes the div (only from the text its already been counted above) from {<div class="contents"><h1>Rohan Kadambi</h1>}
					currentDiv =currentDiv.substring(5);
					isNewEmptyDiv = false;
				}

				// As long as a thread is open you need to write all the text in that thread to one document
				while(divsOpen > 2){
					// Print the current contents to a div
					filePrinter.print(currentDiv);

					// Move to the next div
					currentDiv = scComplete.next();

					// Add any new divs to the stack and remove one for the new line
					divsOpen += numberOfOccurances(currentDiv,"<div")-1;

				}
				// Print the last edge div
				filePrinter.print(currentDiv);

				// Close the writer
				bw.close();
				fw.close();
				filePrinter.close();
				// There are 3 Distinct Cases now:
				//		1) We're just at the end of a thread
				// 		2) We're at the end of one of the empty divs
				// 		3) We're actually at the end

				// Move to the next line
				currentDiv = scComplete.next();
				if(currentDiv.contains("thread")){
					currentDiv = currentDiv + scComplete.delimiter();
					// If theres a thread after us we good and we can just loop back
				}
				else{
					// Check the next line, if the next line has / then it must be the last one and we're done
					currentDiv = scComplete.next();
					if(currentDiv.contains("<div class=\"footer\"")){
						areMoreThreads = false;
						scComplete.close();
						return;
					}
					else{
						currentDiv = currentDiv + scComplete.delimiter();
						isNewEmptyDiv = true;
						divsOpen += -1;
					}
				}
				
				System.out.println(title);

				if(currentDiv.contains("<div class=\"footer\"")){
					scComplete.close();
					return;
				}

				// UPDATE THIS TO CONTINUE LOOP TO NEXT THREAD
				//				areMoreThreads = false;
			}
			scComplete.close();
			return;	
		}catch(Exception e){
			e.printStackTrace();
		}
		return;
	}
	
	
	// This returns a string (that you probably want to write to a file, see main. I'm not writing it to a file here so I can sort it (TODO)) 
	// The string is formated <MM/DD/YYYY>, <Number of Messages> \n
	// Yes, I know this is a shit tier way to write dates but Excel was being a little bitch and I didn't want to deal with it. If you want to change it
	// Go ahead, its pretty trivial just fuck with the end of the innermost while loop
	public static String messagesPerDay(File conversation){

		String output = "";
		try {
			Scanner sc = new Scanner(conversation);
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()){
				sb.append(sc.next());

			}
			Pattern pattern = Pattern.compile("\\w*,\\w*\\d\\d,\\d\\d\\d\\dat([1-9]|1[012]):[0-5][0-9]");
			Matcher matcher = pattern.matcher(sb);
			while(matcher.find()){
				String temp = matcher.group();
				temp = temp.substring(temp.indexOf(",")+1);
				temp = temp.replaceAll(",", " ");
				temp = temp.substring(0,temp.indexOf("at"));
				for (int i = temp.length()-1; i>-1; i--){
					if(!Character.isDigit(temp.charAt(i)) && temp.charAt(i)!=' '){
						temp = temp.substring(0,i+1)+" "+temp.substring(i+1);
						break;
					}
				}
				Date date = null;
				try {
					date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(temp.substring(0,temp.indexOf(" ")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
			    int month = cal.get(Calendar.MONTH)+1;
			    temp = month + temp.substring(temp.indexOf(" "));
			    temp = temp.replaceAll(" ","/");
				output += temp+"\n";	
			}
			ArrayList<String> dates = new ArrayList<String>();
			ArrayList<Integer> count = new ArrayList<Integer>();
			sc.close();
			sc = new Scanner(output);
			while(sc.hasNextLine()){
				String temp = sc.nextLine();
				if(dates.contains(temp)){
					count.set(dates.indexOf(temp), count.get(dates.indexOf(temp))+1);
				}else{
					dates.add(temp);
					count.add(1);
				}
			}
			output = "";
			for(int i = 1; i<dates.size(); i++){
				output += dates.get(i) + "," + count.get(i) + "\n";
			}
			//			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return output;

	}
	public static void main(String[] args){
		////////////////////////////////////////////////////////////////////////////////////////////
		//	Uncomment this the first time you run it and then never again	
		// 	Also this throws an IllegalStateException but don't worry about it. It happens after everything is done anyways.
		//  I GUESS I could surround this with try catch but fuck it. I don't care, this only runs ones otherwise you fuck everything up.
		
//				File rawText = new File("/Users/Rohan_Kadambi/Desktop/messages/messages.htm");
//				createIndividualTextDocuments(rawText); 
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		// Uncomment this after you've run the first part and change the names as you please
		// THis will give you a new textfile that contains a list of <Date>,<Number of Msgs>. Its not sorted sorry, that would require work I don't care to do right now
//		try{
//			File output = new File("/Users/Rohan_Kadambi/Desktop/Jeremy_Thaller.txt");
//			if(!output.exists())
//				output.createNewFile();
//			FileWriter fw = new FileWriter(output);
//			fw.write(messagesPerDay(new File("/Users/Rohan_Kadambi/Desktop/messages/Jeremy_Thaller_Rohan_Kadambi.txt")));
//			fw.close();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}

	}
}
