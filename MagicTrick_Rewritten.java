import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

public class MagicTrick_Rewritten
{

    static final String[] CARD_VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    static final String[] CARD_SUITS = {"♥", "♦", "♠", "♣"};

    static final Random rand = new Random();

    /*
        I chose to store the cards fundamentally in 3 columns because I didn't
        understand the shuffling logic at the time, and this makes GUI drawing easier.

        Still, either representation is perfectly fine (you have it all as 1 list)
    */
    static ArrayList<String> colOne;
    static ArrayList<String> colTwo;
    static ArrayList<String> colThree;

    static int numIterationsLeft;

    public static void main(String[] args)
    {
        playGame();
    }

    static void playGame()
    {
        boolean keepPlaying = true;

        while (keepPlaying) {
            resetGameVariables();

            showIntroDialogue();

            while (numIterationsLeft > 0) 
            {
                int playerChoice = showCardsAndTakeInput();
                moveColumnsAround(playerChoice);
                numIterationsLeft--;
            }

            // Exactly why this produces the right answer
            // is a little weird, but I trust it
            String correctCard = colTwo.get(3);
            showAnswer(correctCard);
            int restartChoice = showRestartPrompt();

            if (restartChoice == 0)
                keepPlaying = false;
        }
    }







    //
    // Game Logic
    //

    static void resetGameVariables () {
        colOne = new ArrayList<>();     // Note, because the type of these objecst
        colTwo = new ArrayList<>();     // is already ArrayList<String>, I don't have
        colThree = new ArrayList<>();   // to specify String here

        numIterationsLeft = 3;

        ArrayList<String> fullDeck = getShuffeledDeck();
        splitDeckIntoColumns(fullDeck);
    }

    static ArrayList<String> getShuffeledDeck()
    {
        ArrayList<String> shuffledDeck = new ArrayList<String>();

        while (shuffledDeck.size() <= 21)
        {
            // Technically, it's less efficient not adding the first card outside of the loop,
            // but readability is way more important, so thats why I do this instead
            int randSuitInd = rand.nextInt(4);
            int randValueInd = rand.nextInt(13);
            String potentialCard = CARD_VALUES[randValueInd] + " of " + CARD_SUITS[randSuitInd];

            if (!shuffledDeck.contains(potentialCard))
            {
                shuffledDeck.add(potentialCard);
            }
        }

        return shuffledDeck;
    }

    static void splitDeckIntoColumns (ArrayList<String> fullDeck) {
        colOne = new ArrayList<>();
        colTwo = new ArrayList<>();
        colThree = new ArrayList<>();

        int cardInd = 0;
        for(int i = 0; i < 7; i++)
        {
            colOne.add(fullDeck.get(cardInd));
            colTwo.add(fullDeck.get(cardInd + 1));
            colThree.add(fullDeck.get(cardInd + 2));
            cardInd += 3;
        }
    }

    static void moveColumnsAround (int colWithCard)
    {
        // Step 1 : Shuffle around the columns based on input
        ArrayList<String> tempColOne = colOne;
        ArrayList<String> tempColTwo = colTwo;
        ArrayList<String> tempColThree = colThree;

        switch (colWithCard) {
            case 1:
                colOne = tempColTwo;
                colTwo = tempColOne;
                colThree = tempColThree;
                break;

            case 2:
                colOne = tempColThree;
                colTwo = tempColTwo;
                colThree = tempColOne;
                break;

            case 3:
                colOne = tempColTwo;
                colTwo = tempColThree;
                colThree = tempColOne;
                break;
            
            default:
                // Putting checks like right into methods is very helpful because
                // it immediately tells you if there's a problem what the expected result is.
                //
                // Silently failing (i.e. not throwing an error), when developing code, is really bad
                // and makes debugging longer.
                throw new IllegalArgumentException("Called moveColumnsAround with invalid column. The value must be 1, 2, or 3." +
                    "Make sure to sanitize input before calling this method");
        }


        // Step 2 : Combine all decks together & resplit
        ArrayList<String> combinedDeck = new ArrayList<String>();
        combinedDeck.addAll(colOne);
        combinedDeck.addAll(colTwo);
        combinedDeck.addAll(colThree);
        splitDeckIntoColumns(combinedDeck);
    }










    //
    // GUI (input / output) Methods
    //

    /**
     * Shows the three columns, as they're stored in the globals, and then returns 
     * the column that the user says their card is in.
     */
    static int showCardsAndTakeInput ()
    {
        // First, generate the string to display
        String whitespaceGap = "                              ";
        StringBuilder deckDisplay = new StringBuilder();
        deckDisplay.append("\n");
        deckDisplay.append(" Column 1:                       Column 2:                          Column 3:\n\n");
        for (int i=0; i<=6; i++) {
            deckDisplay.append("   " + colOne.get(i) + whitespaceGap + colTwo.get(i) + whitespaceGap + colThree.get(i) + "\n");
        }
        deckDisplay.append("Type below which column your card is in [either '1', '2', or '3']. We will do this a total of " + numIterationsLeft +  " more time(s)");
        String displayStr = deckDisplay.toString();

        // Keeps asking for input until input is valid
        while (true) {
            String ans = JOptionPane.showInputDialog(displayStr);

            if(ans.equals("1") || ans.equals("one"))
            {
                return 1;
            }
            else if(ans.equals("2") || ans.equals("two"))
            {
                return 2;
            }
            else if(ans.equals("3") || ans.equals("three"))
            {
                return 3;
            }
            else
            {
                showInvalidInputMessage();
            }
        }
    }

    /**
     * Shows the restart prompt to the user, and returns their output
     *  1 = restart
     *  0 = exit
     */
    static int showRestartPrompt () {
        while (true) {
            String again = JOptionPane.showInputDialog("Would you like to play again? ['y' for yes and 'n' for no]");
            if(again.equals("n"))
            {
                return 0;
            }
            else if (again.equals("y"))
            {
                return 1;
            }
            else
            {
                showInvalidInputMessage();
            }
        }
    }

    //
    // Simple (one line) output methods

    static void showIntroDialogue ()
    {
        JOptionPane.showMessageDialog(null,"Hey do you want to see a cool magic trick?\n" +
            "On the next screen, you will see 21 cards, memorize one of them.");
    }

    static void showInvalidInputMessage ()
    {
        JOptionPane.showMessageDialog(null, "Unidentified answer. Please type it in again!");
    }

    static void showLeavingMessage ()
    {
        JOptionPane.showMessageDialog(null, "Thanks for playing!");
    }

    static void showAnswer (String correctCard)
    {
        JOptionPane.showMessageDialog(null,"I figured it out! Your card was the " + correctCard);
    }

}