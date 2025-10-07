import java.util.Scanner;

/**
 * Program that runs a GPA Calculator for the University of Wisconsin-Madison
 * 
 * @author Darren Seubert
 */
public class GPACalc {
  /**
   * Main method that runs the GPA Calculator
   * 
   * @param args Not used in this program
   */
  public static void main(String[] args) {   
    boolean hasValidInput = false;
    boolean hasReceivedEnter = false;
    double previousCumulativeGPA = 0.0;
    int totalPreviousCredits;
    int numClasses;
    String tempString;
    int classCredits;
    int classIndex;
    
    String[] classNames;
    int[] credits;
    char[] letterGrades;
    double[] GPAs;

    Scanner scnr = new Scanner(System.in);
    
    do { // Total Credits Prior to Semester Prompt
      System.out.println("Enter Total GPA Credits (GPA Units) Prior to this Semester:");
      if (scnr.hasNextInt()) {
        totalPreviousCredits = scnr.nextInt();

        if (totalPreviousCredits < 0) {
          System.out.println("Error, invalid entry");
          totalPreviousCredits = 0;
          scnr.nextLine();
        } else {
          scnr.nextLine();
          hasValidInput = true;
        }
      } else {
        System.out.println("Error, invalid entry");
        totalPreviousCredits = 0;
        scnr.nextLine();
      }
    } while (!hasValidInput);
    hasValidInput = false;

    while (totalPreviousCredits > 0 && !hasValidInput) { // Current Cumulative GPA Prompt
      System.out.println("Enter Current Cumulative GPA:");
      if (scnr.hasNextDouble()) {
        previousCumulativeGPA = scnr.nextDouble();
        scnr.nextLine();

        if (previousCumulativeGPA > 5.0 || previousCumulativeGPA < 0.0) {
          System.out.println("Error, invalid entry");
          previousCumulativeGPA = 0.0;
        } else {
          hasValidInput = true;
        }
      } else {
        System.out.println("Error, invalid entry");
        previousCumulativeGPA = 0.0;
        scnr.nextLine();
      }
    }
    hasValidInput = false;

    do { // Number of Classes for Current Semester Prompt
      System.out.println("Enter Number of Classes for Current Semester:");
      if (scnr.hasNextInt()) {
        numClasses = scnr.nextInt();
        scnr.nextLine();

        if (numClasses > 0) {
          hasValidInput = true;
        } else {
          System.out.println("Error, invalid entry");
        }

      } else {
        System.out.println("Error, invalid entry");
        numClasses = 0;
        scnr.nextLine();
      }
    } while (!hasValidInput);
    hasValidInput = false;

    classNames = new String[numClasses];
    credits = new int[numClasses];
    letterGrades = new char[numClasses];
    GPAs = new double[numClasses];
    tempString = "";

    for (int i = 0; i < numClasses; i++) { // For The Number of Classes The User Entered
      do { // Enter Class Name Prompt
        System.out.println("Enter Class #" + (i + 1) + " Name:");
        tempString = scnr.nextLine().trim();

        if (!tempString.equals("")) {
          classNames[i] = tempString;
          hasValidInput = true;
        } else {
          System.out.println("Error, invalid entry");
        }
      } while (!hasValidInput);
      hasValidInput = false;
      tempString = "";

      do { // Enter Class Credits Prompt
        System.out.println("Enter Class #" + (i + 1) + " Credits:");
        if (scnr.hasNextInt()) {
          classCredits = scnr.nextInt();
          scnr.nextLine();

          if (classCredits <= 0) {
            System.out.println("Error, invalid entry");
          } else {
            credits[i] = classCredits;
            hasValidInput = true;
          }
        } else {
          System.out.println("Error, invalid entry");
          scnr.nextLine();
        }
      } while (!hasValidInput);
      hasValidInput = false;

      do { // Enter Class Letter Grade Prompt
        System.out.println("Enter Class #" + (i + 1) + " Letter Grade:");
        tempString = scnr.nextLine().trim().toUpperCase();

        if (tempString.equals("AB")) {
          letterGrades[i] = '@';
          hasValidInput = true;
        } else if (tempString.equals("BC")) {
          letterGrades[i] = '#';
          hasValidInput = true;
        } else if ((tempString.length() == 1) && (tempString.charAt(0) == 'A'
            || tempString.charAt(0) == 'B' || tempString.charAt(0) == 'C'
            || tempString.charAt(0) == 'D' || tempString.charAt(0) == 'F')) {
          letterGrades[i] = tempString.charAt(0);
          hasValidInput = true;
        } else {
          System.out.println("Error, invalid entry");
        }
      } while (!hasValidInput);
      hasValidInput = false;
      tempString = "";
    }

    do { // Output Results and Prompt for Update Until Enter
      GPAs = convertLetterGradesToGPAs(letterGrades);
      printClasses(classNames, letterGrades);
      calculateSemesterAndCumulativeGPA(totalPreviousCredits, previousCumulativeGPA, credits, GPAs);

      do {
        System.out.println("Enter a Class Index to Edit Grade (Press Enter to Exit):");
        
        tempString = scnr.nextLine().trim();
        try {
          classIndex = Integer.parseInt(tempString) - 1;

          if (classIndex < 0 || classIndex >= numClasses) {
            System.out.println("Error, invalid entry");
            classIndex = -1;
          } else {
            hasValidInput = true;
          }
        } catch (NumberFormatException e) {
          classIndex = -1;

          if (tempString.equals("")) {
            System.out.println("We Done");
            hasValidInput = true;
            hasReceivedEnter = true;
          } else {
            System.out.println("Error, invalid entry");
          }
        }
      } while (!hasValidInput);
      hasValidInput = false;
      tempString = "";

      if (hasReceivedEnter) {
        break;
      }

      do { // Reprompt for Selected Class's Letter Grade
        System.out.println("Enter a New Letter Grade for " + classNames[classIndex] + ":");
        tempString = scnr.nextLine().trim().toUpperCase();

        if (tempString.equals("AB")) {
          letterGrades[classIndex] = '@';
          hasValidInput = true;
        } else if (tempString.equals("BC")) {
          letterGrades[classIndex] = '#';
          hasValidInput = true;
        } else if ((tempString.length() == 1) && (tempString.charAt(0) == 'A'
            || tempString.charAt(0) == 'B' || tempString.charAt(0) == 'C'
            || tempString.charAt(0) == 'D' || tempString.charAt(0) == 'F')) {
          letterGrades[classIndex] = tempString.charAt(0);
          hasValidInput = true;
        } else {
          System.out.println("Error, invalid entry");
        }
      } while (!hasValidInput);
      hasValidInput = false;
    } while (!hasReceivedEnter);

    scnr.close();
  }

  /**
   * Function that converts the inputted letter grades to GPA values
   * 
   * @param letterGrades Array of letter grades represented as chars to be converted
   * @return Array of doubles representing each of the classes letter grade's GPA value
   * @throws IllegalArgumentException If letterGrades has an invalid char in it 
   */
  public static double[] convertLetterGradesToGPAs(char[] letterGrades) {
    double[] GPAs = new double[letterGrades.length];

    for (int i = 0; i < letterGrades.length; i++) {
      if (letterGrades[i] == 'A') {
        GPAs[i] = Constants.A;
      } else if (letterGrades[i] == '@') {
        GPAs[i] = Constants.AB;
      } else if (letterGrades[i] == 'B') {
        GPAs[i] = Constants.B;
      } else if (letterGrades[i] == '#') {
        GPAs[i] = Constants.BC;
      } else if (letterGrades[i] == 'C') {
        GPAs[i] = Constants.C;
      } else if (letterGrades[i] == 'D') {
        GPAs[i] = Constants.D;
      } else if (letterGrades[i] == 'F') {
        GPAs[i] = Constants.F;
      } else {
        throw new IllegalArgumentException("Error: The char array letterGrades had a invalid " +
          "value at index: " + i);
      }
    }
    return GPAs;
  }

  /**
   * Function that calculates the semester and cumulative GPA and prints it to the console
   * 
   * @param totalPreviousCredits The number of previous credits graded on the transcript
   * @param previousCumulativeGPA The previous cumulative GPA earned on the transcript
   * @param credits The array of credits values for classes taken this year
   * @param GPAs The array of GPAs earned for classes taken this year
   */
  public static void calculateSemesterAndCumulativeGPA(int totalPreviousCredits,
      double previousCumulativeGPA, int[] credits, double[] GPAs) {
    int termCreditTotal = 0;
    double termWeightedGPATotal = 0.0;

    for (int i = 0; i < credits.length; i++) {
      termCreditTotal = credits[i] + termCreditTotal;
    }

    for (int i = 0; i < credits.length; i++) {
      termWeightedGPATotal = (credits[i] * GPAs[i]) + termWeightedGPATotal;
    }

    double termWeightedGPA = termWeightedGPATotal / termCreditTotal;

    double perviousGPATotal = previousCumulativeGPA * totalPreviousCredits;
    double cumulativeGPA =
        (termWeightedGPATotal + perviousGPATotal) / (termCreditTotal + totalPreviousCredits);

    System.out.println("______________________________________________________");
    System.out.println("You are Taking " + termCreditTotal + " Credits this Semester");
    System.out.println("Your GPA this Semester is: " + termWeightedGPA);
    System.out.println("------------------------------------------------------");
    System.out.println(
        "You Have Now Taken a Total of " + (termCreditTotal + totalPreviousCredits) + " Credits");
    System.out.println("Your New Cumulative GPA is: " + cumulativeGPA);
    System.out.println("------------------------------------------------------");
  }

  /**
   * Function that prints out all entered classes and their letter grades.
   * 
   * @param classNames The array of class names entered by the user
   * @param letterGrades Them array of letter grades entered by the user
   */
  public static void printClasses(String[] classNames, char[] letterGrades) {
    System.out.println("______________________________________________________");

    for (int i = 0; i < classNames.length; i++) {
      if (letterGrades[i] == '@') {
        System.out.println((i + 1) + ") " + classNames[i] + ": AB");
      } else if (letterGrades[i] == '#') {
        System.out.println((i + 1) + ") " + classNames[i] + ": BC");
      } else {
        System.out.println((i + 1) + ") " + classNames[i] + ": " + letterGrades[i]);
      }
    }
  }
}
