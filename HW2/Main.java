import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final String HELP = """
            /r - выбрать случайного студента из имеющегося списка,
            /l - показать студента с оценками
            /h - показать данную подсказку
            /showAll - показать список всех студентов
            /showAllWithMarks - показать список всех студентов с оценками
            """;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // словарь вида "студент:оценка"
        // если оценка равна -1, то студент еще не отвечал
        // флаг присутсвия
        Map<String,Integer> studentsMarks = new HashMap<String,Integer>();
        getStudents(studentsMarks, scanner);  // добавляем студентов
        boolean[] isPresent = new boolean[studentsMarks.size()];  // массив флагов присутсвия студента на занятии
        for (int i = 0; i < studentsMarks.size(); ++i) {
            isPresent[i] = false;
        }

        activateHelp(studentsMarks, scanner);

    }

    public static boolean haveAllStudentsAnswered(Map<String,Integer> studentsMarks) {
        for (String key : studentsMarks.keySet()) {
            if (studentsMarks.get(key) == -1) {
                return false;
            }
        }
        return true;
    }

    public static void activateHelp(Map<String,Integer> studentsMarks, Scanner scanner) {
        while (true) {
            printHelp(HELP);
            String command = scanner.next();
            boolean exit = false;
            switch (command) {
                case "/h" -> {
                    continue;
                }
                case "/l" -> {
                    System.out.println("Напишите имя & фамилию студента, оценку которого Вы хотите увидеть");
                    String name = scanner.next();
                    String surname = scanner.next();
                    String nameSurname = name + " " + surname;
                    System.out.println("nameSurname: " + nameSurname);

                    if (studentsMarks.containsKey(nameSurname)) {
                        int mark = studentsMarks.get(nameSurname);
                        System.out.println(mark == -1 ? "Нет оценок" : mark);
                    } else {
                        System.out.println("Упс, такого студента не существует!");
                    }
                }
                case "/r" -> {
                    exit = true;
                    rCase(studentsMarks, scanner);
                }
                case "/showAll" -> printAllStudents(studentsMarks);
                case "/showAllWithMarks" -> printStudentsWithMarks(studentsMarks);
                default -> System.out.println("Такой команды нет!");
            }
            if (exit) {
                break;
            }
        }
    }

    /**
     Функция обрабатывает случай когда пользователь хочет поставить
     рандомную оценку рандомному студенту из списка студентов
     */
    public static void rCase(Map<String,Integer> studentsMarks, Scanner scanner) {
        int studentsCounter = 0;
        while (true) {
            if (haveAllStudentsAnswered(studentsMarks)) {
                System.out.println("Все студенты ответили! Программа завершается.");
                break;
            }
            if (studentsCounter > 10) {
                System.out.println("Слишком много промахов. Пара заканчивается досрочно.");
                break;
            }
            String chosenStudent = chooseRandomStudent(studentsMarks);
            if (studentsMarks.get(chosenStudent) != -1) {  // выбранный студент уже отвечал
                continue;
            }

            System.out.println("Отвечает " + chosenStudent);
            System.out.println("Пристуствует ли на паре?");
            String isStudentPresent = scanner.next();
            if (isStudentPresent.equals("y")) {
                int markForCurrentStudent = (int) (Math.random() * 7) + 3;
                studentsMarks.put(chosenStudent, markForCurrentStudent);
                System.out.println("Оценка за ответ: " + markForCurrentStudent);
                activateHelp(studentsMarks, scanner);
                break;
            } else {
                System.out.println("Этого студента нет на паре! Выберите другого");
                ++studentsCounter;
            }
        }
    }

    public static void getStudents(Map<String,Integer> studentsMarks, Scanner scanner) {
        System.out.println("Введите количество студентов:");
        int studentsQuantity = Integer.parseInt(scanner.next());
        System.out.println("Введите фамилию/имя каждого из студентов:");
        for (int i = 0; i < studentsQuantity; ++i) {
            String name = scanner.next();
            String surname = scanner.next();
            studentsMarks.put(name + " " + surname, -1);
        }
    }

    public static void printHelp(String menu) {
        System.out.println("Справка:");
        System.out.println(menu);
        System.out.println();
    }

    public static void printAllStudents(Map<String,Integer> studentsMarks) {
        System.out.println("Список всех студентов:");
        for (String key : studentsMarks.keySet()) {
            System.out.println(key);
        }
        System.out.println();
    }

    public static void printStudentsWithMarks(Map<String,Integer> studentsMarks) {
        System.out.println("Оценки за семинар:");
        boolean hasAnyoneAnswered = false;
        for (String key : studentsMarks.keySet()) {
            int currentMark = studentsMarks.get(key);
            if (studentsMarks.get(key) != -1) {
                System.out.println(key + " - " + currentMark);
                hasAnyoneAnswered = true;
            }
        }
        if (!hasAnyoneAnswered) {
            System.out.println("На этом семинаре никто не отвечал!");
        }
    }

    public static String chooseRandomStudent(Map<String,Integer> studentsMarks) {
        Object[] mapKeysArray = studentsMarks.keySet().toArray();
        int indexOfChosenStudent = (int) (Math.random() * studentsMarks.size());
        String randomKey = (String) mapKeysArray[indexOfChosenStudent];
        return randomKey;
    }
}