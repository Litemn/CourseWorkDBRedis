public class Levenshtein
{
    private String compOne;
    private String compTwo;
    private int[][] matrix;
    private Boolean calculated = false;

    public Levenshtein()
    {

    }

    public int getSimilarity(String one, String two)
    {
        compOne = one;
        compTwo = two;
       // if (!calculated) {
            setupMatrix();
       // }
        return matrix[compOne.length()][compTwo.length()];
    }

    public int[][] getMatrix()
    {
        setupMatrix();
        return matrix;
    }

    private void setupMatrix()
    {
        matrix = new int[compOne.length()+1][compTwo.length()+1];

        for (int i = 0; i <= compOne.length(); i++)
        {
            matrix[i][0] = i;
        }

        for (int j = 0; j <= compTwo.length(); j++)
        {
            matrix[0][j] = j;
        }

        for (int i = 1; i < matrix.length; i++)
        {
            for (int j = 1; j < matrix[i].length; j++)
            {
                if (compOne.charAt(i-1) == compTwo.charAt(j-1))
                {
                    matrix[i][j] = matrix[i-1][j-1];
                }
                else
                {
                    int minimum = Integer.MAX_VALUE;
                    if ((matrix[i-1][j])+1 < minimum)
                    {
                        minimum = (matrix[i-1][j])+1;
                    }

                    if ((matrix[i][j-1])+1 < minimum)
                    {
                        minimum = (matrix[i][j-1])+1;
                    }

                    if ((matrix[i-1][j-1])+1 < minimum)
                    {
                        minimum = (matrix[i-1][j-1])+1;
                    }

                    matrix[i][j] = minimum;
                }
            }
        }
        calculated = true;

    }


}