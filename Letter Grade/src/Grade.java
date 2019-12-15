// Jeremy Thaller
//Class to print the number grade based on letter grade given
public class Grade {

	public static void main(String[] args){	

		String grade;
		String suffix;
		double numberGrade = 0;

		grade = "D"; //insert desired letter here. make it capital.
		suffix = "+"; //insert desired suffix here.


		if (grade == "A"){
			numberGrade = 4.0;
			if (suffix == "+")
				numberGrade = numberGrade + .3;
			if (suffix == "-"){
				numberGrade = numberGrade -.3;
			}System.out.println(numberGrade);}
		if (grade == "B"){
			numberGrade = 3.0;
			if (suffix == "+")
				numberGrade = numberGrade + .3;
			if (suffix == "-"){
				numberGrade = numberGrade -.3;
			}System.out.println(numberGrade);}
		if (grade == "C"){
			numberGrade = 2;
			if (suffix == "+")
				numberGrade = numberGrade + .3;
			if (suffix == "-"){
				numberGrade = numberGrade -.3;
			}System.out.println(numberGrade);}
		if (grade == "D"){
			numberGrade = 1;
			if (suffix == "+")
				numberGrade = numberGrade + .3;
			if (suffix == "-"){
				numberGrade = numberGrade -.3;
			}System.out.println(numberGrade);}
		if (grade == "F"){
			numberGrade = 0;
			System.out.println(numberGrade);}}
	}














