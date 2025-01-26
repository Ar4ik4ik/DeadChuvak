import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    final static String FILE_PATH;
    static Random randomIntGenerator = new Random();
    static FileReader inputTxtFile;
    static List<String> generatedWordsList = new ArrayList<>();
    static String unhiddenWord;
    static String hiddenWord;
    static List<String> enteredLetters;

    static {
        try {
            FILE_PATH = String.valueOf(Path.of("ResultAfterCleaningLessWords.txt"));
            inputTxtFile = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static int getRandomInteger() throws IOException {
        return randomIntGenerator.nextInt((int) Files.lines(Path.of(FILE_PATH)).count());
    }

    static void setNewRandomWord() {
        String randomWord;
        try {
            do {
                randomWord = Files.lines(Path.of(FILE_PATH)).skip(getRandomInteger() - 1).findFirst().get();
                if (!generatedWordsList.contains(randomWord)) {
                    generatedWordsList.add(randomWord);
                    unhiddenWord = randomWord;
                    break;
                }
            } while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean checkLetter(String letter) {
        return unhiddenWord.contains(letter);
    }

    static void hideWord() {
        hiddenWord = "_".repeat(unhiddenWord.length());
    }

    static void unhideLetter(char letter) {
        List<Integer> letterIndexes = new ArrayList<>();
        char[] temp = hiddenWord.toCharArray();
        for (int i = 0; i < unhiddenWord.length(); i++) {
            if (unhiddenWord.charAt(i) == letter) {
                letterIndexes.add(i);
            }
        }

        for (int i: letterIndexes) {
            temp[i] = unhiddenWord.toCharArray()[i];
        }
        hiddenWord = new String(temp);
    }

    static void resetData() {
        hiddenWord = "";
        unhiddenWord = "";
        enteredLetters.clear();
    }

    static String askLetter() {
        System.out.println("Введите букву");
        String answer;
        Scanner in;
        while (true) {
            in = new Scanner(System.in);
            answer = in.next().toLowerCase();
            if (!answer.matches("^[А-Яа-я]$")) {
                System.out.println("Вы ввели неверный символ !\nПопробуйте снова");
                continue;
            }
            if (!enteredLetters.contains(answer)) {
                enteredLetters.add(answer);
                return answer;
            } else {
                System.out.println("Вы уже вводили эту букву !");
            }
        }
    }

    static boolean menuNavigation() {
        while (true) {
            Scanner in = new Scanner(System.in);
            String answer = in.next().toLowerCase();
            switch (answer) {
                case "start":
                    enteredLetters = new ArrayList<>();
                    return true;
                case "exit":
                    return false;
                default:
                    System.out.println("""
                            Введена некорректная команда
                            Чтобы начать игру, введи слово "start"
                            Чтобы выйти из игры, введи слово "exit\"""");
            }
        }
    }

    static void greetPlayer() {
        System.out.print("""
            Привет ! Это игра - виселица, тебе необходимо отгадать слово,
            Максимальное количество ошибок - 7
            Чтобы начать игру, введи слово "start"
            Чтобы выйти из игры, введи слово "exit"
            """);
     }

    static void playCycle() {
        resetData();
        setNewRandomWord();
        hideWord();
        int mistakesCount = 0;
        while (mistakesCount <= 6) {
            printHangman(mistakesCount);
            System.out.println("Загаданное слово: " + hiddenWord);
            String enteredLetter = askLetter();
            if (checkLetter(enteredLetter)) {
                unhideLetter(enteredLetter.charAt(0));
            } else {
                mistakesCount += 1;
                System.out.println("Вы ввели неверную букву\nКоличество ошибок: " + mistakesCount);
            }
            if (hiddenWord.equals(unhiddenWord)) {
                System.out.println("Поздравляю ! Вы выиграли");
                System.out.println("""
                        Чтобы начать новую игру введите "start"
                        Чтобы выйти введите "exit"
                        """);
                return;
            }
        }
        System.out.println("Вы проиграли !\nНо не расстраивайтесь, попробуйте еще раз !");
        System.out.println("""
                        Чтобы начать новую игру введите "start"
                        Чтобы выйти введите "exit"
                        """);
    }

    static void startGame() {
        greetPlayer();
        while (true) {
            if (menuNavigation()) {
                playCycle();
            } else {
                break;
            }
        }

    }



    static void printHangman(int countMistakes) {
        System.out.println("===========================\n");
        switch (countMistakes) {
            case 0:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |
                                       |
                                       |
                                       |
                                       |
                                      /|\\
                                """
                );
                break;
            case 1:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |       0
                                       |
                                       |
                                       |
                                       |
                                      /|\\
                                """
                );
                break;
            case 2:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |       0
                                       |       |
                                       |
                                       |
                                       |
                                      /|\\
                                """
                );
                break;
            case 3:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |       0
                                       |       |\\
                                       |
                                       |
                                       |
                                      /|\\
                                """
                );
                break;
            case 4:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |       0
                                       |      /|\\
                                       |
                                       |
                                       |
                                      /|\\
                                """
                );
                break;
            case 5:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |       0
                                       |      /|\\
                                       |        \\
                                       |
                                       |
                                      /|\\
                                """
                );
                break;
            case 6:
                System.out.print(
                        """
                                   -------------------
                                       |       |
                                       |       0
                                       |      /|\\
                                       |      / \\
                                       |
                                       |
                                      /|\\
                                """
                );
                break;

        }
        System.out.println("===========================");
    }
}

