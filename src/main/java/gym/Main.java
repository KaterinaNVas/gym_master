package gym;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== Добро пожаловать в Gym Master! =====\n");

        // 1. Создаём тренеров
        Coach coach1 = new Coach("Иванов", "Алексей", "Петрович");
        Coach coach2 = new Coach("Петрова", "Мария", "Сергеевна");
        Coach coach3 = new Coach("Смирнов", "Дмитрий", "Игоревич");

        // 2. Создаём группы
        Group group1 = new Group("Фитнес для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Детская акробатика", Age.CHILD, 45);
        Group group3 = new Group("Йога для начинающих", Age.ADULT, 75);

        // 3. Создаём тренировки
        TrainingSession session1 = new TrainingSession(
                group1,
                coach1,
                DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)
        );

        TrainingSession session2 = new TrainingSession(
                group2,
                coach2,
                DayOfWeek.WEDNESDAY,
                new TimeOfDay(16, 30)
        );

        TrainingSession session3 = new TrainingSession(
                group3,
                coach3,
                DayOfWeek.FRIDAY,
                new TimeOfDay(18, 0)
        );

        // 4. Создаём расписание и добавляем тренировки
        Timetable timetable = new Timetable();
        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        // 5. Выводим расписание
        timetable.displayTimetable();

        System.out.println("\n===== Конец =====\n");
    }
}