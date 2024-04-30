import java.util.*;

class Node<T> {
    T data;
    Node<T> next;
    Node<T> down;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.down = null;
    }
}

public class Main {
    static boolean gameEnded = false;
    static String winner = "";
    static boolean firstBingoOccurred1 = false;
    static boolean firstBingoOccurred2 = false;
    static boolean secondBingoOccurred1 = false;
    static boolean secondBingoOccurred2 = false;

    public static void main(String[] args) {
        int[][] user1Matrix;
        int[][] user2Matrix;

        if (card1 != null && card2 != null) {
            user1Matrix = card1;
            user2Matrix = card2;

            System.out.println("User 1 Matris:");
            printMatrixWithMinusOne(user1Matrix);
            System.out.println("\nUser 2 Matris:");
            printMatrixWithMinusOne(user2Matrix);
        } else {
            int rows = 3;
            int columns = 9;

            user1Matrix = completeMatrix(rows, columns);
            user2Matrix = completeMatrix(rows, columns);

            System.out.println("\nUser 1 Matris (Multi-Linked List):");
            printMultiLinkedList(matrixToMultiLinkedList(user1Matrix));
            System.out.println("\nUser 2 Matris (Multi-Linked List):");
            printMultiLinkedList(matrixToMultiLinkedList(user2Matrix));
        }

        Node<Integer> user1MultiLinkedList = matrixToMultiLinkedList(user1Matrix);
        Node<Integer> user2MultiLinkedList = matrixToMultiLinkedList(user2Matrix);

        checkNumberInMatrices(user1Matrix, user2Matrix);
    }
static int card1[][] = null;
    static int card2[][] = null;
   /* static int card1[][] = {{5, -1, 22, -1, 45, -1, 62, 73, -1},
                            {-1, 15, -1, 31, 47, 58, 68, -1, -1},
                            {-1, 17, 26, 38, -1, -1, -1, 78, 89}};

    static int card2[][] = {{4, -1, 24, -1, 47, -1, 65, 74, -1},
                            {-1, 14, -1, 33, 42, 54, 69, -1, -1},
                            {-1, 15, 25, 34, -1, -1, -1, 77, 85}};
*/
    public static int[][] completeMatrix(int rows, int columns) {
        int[][] matrix = new int[rows][columns];
        Random random = new Random();

        // Matrisi doldurma
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 5; j++) {
                int randomColumn;
                do {
                    randomColumn = random.nextInt(columns);
                } while (matrix[i][randomColumn] != 0);

                int randomNumber;
                do {
                    randomNumber = random.nextInt(9) + (randomColumn * 10) + 1;
                } while (contains(matrix, randomNumber));

                matrix[i][randomColumn] = randomNumber;
            }

            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = -1;
                }
            }
        }

        return matrix;
    }

    public static <T> Node<T> matrixToMultiLinkedList(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        Node<T> head = null;
        Node<T> prevRowHead = null;

        for (int i = 0; i < rows; i++) {
            Node<T> rowHead = null;
            Node<T> prev = null;

            for (int j = 0; j < columns; j++) {
                Node<T> newNode = new Node<>(null);

                if (matrix[i][j] == -1) {
                    newNode.data = (T) Integer.valueOf(-1);
                } else {
                    newNode.data = (T) Integer.valueOf(matrix[i][j]);
                }
                if (rowHead == null) {
                    rowHead = newNode;
                }
                if (prev != null) {
                    prev.next = newNode;
                }
                if (prevRowHead != null && j == 0) {
                    prevRowHead.down = newNode;
                }
                prev = newNode;
            }
            prevRowHead = rowHead;

            if (head == null && rowHead != null) {
                head = rowHead;
            }
        }
        return head;
    }

    public static <T> void printMultiLinkedList(Node<T> head) {
        while (head != null) {
            Node<T> current = head;
            while (current != null) {
                if (current.data instanceof Integer && (Integer) current.data == 0) {
                    System.out.print("-1\t"); // Data 0 ise -1 yazdır
                } else {
                    System.out.print(current.data + "\t");
                }
                current = current.next;
            }
            System.out.println();
            head = head.down;
        }
    }

    public static boolean contains(int[][] matrix, int target) {
        for (int[] row : matrix) {
            for (int num : row) {
                if (num == target) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkNumberInMatrices(int[][] matrix1, int[][] matrix2) {
        Random random = new Random();
        int[] randomPermutation = generatePermutation(90);

        for (int i = 0; i < 90; i++) {
            int randomNumber = randomPermutation[i];

            boolean isUser1 = contains(matrix1, randomNumber);
            boolean isUser2 = contains(matrix2, randomNumber);

            if (isUser1 && isUser2) {
                System.out.println("Her iki kullanicida da " + randomNumber + " sayisi mevcut.");
                updateMatrixWithParentheses(matrix1, randomNumber);
                updateMatrixWithParentheses(matrix2, randomNumber);

            } else if (isUser1) {
                System.out.println("User 1: " + "(" + randomNumber + ")" + " sayisi mevcut.");
                updateMatrixWithParentheses(matrix1, randomNumber);
                

            } else if (isUser2) {
                System.out.println("User 2: " + "(" + randomNumber + ")" + " sayisi mevcut.");
                updateMatrixWithParentheses(matrix2, randomNumber);
                

            } else {
                System.out.println("(" + randomNumber + ")" + " sayisi herhangi bir kullanicida bulunmuyor.");
            }

            if (checkBingo(matrix1, matrix2)) {
                gameEnded = true;

                System.out.println("User 1 :");
                printMatrixWithMinusOne(matrix1);
                System.out.println("\nUser 2 :");
                printMatrixWithMinusOne(matrix2);
                break;
            }
            if (!firstBingoOccurred1 && checkBingoForUser(matrix1)) {
                    System.out.println("User 1 First Cinko");
                    firstBingoOccurred1 = true;
                }
                
                if (!firstBingoOccurred2 && checkBingoForUser(matrix2)) {
                    System.out.println("User 2 First Cinko");
                    firstBingoOccurred2 = true;
                }
                

            System.out.println("User 1 :");
            printMatrixWithMinusOne(matrix1);
            System.out.println("\nUser 2 :");
            printMatrixWithMinusOne(matrix2);
        }

        if (gameEnded) {
            System.out.println("Kazanan: " + winner + " \nOyun Bitti ");
        }
    }

    public static int[] generatePermutation(int n) {
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i + 1;
        }
        shuffleArray(permutation);
        return permutation;
    }

    public static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void printMatrixWithMinusOne(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == -1) {
                    System.out.print("-1\t");
                } else if (matrix[i][j] < 0) {
                    System.out.print("(" + (-matrix[i][j]) + ")\t");
                } else {
                    System.out.print(matrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    public static void updateMatrixWithParentheses(int[][] matrix, int number) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == number) {
                    matrix[i][j] = -number; // Sayıyı paranteze al
                }
            }
        }
    }

    public static boolean checkBingo(int[][] matrix1, int[][] matrix2) {
        if (checkParenthesizedNumbers(matrix1) && checkParenthesizedNumbers(matrix2)) {
            System.out.println("Her iki kullanıcı da Bingo (Tombala) yaptı!");
            winner = "Berabere";
            return true;
        } else if (checkParenthesizedNumbers(matrix1)) {
            System.out.println("User 1 Bingo (Tombala) yaptı!");
            winner = "User 1";
            return true;
        } else if (checkParenthesizedNumbers(matrix2)) {
            System.out.println("User 2 Bingo (Tombala) yaptı!");
            winner = "User 2";
            return true;
        }
        return false;
    }

    public static boolean checkParenthesizedNumbers(int[][] matrix) {
        int parenthesizedCount = 0;
        for (int[] row : matrix) {
            for (int num : row) {
                if (num < 0 && num != -1) {
                    parenthesizedCount++;
                }
            }
        }
        return parenthesizedCount == 15;
    }

    public static boolean checkBingoForUser(int[][] matrix) {
        int parenthesizedCount = 0;
        for (int[] row : matrix) {
            int count = 0;
            for (int num : row) {
                if (num < 0 && num != -1) {
                    count++;
                }
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }
    
    /* public static boolean checkMatrix(int[][] matrix) {
        
        if (matrix == null || matrix.length != 3 || matrix[0].length != 9) {
            System.out.println("Matrisin boyutu 3 satır 9 sütun olmalıdır.");
            return false;
        }

        // Her sütun için doğru aralıkta sayıların bulunduğunu kontrol et
        int[] startValues = {1, 10, 20, 30, 40, 50, 60, 70, 80}; 
        for (int j = 0; j < matrix[0].length; j++) {
            int expectedStart = startValues[j]; 
            int expectedEnd = expectedStart + 9;
            boolean[] seen = new boolean[91]; 
            for (int i = 0; i < matrix.length; i++) {
                int num = matrix[i][j];
              
                if (num == -1) {
                    continue;
                }
               
                if (seen[num]) {
                    System.out.println("Hata: Satır " + (i + 1) + " sütun " + (j + 1) + " aynı sayıyı içeriyor (" + num + ").");
                    return false;
                }
                seen[num] = true;
               
                if (num < expectedStart || num > expectedEnd) {
                    System.out.println("Hata: Satır " + (i + 1) + " sütun " + (j + 1) + " aralıkta olmalıdır (" + num + ").");
                    return false;
                }
            }
        }

        
        for (int i = 0; i < matrix.length; i++) {
            int count = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 0) {
                    count++;
                }
            }
            if (count != 5) {
                System.out.println("Hata: Satır " + (i + 1) + " sadece 5 tane pozitif sayı içermelidir.");
                return false;
            }
        }

        return true;
    }*/

   
}
