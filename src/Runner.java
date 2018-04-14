import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Runner {
    static String resTask;
    static int resultOfTask;
    static long iter = 0;

    public static void main(String[] args) {
        int numTasks = 0;
        int totalRight = 0;
        int totalWrong = 0;


        Scanner scanner = new Scanner(System.in);

        try (
                FileWriter fileWriter = new FileWriter("results.txt", true);
//                FileWriter fileTracker = new FileWriter("track.txt", true)
        ) {
            // запись всей строки
            do {
                System.out.println("Введите количество примеров (1-12).");
                System.out.print("Для выхода ввести 0. ");
                numTasks = Integer.parseInt(scanner.nextLine());
                if ((numTasks < 1) || (numTasks > 12)) continue;

                fileWriter.write("Блок из " + numTasks + " примеров.\n\r");
                // random1 - первое слагаемое(целое до 100)
                // random2 - первый оператор(=/-)
                // random3 - первый множитель(целое до 10)
                // random4 - второй множитель(целое до 10)
                // random5 - второй оператор(=/-)
                // random6 - последнее слагаемое(целое до 100)
                totalRight=0;
                totalWrong=0;
                for (int i = 1; i <= numTasks; i++) {
//                    generateValidExpression(fileTracker);
                    generateValidExpression();

                    System.out.print("Пример " + i + ": " + resTask+"\t"+"Ответ : ");
                    fileWriter.write("Пример " + i + ": " + resTask + "\t"+"Ответ : ");
//                    System.out.print("Ответ : ");
//                    fileWriter.write("Ответ ");
                    int answer = Integer.parseInt(scanner.nextLine());

                    if (answer == resultOfTask) {
                        System.out.println("Правильно!");
                        fileWriter.write("" + answer + " правильный.\n\r");
                        totalRight++;
                    } else {
                        System.out.println("Неправильно!. Правильный ответ : " + resultOfTask);
                        fileWriter.write("" + answer + " неправильный. Правильный ответ : " + resultOfTask + "\n\r");
                        totalWrong++;
                    }
                }
                String results = summaryResults(numTasks, totalRight, totalWrong);
                System.out.println(results);
                fileWriter.write(results);
                fileWriter.flush();

            } while (numTasks > 0);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Всего хорошего!");
    }

    //    private static void generateValidExpression(FileWriter fileTracker) throws IOException {
    private static void generateValidExpression() {
        boolean valid = true;
        do {
            valid = true;
            resTask = "";
            resultOfTask = 0;
//            fileTracker.write("\n\r");

            int random2 = (int) (Math.random() * 10 % 2);
            int random5 = (int) (Math.random() * 10 % 2);
            int random3 = (int) (Math.random() * 10);
            int random4 = (int) (Math.random() * 10);
            int random1 = (random2 == 1) ? (int) (Math.random() * 100) : (int) (50 + Math.random() * random3*random4);
            int random6 = (int) (Math.abs(Math.random() * (random1 - random3 * random4)));
            iter++;
//            fileTracker.write("Iteration " + iter + " Source: " + random1 + ((random2==1)?"+":"-")+ random3+ "*" + random4 + ((random5==1)?"+":"-") +  random6 + "\t");
//            fileTracker.flush();
            if (random2 == 1) {
                resultOfTask = random1 + random3 * random4;
                resTask += random1 + "+" + random3 + "*" + random4;
//                valid = true;
            } else {
                if (random1 >= random3 * random4) {
                    resultOfTask = random1 - random3 * random4;
                    resTask += random1 + "-" + random3 + "*" + random4;
//                    valid = true;
                } else {
                    resultOfTask = random1 - random3 * random4;
                    resTask += random1 + "-" + random3 + "*" + random4;
//                    fileTracker.write("Условие: " + resTask + " Ответ: " + resultOfTask + "\n\r");
                    valid = false;
//                    fileTracker.flush();
                    continue;
                }
            }

            if (random5 == 1) {
                resultOfTask = resultOfTask + random6;
                resTask += "+" + random6;
//                valid = true;
            } else {
                if (resultOfTask >= random6) {
                    resultOfTask = resultOfTask - random6;
                    resTask += "-" + random6;
//                    valid = true;
                } else {
                    resultOfTask = resultOfTask - random6;
                    resTask += "-" + random6;
//                    fileTracker.write("Условие: " + resTask + "Ответ: " + resultOfTask + "\n\r");
                    valid = false;
//                    fileTracker.flush();
                    continue;
                }
            }
        } while (!valid);
    }

    private static String summaryResults(int numTasks, int totalRight, int totalWrong) {
        String res = "\n\rИтого:\n\r";
        res += "Правильных ответов: ";
        res += totalRight;
        res += "\n\r";
        res += "Неправильных ответов: ";
        res += totalWrong;
        res += "\n\r";
        int resultTotal = (int) Math.ceil((12 * totalRight / numTasks));
//        int resultTotal=totalRight;
        res += "Оценка: ";
        res += resultTotal;
        res += "\n\r";
        return res;
    }
}
