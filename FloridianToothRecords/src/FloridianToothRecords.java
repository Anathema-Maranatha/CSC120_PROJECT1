import java.util.Scanner;

/**
 * Floridian Tooth Records: records the teeth of the members of one Florida family and
 * offers a menu to print the record, extract a tooth and report the family's root canal
 * indices. The names are kept in an array of strings and the teeth in a three dimensional
 * array of characters (one plane per person, one row per layer, one column per tooth).
 *
 * @author Maxime Manuel Afonso
 */
public class FloridianToothRecords {
    private static final int MAXIMUM_NUMBER_OF_FAMILY_MEMBERS = 6;
    private static final int MAXIMUM_NUMBER_OF_TEETH_PER_LAYER = 8;
    private static final int NUMBER_OF_LAYERS = 2;
    private static final int ROW_INDEX_UPPERS = 0;
    private static final int ROW_INDEX_LOWERS = 1;
    private static final char INCISOR = 'I';
    private static final char BICUSPID = 'B';
    private static final char MISSING = 'M';
    private static final char PRINT_OPTION = 'P';
    private static final char EXTRACT_OPTION = 'E';
    private static final char ROOT_OPTION = 'R';
    private static final char EXIT_OPTION = 'X';
    private static final char UPPER_OPTION = 'U';
    private static final char LOWER_OPTION = 'L';
    private static final String UPPERS_LABEL = "uppers"; // used in the input prompts
    private static final String LOWERS_LABEL = "lowers";
    private static final String UPPERS_HEADING = "Uppers:"; // used when printing the record
    private static final String LOWERS_HEADING = "Lowers:";
    private static final int NOT_FOUND = -1;
    private static final int PROMPT_WIDTH = 44;
    private static final String PROMPT_FORMAT = "%-" + PROMPT_WIDTH + "s: ";   // Prompt formatting "%-44s: "

    /**
     * Runs the tooth records program: welcome, family data, menu, exit.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        int numberOfFamilyMembers;
        String[] familyMemberNames;
        char[][][] teeth;
        Scanner keyboard = new Scanner(System.in);

        displayWelcomeMessage();
        numberOfFamilyMembers = getNumberOfFamilyMembers(keyboard);

        // one name per family member; one plane per member with two rows (layers), the columns of each
        // row is created when that row is read
        familyMemberNames = new String[numberOfFamilyMembers];
        teeth = new char[numberOfFamilyMembers][NUMBER_OF_LAYERS][];

        getFamilyMembersRecords(keyboard, familyMemberNames, teeth);
        processMenuOptions(keyboard, familyMemberNames, teeth);

        displayExitMessage();
    }// end of the main method

    /**
     * Displays the welcome message.
     */
    private static void displayWelcomeMessage() {
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");
    }// end of the displayWelcomeMessage method

    /**
     * Asks for and reads the number of people in the family, asking again until it is
     * between 1 and MAXIMUM_NUMBER_OF_FAMILY_MEMBERS.
     *
     * @param keyboard the Scanner reading the keyboard
     * @return the number of people in the family
     */
    private static int getNumberOfFamilyMembers(Scanner keyboard) {
        int numberOfFamilyMembers;

        System.out.printf(PROMPT_FORMAT, "Please enter number of people in the family");
        numberOfFamilyMembers = keyboard.nextInt();

        while ((numberOfFamilyMembers < 1) || (numberOfFamilyMembers > MAXIMUM_NUMBER_OF_FAMILY_MEMBERS)) {
            System.out.printf(PROMPT_FORMAT, "Invalid number of people, try again");
            numberOfFamilyMembers = keyboard.nextInt();
        }
        return numberOfFamilyMembers;
    }// end of the getNumberOfFamilyMembers method

    /**
     * Gets the name, the uppers and the lowers of every family member and records them in
     * the family member names array and the teeth array.
     *
     * @param keyboard          the Scanner reading the keyboard
     * @param familyMemberNames the names of the family members, one element per member,
     *                          filled in here
     * @param teeth             the teeth of the family members, one plane per member with
     *                          NUMBER_OF_LAYERS rows; each row is created and filled in here
     */
    private static void getFamilyMembersRecords(Scanner keyboard, String[] familyMemberNames,
                                                char[][][] teeth) {
        int familyMember;

        // familyMember is the array index (from 0), the member number shown is one more
        for (familyMember = 0; familyMember < familyMemberNames.length; familyMember++) {
            familyMemberNames[familyMember] = getFamilyMemberName(keyboard, familyMember + 1);
            teeth[familyMember][ROW_INDEX_UPPERS] = getTeethLayer(keyboard, familyMemberNames[familyMember],
                                                                  UPPERS_LABEL);
            teeth[familyMember][ROW_INDEX_LOWERS] = getTeethLayer(keyboard, familyMemberNames[familyMember],
                                                                  LOWERS_LABEL);
        }
    }// end of the getFamilyMembersRecords method

    /**
     * Asks for and reads the name of one family member.
     *
     * @param keyboard           the Scanner reading the keyboard
     * @param familyMemberNumber the member's number in the family, from 1
     * @return the name typed by the user
     */
    private static String getFamilyMemberName(Scanner keyboard, int familyMemberNumber) {
        String familyMemberName;

        System.out.printf(PROMPT_FORMAT, "Please enter the name for family member " + familyMemberNumber);
        familyMemberName = keyboard.next();

        return familyMemberName;
    }// end of the getFamilyMemberName method

    /**
     * Reads one layer of teeth (the uppers or the lowers) of a family member, asking again
     * until the string typed has at most MAXIMUM_NUMBER_OF_TEETH_PER_LAYER characters and
     * every character is a tooth type (I, B or M, in upper or lower case).
     *
     * @param keyboard         the Scanner reading the keyboard
     * @param familyMemberName the name of the family member
     * @param teethLayer       the layer being read, UPPERS_LABEL or LOWERS_LABEL
     * @return the layer as a row of upper case tooth types, one column per tooth
     */
    private static char[] getTeethLayer(Scanner keyboard, String familyMemberName,
                                        String teethLayer) {
        String teethTyped;
        char[] teethRow;
        int toothIndex;

        System.out.printf(PROMPT_FORMAT, "Please enter the " + teethLayer + " for " + familyMemberName);
        teethTyped = keyboard.next();

        while (teethTyped.length() > MAXIMUM_NUMBER_OF_TEETH_PER_LAYER || !checkTeethTypes(teethTyped)) {
            if (teethTyped.length() > MAXIMUM_NUMBER_OF_TEETH_PER_LAYER) {
                System.out.printf(PROMPT_FORMAT, "Too many teeth, try again");
            }
            else {
                System.out.printf(PROMPT_FORMAT, "Invalid teeth types, try again");
            }// end of inner if-else loop
            teethTyped = keyboard.next();
        }// end of the outer while loop

        // the string is valid: build the row with exactly one column per tooth
        teethTyped = teethTyped.toUpperCase();
        teethRow = new char[teethTyped.length()];

        for (toothIndex = 0; toothIndex < teethRow.length; toothIndex++) {
            teethRow[toothIndex] = teethTyped.charAt(toothIndex);
        }
        return teethRow;
    }// end of the getTeethLayer method

    /**
     * Checks that every character of a string of tooth types is a tooth type, in upper or
     * lower case.
     *
     * @param toothTypes the string of tooth types typed by the user
     * @return true if every character is INCISOR, BICUSPID or MISSING, false otherwise
     */
    private static boolean checkTeethTypes(String toothTypes) {
        String upperCaseTypes = toothTypes.toUpperCase();
        int toothIndex;

        for (toothIndex = 0; toothIndex < upperCaseTypes.length(); toothIndex++) {
            switch (upperCaseTypes.charAt(toothIndex)) {
                case INCISOR:
                case BICUSPID:
                case MISSING:
                    break;              // a tooth type, check the next character
                default:
                    return false;       // not a tooth type
            }                           // end of inner switch statement
        }                               // end of outer for loop
        return true;
    }// end of the checkTeethTypes method

    /**
     * Displays the menu and reads the option chosen, asking again until it is one of the
     * four options (in upper or lower case).
     *
     * @param keyboard the Scanner reading the keyboard
     * @return the option as an upper case letter: PRINT_OPTION, EXTRACT_OPTION,
     *         ROOT_OPTION or EXIT_OPTION
     */
    private static char getMenuOption(Scanner keyboard) {
        String menuOption;
        char optionLetter;

        System.out.println();
        System.out.printf(PROMPT_FORMAT, "(P)rint, (E)xtract, (R)oot, e(X)it");

        menuOption = keyboard.next();
        optionLetter = Character.toUpperCase(menuOption.charAt(0));

        while (optionLetter != PRINT_OPTION && optionLetter != EXTRACT_OPTION && optionLetter != ROOT_OPTION &&
                optionLetter != EXIT_OPTION) {
            System.out.printf(PROMPT_FORMAT, "Invalid menu option, try again");
            menuOption = keyboard.next();
            optionLetter = Character.toUpperCase(menuOption.charAt(0));
        }
        return optionLetter;
    }// end of the getMenuOption method

    /**
     * Repeatedly gets a menu option and carries it out, until the user chooses to exit.
     *
     * @param keyboard          the Scanner reading the keyboard
     * @param familyMemberNames the names of the family members
     * @param teeth             the teeth of the family members, teeth[member][layer][tooth]
     */
    private static void processMenuOptions(Scanner keyboard, String[] familyMemberNames, char[][][] teeth) {
        char optionLetter;

        do {
            optionLetter = getMenuOption(keyboard);
            switch (optionLetter) {
                case PRINT_OPTION:
                    printFamilyRecords(familyMemberNames, teeth);
                    break;
                case EXTRACT_OPTION:
                    extractTooth(keyboard, familyMemberNames, teeth);
                    break;
                case ROOT_OPTION:
                    reportRootCanals(teeth);
                    break;
                case EXIT_OPTION:
                    break;                       // nothing to do: the loop condition ends the menu
            }                                    // end of the inner switch statement
        } while (optionLetter != EXIT_OPTION);   // end of the outer do-while loop
    }// end of the processMenuOptions method

    /**
     * Prints the family's teeth record: each member's name, then the uppers and lowers.
     *
     * @param familyMemberNames the names of the family members
     * @param teeth             the teeth of the family members, teeth[member][layer][tooth]
     */
    private static void printFamilyRecords(String[] familyMemberNames, char[][][] teeth) {
        int familyMember;

        System.out.println();
        for (familyMember = 0; familyMember < familyMemberNames.length; familyMember++) {
            System.out.println(familyMemberNames[familyMember]);
            printTeethLayer(UPPERS_HEADING, teeth[familyMember][ROW_INDEX_UPPERS]);
            printTeethLayer(LOWERS_HEADING, teeth[familyMember][ROW_INDEX_LOWERS]);
        }
    }// end of the printFamilyRecords method

    /**
     * Prints one layer of teeth on one line: the heading, then each tooth as number:type.
     *
     * @param layerHeading UPPERS_HEADING or LOWERS_HEADING
     * @param teethRow     the row of teeth of that layer
     */
    private static void printTeethLayer(String layerHeading, char[] teethRow) {
        int toothIndex;
        int toothNumber;

        System.out.print("  " + layerHeading);

        for (toothIndex = 0; toothIndex < teethRow.length; toothIndex++) {
            toothNumber = toothIndex + 1;       // teeth are numbered from 1, columns from 0
            System.out.print("  " + toothNumber + ":" + teethRow[toothIndex]);
        }
        System.out.println();
    }// end of the printTeethLayer method

    /**
     * Extracts one tooth: asks which family member, which layer and which tooth, and
     * records the tooth as missing.
     *
     * @param keyboard          the Scanner reading the keyboard
     * @param familyMemberNames the names of the family members
     * @param teeth             the teeth of the family members, teeth[member][layer][tooth];
     *                          the extracted tooth becomes MISSING
     */
    private static void extractTooth(Scanner keyboard, String[] familyMemberNames, char[][][] teeth) {
        int familyMemberIndex;
        int toothLayerIndex;
        int toothNumber;

        familyMemberIndex = getFamilyMemberIndex(keyboard, familyMemberNames);
        toothLayerIndex = getToothLayer(keyboard);
        toothNumber = getToothNumber(keyboard, teeth[familyMemberIndex][toothLayerIndex]);
        teeth[familyMemberIndex][toothLayerIndex][toothNumber - 1] = MISSING;
    }// end of the extractTooth method

    /**
     * Asks for the name of a family member and finds that member, ignoring case, asking
     * again until a name matches.
     *
     * @param keyboard          the Scanner reading the keyboard
     * @param familyMemberNames the names of the family members
     * @return the index of the member in familyMemberNames (and of the plane in teeth)
     */
    private static int getFamilyMemberIndex(Scanner keyboard, String[] familyMemberNames) {
        int familyMemberIndex = NOT_FOUND;
        int member;
        String memberName;

        System.out.printf(PROMPT_FORMAT, "Which family member");
        while (familyMemberIndex == NOT_FOUND) {
            memberName = keyboard.next();

            // search the names; NOT_FOUND means no name matched
            for (member = 0; member < familyMemberNames.length; member++) {
                if (familyMemberNames[member].equalsIgnoreCase(memberName)) {
                    familyMemberIndex = member;
                }// end of inner if-statement
            }// end of outer for loop
            if (familyMemberIndex == NOT_FOUND) {
                System.out.printf(PROMPT_FORMAT, "Invalid family member, try again");
            }
        }// end of outer while loop
        return familyMemberIndex;
    }// end of the getFamilyMemberIndex method

    /**
     * Asks which tooth layer, upper or lower, asking again until the answer starts with U
     * or L (in upper or lower case).
     *
     * @param keyboard the Scanner reading the keyboard
     * @return the row index of the layer, ROW_INDEX_UPPERS or ROW_INDEX_LOWERS
     */
    private static int getToothLayer(Scanner keyboard) {
        String toothLayer;
        char layerLetter;

        System.out.printf(PROMPT_FORMAT, "Which tooth layer (U)pper or (L)ower");
        toothLayer = keyboard.next();
        layerLetter = Character.toUpperCase(toothLayer.charAt(0));

        while (layerLetter != UPPER_OPTION && layerLetter != LOWER_OPTION) {
            System.out.printf(PROMPT_FORMAT, "Invalid layer, try again");
            toothLayer = keyboard.next();
            layerLetter = Character.toUpperCase(toothLayer.charAt(0));
        }
        if (layerLetter == UPPER_OPTION) {
            return ROW_INDEX_UPPERS;
        }
        else {
            return ROW_INDEX_LOWERS;
        }
    }// end of the getToothLayer method

    /**
     * Asks for a tooth number in a row of teeth, asking again until the number is in the
     * row and the tooth there is not already missing.
     *
     * @param keyboard the Scanner reading the keyboard
     * @param teethRow the row of teeth the tooth belongs to
     * @return the tooth number, from 1 to the number of teeth in the row
     */
    private static int getToothNumber(Scanner keyboard, char[] teethRow) {
        int toothNumber;

        System.out.printf(PROMPT_FORMAT, "Which tooth number");
        toothNumber = keyboard.nextInt();

        // the range is tested first, so the row is only indexed with a valid number
        while (toothNumber < 1 || toothNumber > teethRow.length || teethRow[toothNumber - 1] == MISSING) {
            if (toothNumber < 1 || toothNumber > teethRow.length) {
                System.out.printf(PROMPT_FORMAT, "Invalid tooth number, try again");
            }
            else {
                System.out.printf(PROMPT_FORMAT, "Missing tooth, try again");
            }// end of inner if-else statement
            toothNumber = keyboard.nextInt();
        }// end of the outer while loop
        return toothNumber;
    }// end of the getToothNumber method

    /**
     * Reports the family's root canal indices: the roots of I x^2 + B x - M = 0, where I, B
     * and M are the family's numbers of incisors, bicuspids and missing teeth.
     *
     * @param teeth the teeth of the family members, teeth[member][layer][tooth]
     */
    private static void reportRootCanals(char[][][] teeth) {
        int incisorCount;
        int bicuspidCount;
        int missingCount;
        double discriminant;
        double firstRoot;
        double secondRoot;

        incisorCount = countTeethType(teeth, INCISOR);
        bicuspidCount = countTeethType(teeth, BICUSPID);
        missingCount = countTeethType(teeth, MISSING);

        // Compute and display the root canal indices, the roots of I x^2 + B x - M = 0
        if (incisorCount == 0) { //No incisors, so the equation is linear: B x - M = 0
            if (bicuspidCount == 0) {
                System.out.println("No root canals for this family");
            }
            else {
                System.out.printf("One root canal at %8.2f\n", (double) missingCount / bicuspidCount);
            }// end of inner if-else statement
        }
        else { //never negative, because I, B and M are counts, so both roots are real
            discriminant = Math.pow(bicuspidCount, 2) + 4 * incisorCount * missingCount;
            firstRoot = (-bicuspidCount + Math.sqrt(discriminant)) / (2 * incisorCount);
            secondRoot = (-bicuspidCount - Math.sqrt(discriminant)) / (2 * incisorCount);
            System.out.printf("One root canal at %8.2f\n", firstRoot);
            System.out.printf("Another root canal at %8.2f\n", secondRoot);
        }// end of the outer if-else statement
    }// end of the reportRootCanals method

    /**
     * Counts the teeth of one type in the whole family.
     *
     * @param teeth     the teeth of the family members, teeth[member][layer][tooth]
     * @param toothType the tooth type to count: INCISOR, BICUSPID or MISSING
     * @return the number of teeth of that type in the family
     */
    private static int countTeethType(char[][][] teeth, char toothType) {
        int familyMember;
        int teethLayer;
        int toothIndex;
        int numberOfTeethType = 0;

        for (familyMember = 0; familyMember < teeth.length; familyMember++) {
            for (teethLayer = 0; teethLayer < teeth[familyMember].length; teethLayer++) {
                for (toothIndex = 0; toothIndex < teeth[familyMember][teethLayer].length; toothIndex++) {
                    if (teeth[familyMember][teethLayer][toothIndex] == toothType) {
                        numberOfTeethType++;
                    }
                }
            }
        }// end of outer for loop
        return numberOfTeethType;
    }// end of the countTeethType method

    /**
     * Displays the exit message.
     */
    private static void displayExitMessage() {
        System.out.println();
        System.out.println("Exiting the Floridian Tooth Records :-)");
    } // end of the displayExitMessage method
}// end of the FloridianToothRecords class
