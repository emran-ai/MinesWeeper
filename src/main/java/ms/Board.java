package ms;

public class Board {

    private static int[][] fieldVisible;
    private final int[][] fieldHidden;

    public Board(int[][] fieldVisible, int[][] fieldHidden) {
        Board.fieldVisible = fieldVisible;
        this.fieldHidden = fieldHidden;
    }
    /*
    Our board represents the game board with two filed:
    fieldHidden and fieldVisible
    The fieldVisible is defined as a static 2D array and the fieldHidden is defined as the final 2D array:
    The static keyword is used to define a class variable which means that the variable will be shared among other instances of the class.
    but in this case
     */

    /**
     *
     */
    public static void displayVisible()
    {
        System.out.print("\t ");
        for(int i=0; i<10; i++)
        {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for(int i=0; i<10; i++)
        {
            System.out.print(i + "\t| ");
            for(int j=0; j<10; j++)
            {
                if(fieldVisible[i][j]==0)
                {
                    System.out.print("?");
                }
                else if(fieldVisible[i][j]==50) /* The i and j  are used as the indexes for the nested loop
                the outer loops uses i as the index for the row and inner loops uses j as the index for the column*/
                {
                    System.out.print(" ");
                }
                else
                {
                    System.out.print(fieldVisible[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }


    /**
     *
     */

    /**
     *
     */
    public void displayHidden()
    {
        System.out.print("\t ");
        for(int i=0; i<10; i++)
        {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for(int i=0; i<10; i++)
        {
            System.out.print(i + "\t| ");
            for(int j=0; j<10; j++)
            {
                if(fieldHidden[i][j]==0)
                {
                    System.out.print(" ");
                }
                else if(fieldHidden[i][j]==100)
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print(fieldHidden[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

}
