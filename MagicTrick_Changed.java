import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

public class MagicTrick_Changed
{
    public static void main(String[] args)
    {
        String[] numbers = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] type = {"♥", "♦", "♠", "♣"};
        playGame(numbers, type);
    }

    public static void playGame(String[] numbers, String[] type)
    {
        showIntroDialogue();
        ArrayList<String> NewDeck = shuffleDeck(numbers, type);
        int times = 2;
        ArrayList<String> NewDeck2 = layoutDeck(NewDeck, times);
        times = times - 1;
        ArrayList<String> NewDeck3 = layoutDeck(NewDeck2, times);
        times = times - 1;
        ArrayList<String> DoneDeck = layoutDeck(NewDeck3, times);
        revealAnswer(DoneDeck);
    }

    public static void showIntroDialogue()
    {
        JOptionPane.showMessageDialog(null,"Hey do you want to see a cool magic trick?\n" +
                "On the next screen, you will see 21 cards, memorize one of them.");
    }

    public static ArrayList<String> shuffleDeck(String[] name, String[] suit)
    {
        ArrayList<String> shuffledDeck = new ArrayList<String>();
        Random first = new Random();
        Random second = new Random();
        int secondrand = second.nextInt(4);
        int firstrand = first.nextInt(13);
        shuffledDeck.add(name[firstrand] + " of " + suit[secondrand]);

        while (shuffledDeck.size() <= 21)
        {
            secondrand = second.nextInt(4);
            firstrand = first.nextInt(13);

            if (!shuffledDeck.contains(name[firstrand] + " of " + suit[secondrand]))
            {
                shuffledDeck.add(name[firstrand] + " of " + suit[secondrand]);
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
            for(int j = 0; j < 3; j++)
            {
                if (j == 0)
                {
                    firstcolumn.add(NewDeck.get(cardindex));
                }
                if (j == 1)
                {
                    secondcolumn.add(NewDeck.get(cardindex));
                }
                if (j == 2)
                {
                    thirdcolumn.add(NewDeck.get(cardindex));
                }
                cardindex++;
            }
        }

        String ans = JOptionPane.showInputDialog(
                "\n Column 1:                       Column 2:                          Column 3:\n\n" +
                        "   " + firstcolumn.get(0) + "                              " + secondcolumn.get(0) + "                              " + thirdcolumn.get(0) + "\n" +
                        "   " + firstcolumn.get(1) + "                              " + secondcolumn.get(1) + "                              " + thirdcolumn.get(1) + "\n" +
                        "   " + firstcolumn.get(2) + "                              " + secondcolumn.get(2) + "                              " + thirdcolumn.get(2) + "\n" +
                        "   " + firstcolumn.get(3) + "                              " + secondcolumn.get(3) + "                              " + thirdcolumn.get(3) + "\n" +
                        "   " + firstcolumn.get(4) + "                              " + secondcolumn.get(4) + "                              " + thirdcolumn.get(4) + "\n" +
                        "   " + firstcolumn.get(5) + "                              " + secondcolumn.get(5) + "                              " + thirdcolumn.get(5) + "\n" +
                        "   " + firstcolumn.get(6) + "                              " + secondcolumn.get(6) + "                              " + thirdcolumn.get(6) + "\n\n" +
                        "Type below which column your card is in [either '1', '2', or '3']. We will do this a total of " + times +  " more time(s)");


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
            String again = JOptionPane.showInputDialog("I'm sorry but it seems you have inputted an invalid response\n"+
            "Would you like to play again? ['y' for yes and 'n' for no]"
            );
            if(!again.equals("y"))
            {
                JOptionPane.showMessageDialog(null, "Thanks for playing!!");
            }
            else
            {
                main(null);
            }
        }
        return NewDeck;
    }

    public static void revealAnswer (ArrayList<String> DoneDeck)
    {
        JOptionPane.showMessageDialog(null,"I figured it out! Your card was the " + DoneDeck.get(10));
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
            revealAnswer(DoneDeck);
        }
    }
}