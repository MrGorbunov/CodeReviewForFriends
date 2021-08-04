import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

public class MagicTrick_Changed
{
    public static void main(String[] args)
    {
        playGame();
    }

    public static void playGame()
    {
        showIntroDialogue();

        String[] cardValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] cardSuites = {"♥", "♦", "♠", "♣"};

        ArrayList<String> newDeck = shuffleDeck(cardValues, cardSuites);
        ArrayList<String> newDeck2 = layoutDeck(newDeck, 2);
        ArrayList<String> newDeck3 = layoutDeck(newDeck2, 1);
        ArrayList<String> doneDeck = layoutDeck(newDeck3, 0);
        revealAnswer(doneDeck);
    }

    public static void showIntroDialogue()
    {
        JOptionPane.showMessageDialog(null,"Hey do you want to see a cool magic trick?\n" +
                "On the next screen, you will see 21 cards, memorize one of them.");
    }

    public static ArrayList<String> shuffleDeck(String[] name, String[] suit)
    {
        ArrayList<String> shuffledDeck = new ArrayList<String>();
        Random rand = new Random();
        int randSuitInd = rand.nextInt(4);
        int randValueInd = rand.nextInt(13);
        shuffledDeck.add(name[randValueInd] + " of " + suit[randSuitInd]);

        while (shuffledDeck.size() <= 21)
        {
            randSuitInd = rand.nextInt(4);
            randValueInd = rand.nextInt(13);
            String potentialCard = name[randValueInd] + " of " + suit[randSuitInd];

            if (!shuffledDeck.contains(potentialCard))
            {
                shuffledDeck.add(potentialCard);
            }
        }

        return shuffledDeck;
    }

    public static ArrayList<String> layoutDeck (ArrayList<String> NewDeck, int times)
    {
        ArrayList<String> firstcolumn = new ArrayList<String>();
        ArrayList<String> secondcolumn = new ArrayList<String>();
        ArrayList<String> thirdcolumn = new ArrayList<String>();

        int cardindex = 0;
        for(int i = 0; i < 7; i++)
        {
            firstcolumn.add(NewDeck.get(cardindex));
            secondcolumn.add(NewDeck.get(cardindex + 1));
            thirdcolumn.add(NewDeck.get(cardindex + 2));
            cardindex += 3;
        }

        String whitespaceGap = "                              ";
        StringBuilder deckDisplay = new StringBuilder();
        deckDisplay.append("\n");
        deckDisplay.append(" Column 1:                       Column 2:                          Column 3:\n\n");
        for (int i=0; i<=6; i++) {
            deckDisplay.append("   " + firstcolumn.get(i) + whitespaceGap + secondcolumn.get(i) + whitespaceGap + thirdcolumn.get(i) + "\n");
        }
        deckDisplay.append("Type below which column your card is in [either '1', '2', or '3']. We will do this a total of " + times +  " more time(s)");

        String ans = JOptionPane.showInputDialog(deckDisplay.toString());


        NewDeck.clear();

        if(ans.equals("1") || ans.equals("one"))
        {
            NewDeck.addAll(secondcolumn);
            NewDeck.addAll(firstcolumn);
            NewDeck.addAll(thirdcolumn);
        }
        else if(ans.equals("2") || ans.equals("two"))
        {
            NewDeck.addAll(thirdcolumn);
            NewDeck.addAll(secondcolumn);
            NewDeck.addAll(firstcolumn);
        }
        else if(ans.equals("3") || ans.equals("three"))
        {
            NewDeck.addAll(secondcolumn);
            NewDeck.addAll(thirdcolumn);
            NewDeck.addAll(firstcolumn);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "I'm sorry but it seems you have inputted an invalid response\n");
            restartPrompt();
        }
        return NewDeck;
    }

    public static void revealAnswer (ArrayList<String> DoneDeck)
    {
        JOptionPane.showMessageDialog(null,"I figured it out! Your card was the " + DoneDeck.get(10));
        restartPrompt();
    }

    public static void restartPrompt () {
        String again = JOptionPane.showInputDialog("Would you like to play again? ['y' for yes and 'n' for no]");
        if(again.equals("n"))
        {
            JOptionPane.showMessageDialog(null, "Thanks for playing!");
        }
        else if (again.contains("y"))
        {
            main(null);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Unidentified answer. Please type it in again!");
            restartPrompt();
        }
    }
}