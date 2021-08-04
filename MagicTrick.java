import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;


/*
For some reason, when the program exits a null pointer
exception happens. I couldn't figure out why but I would guess it's
something with all the nulls you're passing as JFrame parents.
*/


public class MagicTrick
{
    public static void main(String[] args)
    {
        // Why are these parameters of playGame? They don't change / need calculation

        /*
        I changed the names to be better
          - 'value' & 'suite' are common naming when dealing with cards 
          - 'numbers' is ambiguous because you have actual numbers in the array
          - prefixing both with 'card' makes it clear they belong together
        */
        String[] cardValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] cardSuits = {"♥", "♦", "♠", "♣"};
        playGame(cardValues, cardSuits);
    }

    // Very good seperating out playGame from main
    // Even though playGame effectively becomes the main method,
    // it's much cleaner because if you were to expand the game 
    // you might start adding to main
    public static void playGame(String[] numbers, String[] type)
    {
        showIntroDialogue();
        
        // Because shuffleDeck & layoutDeck are called so differently from
        // showIntroDialogue, it isn't obvious that they also handle the GUI. 
        //
        // I initially tried to move the 'showIntroDialogue()' to be after
        // the shuffling section because I thought all these methods just did 
        // data pre-processing.

        ArrayList<String> NewDeck = shuffleDeck(numbers, type);
        int times = 2; // times variable seems unnecessary, just call each method with 2, 1, and 0
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

    // Above you refer to card-value as number, here as name, be consistent!
    public static ArrayList<String> shuffleDeck(String[] name, String[] suit)
    {
        ArrayList<String> shuffledDeck = new ArrayList<String>();
        // You don't need 2 seperate randoms
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

    /*
    This method does a _lot_ of things and at many different levels. It handles GUI, breaks
    the deck into multiple columns, and even prompts for restarting on wrong input.
    */
    public static ArrayList<String> layoutDeck (ArrayList<String> NewDeck, int times)
    {
        ArrayList<String> firstcolumn = new ArrayList<String>();
        ArrayList<String> secondcolumn = new ArrayList<String>();
        ArrayList<String> thirdcolumn = new ArrayList<String>();

        int cardindex = 0;
        for(int i = 0; i < 7; i++)
        {
            /*
            This inner for loop is unnecessary. Instead:

                firstcolumn.add(NewDeck.get(cardindex));
                secondcolumn.add(NewDeck.get(cardindex + 1));
                thirdcolumn.add(NewDeck.get(cardindex + 2));
                cardIndex += 3;
            */
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

        // The repeated .get(0), .get(1), .get(2) should be turned into a for loop
        String ans = JOptionPane.showInputDialog(
            // The reliance on whitespaces to align everything is generally poor (different fonts might have different sized spcaes),
            // but for this it's actually not that bad.
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

            // This again message is duplicated in the revealAnswer method, instead create a common restartPrompt method
            //
            // It's also useful because in revealAnswer(), input is actually checked to be valid, giving the user a chance
            // to re-enter their command if its not recognized. If instead a common method was used, this wouldn't be an issue.
            String again = JOptionPane.showInputDialog("I'm sorry but it seems you have inputted an invalid response\n"+
            "Would you like to play again? ['y' for yes and 'n' for no]"
            );


            // again.equals("n") is more clear
            if(!again.equals("y"))
            {
                JOptionPane.showMessageDialog(null, "Thanks for playing!!");
            }
            else
            {
                /*
                    Restarting the game by calling main again is very ugly

                    You might not immediately see it, but this is recursion. 
                    
                    The thing with recursion is every time you recurse a step deeper, it save the local
                    variables and needed information of the upper method. This means each time I 
                    restart the game it's saving local variables & keeping unnecessary info.
                */
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