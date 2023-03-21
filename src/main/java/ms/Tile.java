package ms;

import java.util.Random;

public class Tile {

    private static int[][] fieldVisible;
    private final int[][] fieldHidden;

    /**
     * Tile constructor
     *
     * @param fieldVisible
     * @param fieldHidden
     */
    public Tile(int[][] fieldVisible, int[][] fieldHidden) {
        Tile.fieldVisible = fieldVisible;
        this.fieldHidden = fieldHidden;
    }

    /**
     * @param i
     * @param j
     */
    public void fixVisible(int i, int j) {
        fieldVisible[i][j] = 50;
        if (i != 0) {
            fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
            if (fieldVisible[i - 1][j] == 0) fieldVisible[i - 1][j] = 50;
            if (j != 0) {
                fieldVisible[i - 1][j - 1] = fieldHidden[i - 1][j - 1];
                if (fieldVisible[i - 1][j - 1] == 0) fieldVisible[i - 1][j - 1] = 50;

            }
        }
        if (i != 9) {
            fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
            if (fieldVisible[i + 1][j] == 0) fieldVisible[i + 1][j] = 50;
            if (j != 9) {
                fieldVisible[i + 1][j + 1] = fieldHidden[i + 1][j + 1];
                if (fieldVisible[i + 1][j + 1] == 0) fieldVisible[i + 1][j + 1] = 50;
            }
        }
        if (j != 0) {
            fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
            if (fieldVisible[i][j - 1] == 0) fieldVisible[i][j - 1] = 50;
            if (i != 9) {
                fieldVisible[i + 1][j - 1] = fieldHidden[i + 1][j - 1];
                if (fieldVisible[i + 1][j - 1] == 0) fieldVisible[i + 1][j - 1] = 50;
            }
        }
        if (j != 9) {
            fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
            if (fieldVisible[i][j + 1] == 0) fieldVisible[i][j + 1] = 50;
            if (i != 0) {
                fieldVisible[i - 1][j + 1] = fieldHidden[i - 1][j + 1];
                if (fieldVisible[i - 1][j + 1] == 0) fieldVisible[i - 1][j + 1] = 50;
            }
        }
    }


    /**
     * this code set the value of cells at position(i,j)
     * filling array, with fix position of the mines
     *
     * @param i
     * @param j
     */
    public void fixNeighbours(int i, int j)
    {
        Random random = new Random();
        int x = random.nextInt() % 4;

        fieldVisible[i][j] = fieldHidden[i][j];

        if (x == 0) {
            if (i != 0) {
                if (fieldHidden[i - 1][j] != 100) {
                    fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
                    if (fieldVisible[i - 1][j] == 0) fieldVisible[i - 1][j] = 50;
                }
            }
            if (j != 0) {
                if (fieldHidden[i][j - 1] != 100) {
                    fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
                    if (fieldVisible[i][j - 1] == 0) fieldVisible[i][j - 1] = 50;
                }

            }
            if (i != 0 && j != 0) {
                if (fieldHidden[i - 1][j - 1] != 100) {
                    fieldVisible[i - 1][j - 1] = fieldHidden[i - 1][j - 1];
                    if (fieldVisible[i - 1][j - 1] == 0) fieldVisible[i - 1][j - 1] = 50;
                }

            }
        } else if (x == 1) {
            if (i != 0) {
                if (fieldHidden[i - 1][j] != 100) {
                    fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
                    if (fieldVisible[i - 1][j] == 0) fieldVisible[i - 1][j] = 50;
                }
            }
            if (j != 9) {
                if (fieldHidden[i][j + 1] != 100) {
                    fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
                    if (fieldVisible[i][j + 1] == 0) fieldVisible[i][j + 1] = 50;
                }
            }
            if (i != 0 && j != 9) {
                if (fieldHidden[i - 1][j + 1] != 100) {
                    fieldVisible[i - 1][j + 1] = fieldHidden[i - 1][j + 1];
                    if (fieldVisible[i - 1][j + 1] == 0) fieldVisible[i - 1][j + 1] = 50;
                }
            }
        } else if (x == 2) {
            if (i != 9) {
                if (fieldHidden[i + 1][j] != 100) {
                    fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
                    if (fieldVisible[i + 1][j] == 0) fieldVisible[i + 1][j] = 50;
                }
            }
            if (j != 9) {
                if (fieldHidden[i][j + 1] != 100) {
                    fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
                    if (fieldVisible[i][j + 1] == 0) fieldVisible[i][j + 1] = 50;
                }

            }
            if (i != 9 && j != 9) {
                if (fieldHidden[i + 1][j + 1] != 100) {
                    fieldVisible[i + 1][j + 1] = fieldHidden[i + 1][j + 1];
                    if (fieldVisible[i + 1][j + 1] == 0) fieldVisible[i + 1][j + 1] = 50;
                }
            }
        } else {
            if (i != 9) {
                if (fieldHidden[i + 1][j] != 100) {
                    fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
                    if (fieldVisible[i + 1][j] == 0) fieldVisible[i + 1][j] = 50;
                }
            }
            if (j != 0) {
                if (fieldHidden[i][j - 1] != 100) {
                    fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
                    if (fieldVisible[i][j - 1] == 0) fieldVisible[i][j - 1] = 50;
                }

            }
            if (i != 9 && j != 0) {
                if (fieldHidden[i + 1][j - 1] != 100) {
                    fieldVisible[i + 1][j - 1] = fieldHidden[i + 1][j - 1];
                    if (fieldVisible[i + 1][j - 1] == 0) fieldVisible[i + 1][j - 1] = 50;
                }
            }
        }
    }

    /**
     * count in the back end ++++ print the count in the end of the game +++ database connection
     */
    public void buildHidden() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int cnt = 0;
                if (fieldHidden[i][j] != 100) {

                    if (i != 0) {
                        if (fieldHidden[i - 1][j] == 100) cnt++;
                        if (j != 0) {
                            if (fieldHidden[i - 1][j - 1] == 100) cnt++;
                        }

                    }
                    if (i != 9) {
                        if (fieldHidden[i + 1][j] == 100) cnt++;
                        if (j != 9) {
                            if (fieldHidden[i + 1][j + 1] == 100) cnt++;
                        }
                    }
                    if (j != 0) {
                        if (fieldHidden[i][j - 1] == 100) cnt++;
                        if (i != 9) {
                            if (fieldHidden[i + 1][j - 1] == 100) cnt++;
                        }
                    }
                    if (j != 9) {
                        if (fieldHidden[i][j + 1] == 100) cnt++;
                        if (i != 0) {
                            if (fieldHidden[i - 1][j + 1] == 100) cnt++;
                        }
                    }

                    fieldHidden[i][j] = cnt;
                }
            }
        }
        /*
        This code appears to be building a hidden field in a minesweeper-like game. The outer for loop
         iterates through all the rows (i) and inner for loop iterates through all the columns (j) of a 10x10
         2D array called "fieldHidden".
        The variable "cnt" is used to count the number of mines that are located in the 8 adjacent cells of the
         current cell (i, j). If the current cell does not contain a mine (fieldHidden[i][j] != 100), the code
          then checks the 8 surrounding cells (up, down, left, right, and diagonals) to see if they contain a
           mine (fieldHidden[x][y] == 100). If a surrounding cell does contain a mine, "cnt" is incremented.
        After checking all 8 surrounding cells, the current cell's value is updated to the count of mines in
        its surrounding cells. If the current cell contains a mine, the value of the cell will not be updated
        and remain as 100.
         */

        //displayHidden();
    }

    /**
     * random number on
     *
     * @param diff
     */
    public void setupField(int diff) {
        int var = 0;
        while (var != 10) {
            Random random = new Random();
            int i = random.nextInt(10);
            int j = random.nextInt(10);
            //System.out.println("i: " + i + " j: " + j);
            fieldHidden[i][j] = 100;
            var++;
        }
        buildHidden();
    }
//
//    private boolean isFlagged;
//    private boolean isShown;
//    private boolean isMine;
//
//    public Tile(boolean isFlagged, boolean isShown) {
//        this.isFlagged = isFlagged;
//        this.isShown = isShown;
//
//    }
//
//    public boolean isShown() {
//        return isShown;
//    }
//
//    public boolean isMine(){
//        return isMine;
//    }
//
//    public void setShown(boolean isShown) {
//        this.isShown = isShown;
//    }
//
//    public boolean isFlagged() {
//        return isFlagged;
//    }
//
//    public void setFlagged(boolean isFlagged) {
//        this.isFlagged = isFlagged;
//        String symbol;
//        if (isShown) {
//            buildHidden();
//        }else if (isFlagged) {
//            symbol = "F";
//        }else{
//            if (isMine) {
//                symbol = "M";
//            }else if (isShown){
//                symbol = "S";
//            }else {
//                symbol = "^";
//            }
//
//       }
//    }
}
