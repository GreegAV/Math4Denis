import java.io.FileWriter;
import java.util.Calendar;
import java.util.Scanner;

public class Runner {
    private static String resTask;
    private static int resultOfTask;
    private static long startWork = 0, endWork = 0;

    public static void main(String[] args) {
        int numTasks = 0;
        int totalRight;
        int totalWrong;

        Scanner scanner = new Scanner(System.in);

        try (FileWriter fileWriter = new FileWriter("results.txt", true)) {
            do {
                startWork = System.currentTimeMillis();
                System.out.println("Введите количество примеров (1-12).");
                System.out.print("Для выхода ввести 0. ");
                try {
                    numTasks = Integer.parseInt(scanner.nextLine());
                } catch (Exception ex) {
                    System.out.println("Допустим ввод только цифр! Запустите программу еще раз.");
                    continue;
                }
                if ((numTasks < 1) || (numTasks > 12)) continue;
                fileWriter.write("" + Calendar.getInstance().getTime());
                fileWriter.write("Блок из " + numTasks + " примеров.\n\r");
                totalRight = 0;
                totalWrong = 0;
                for (int i = 1; i <= numTasks; i++) {
                    switch ((int) (Math.random() * 10 % 3)) {
                        case 1: {
                            generateValidExpression();
                            break;
                        }
                        case 2: {
                            generateValidExpression2();
                            break;
                        }
                        default: {
                            generateValidExpression3();
                            break;
                        }
                    }

                    System.out.print("Пример " + i + ": " + resTask + "\t" + "Ответ : ");
                    fileWriter.write("Пример " + i + ": " + resTask + "\t" + "Ответ : ");
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
                endWork = System.currentTimeMillis();
                String results = summaryResults(numTasks, totalRight, totalWrong);
                System.out.println(results);

                fileWriter.write(results);
                fileWriter.flush();
            } while (numTasks > 0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Всего хорошего!");
    }

    private static void generateValidExpression() {
        // A +/- B*C +/- D
        boolean valid;
        do {
            valid = true;
            resTask = "";
            resultOfTask = 0;

            int randSign1 = (int) (Math.random() * 10 % 2);
            int randSign2 = (int) (Math.random() * 10 % 2);
            int randomB = (int) (Math.random() * 10);
            int randomC = (int) (Math.random() * 10);
            int randomA = (int) (Math.random() * 100);
            int randomD = (int) (Math.random() * 100);
            if (randSign1 == 1) {
                resultOfTask = randomA + randomB * randomC;
                resTask += randomA + "+" + randomB + "*" + randomC;
            } else {
                if (randomA >= randomB * randomC) {
                    resultOfTask = randomA - randomB * randomC;
                    resTask += randomA + "-" + randomB + "*" + randomC;
                } else {
                    valid = false;
                    continue;
                }
            }

            if (randSign2 == 1) {
                resultOfTask = resultOfTask + randomD;
                resTask += "+" + randomD;
            } else {
                if (resultOfTask >= randomD) {
                    resultOfTask = resultOfTask - randomD;
                    resTask += "-" + randomD;
                } else {
                    valid = false;
//                    continue;
                }
            }
        } while (!valid);
    }

    private static void generateValidExpression2() {
        // A*B +/- C*D
        boolean valid;
        do {
            valid = true;
            resTask = "";
            resultOfTask = 0;

            int randSign1 = (int) (Math.random() * 10 % 2);
            int randomA = (int) (Math.random() * 10);
            int randomB = (int) (Math.random() * 10);
            int randomC = (int) (Math.random() * 10);
            int randomD = (int) (Math.random() * 10);
            if (randSign1 == 1) {
                resultOfTask = randomA * randomB + randomC * randomD;
                resTask += randomA + "*" + randomB + "+" + randomC + "*" + randomD;
            } else {
                if (randomA * randomB > randomC * randomD) {
                    resultOfTask = randomA * randomB - randomC * randomD;
                    resTask += randomA + "*" + randomB + "-" + randomC + "*" + randomD;
                } else {
                    valid = false;
//                    continue;
                }
            }
        } while (!valid);
    }

    private static void generateValidExpression3() {
        // A*B +/- C +/- D
        boolean valid;
        do {
            valid = true;
            resTask = "";
            resultOfTask = 0;

            int randSign1 = (int) (Math.random() * 10 % 2);
            int randSign2 = (int) (Math.random() * 10 % 2);
            int randomA = (int) (Math.random() * 10);
            int randomB = (int) (Math.random() * 10);
            int randomC = (int) (Math.random() * 100);
            int randomD = (int) (Math.random() * 100);

            if (randSign1 == 1) {
                resultOfTask = randomA * randomB + randomC;
                resTask += randomA + "*" + randomB + "+" + randomC;
            } else {
                if (randomA * randomB >= randomC) {
                    resultOfTask = randomA * randomB - randomC;
                    resTask += randomA + "*" + randomB + "-" + randomC;
                } else {
                    valid = false;
                    continue;
                }
            }

            if (randSign2 == 1) {
                resultOfTask += randomD;
                resTask += "+" + randomD;
            } else {
                if (resultOfTask >= randomD) {
                    resultOfTask -= randomD;
                    resTask += "-" + randomD;
                } else {
                    valid = false;
//                    continue;
                }
            }
        } while (!valid);
    }

    private static String summaryResults(int numTasks, int totalRight, int totalWrong) {
        String res = "\n\rИтого:\n\r";
        res += "Правильных ответов: ";
        res += totalRight + "\n\r";
        res += "Неправильных ответов: ";
        res += totalWrong + "\n\r";
        int resultTotal = (int) Math.ceil((12 * totalRight / numTasks));
        res += "Оценка: ";
        res += resultTotal;
        res += "\n\r";
        res += "Затрачено времени: ";
        int spentTime = (int) (endWork - startWork);
//        res+=spentTime+"ms, ";
        res += spentTime / 60000 + " минут " + spentTime / 1000 + " секунд.";
        return res;
    }
}
