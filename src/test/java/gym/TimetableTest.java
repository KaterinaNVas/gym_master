package gym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import static gym.TrainingSession.Age;
import static gym.TrainingSession.DayOfWeek;


public class TimetableTest {

    @Test
    void testAddNewTrainingSession_SingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, sessions.size(), "Должно быть добавлено 1 занятие");
        assertEquals(session, sessions.getFirst(), "Добавленное занятие должно совпадать");
    }

    @Test
    void testAddNewTrainingSession_MultipleSessionsSameDay() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session3 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(16, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(3, sessions.size(), "Должно быть добавлено 3 занятия");

        // Проверяем порядок (сортировка по времени)
        assertEquals(new TimeOfDay(10, 0), sessions.get(0).getTimeOfDay(),
                "Первым должно быть занятие в 10:00");
        assertEquals(new TimeOfDay(13, 0), sessions.get(1).getTimeOfDay(),
                "Вторым должно быть занятие в 13:00");
        assertEquals(new TimeOfDay(16, 0), sessions.get(2).getTimeOfDay(),
                "Третьим должно быть занятие в 16:00");
    }

    @Test
    void testAddNewTrainingSession_MultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Алексей", "Петрович");
        Coach coach2 = new Coach("Петрова", "Мария", "Сергеевна");

        Group group1 = new Group("Фитнес", Age.ADULT, 60);
        Group group2 = new Group("Йога", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        assertEquals(2, sessions.size(), "В 10:00 должно быть 2 занятия");
        assertTrue(sessions.contains(session1), "Должен быть session1");
        assertTrue(sessions.contains(session2), "Должен быть session2");
    }

    @Test
    void testAddNewTrainingSession_EmptyTimetable() {
        Timetable timetable = new Timetable();

        // Проверяем, что в пустом расписании нет занятий
        for (DayOfWeek day : DayOfWeek.values()) {
            List<TrainingSession> sessions =
                    timetable.getTrainingSessionsForDay(day);
            assertEquals(0, sessions.size(),
                    "В день " + day + " не должно быть занятий");
        }
    }

    @Test
    void testAddNewTrainingSession_DifferentDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        TrainingSession monday = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession wednesday = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));
        TrainingSession friday = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(monday);
        timetable.addNewTrainingSession(wednesday);
        timetable.addNewTrainingSession(friday);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }


    @Test
    void testGetTrainingSessionsForDay_SingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size(), "В понедельник должно быть 1 занятие");
        assertEquals(singleTrainingSession, mondaySessions.getFirst(),
                "Занятие должно совпадать с добавленным");

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySessions.size(), "Во вторник не должно быть занятий");
    }

    @Test
    void testGetTrainingSessionsForDay_MultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Понедельник - 1 занятие
        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size(), "В понедельник должно быть 1 занятие");
        assertEquals(mondayChildTrainingSession, mondaySessions.getFirst(),
                "Занятие должно быть детской акробатикой в 13:00");

        // Четверг - 2 занятия (сначала 13:00, потом 20:00)
        List<TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size(), "В четверг должно быть 2 занятия");
        assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0),
                "Первым должно быть занятие в 13:00");
        assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1),
                "Вторым должно быть занятие в 20:00");

        // Вторник - 0 занятий
        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, tuesdaySessions.size(), "Во вторник не должно быть занятий");
    }

    @Test
    void testGetTrainingSessionsForDay_AllDaysEmpty() {
        Timetable timetable = new Timetable();

        for (DayOfWeek day : DayOfWeek.values()) {
            List<TrainingSession> sessions =
                    timetable.getTrainingSessionsForDay(day);
            assertEquals(0, sessions.size(),
                    "В день " + day + " не должно быть занятий в пустом расписании");
        }
    }

    @Test
    void testGetTrainingSessionsForDay_CheckImmutability() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        timetable.addNewTrainingSession(session);

        // Получаем список
        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        // Пытаемся изменить полученный список
        sessions.add(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));

        // Проверяем, что оригинальное расписание не изменилось
        List<TrainingSession> actualSessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, actualSessions.size(),
                "Расписание не должно измениться при изменении полученного списка");
    }

    @Test
    void testGetTrainingSessionsForDay_NotFoundReturnsEmptyList() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        timetable.addNewTrainingSession(session);

        // Запрашиваем день, которого нет в расписании
        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertNotNull(sessions, "Должен возвращаться не null список");
        assertEquals(0, sessions.size(), "Должен возвращаться пустой список");
    }


    @Test
    void testGetTrainingSessionsForDayAndTime_ExactMatch() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, sessionsAt13.size(),
                "В понедельник в 13:00 должно быть 1 занятие");
        assertEquals(singleTrainingSession, sessionsAt13.getFirst(),
                "Занятие должно совпадать с добавленным");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_NoMatch() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, sessionsAt14.size(),
                "В понедельник в 14:00 не должно быть занятий");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_WrongDay() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessionsAt13Tuesday =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        assertEquals(0, sessionsAt13Tuesday.size(),
                "Во вторник в 13:00 не должно быть занятий");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_MultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Алексей", "Петрович");
        Coach coach2 = new Coach("Петрова", "Мария", "Сергеевна");

        Group group1 = new Group("Фитнес", Age.ADULT, 60);
        Group group2 = new Group("Йога", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        assertEquals(2, sessions.size(),
                "В 10:00 должно быть 2 занятия");
        assertTrue(sessions.contains(session1), "Должен быть session1");
        assertTrue(sessions.contains(session2), "Должен быть session2");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime_EmptyTimetable() {
        Timetable timetable = new Timetable();

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        assertEquals(0, sessions.size(),
                "В пустом расписании не должно быть занятий");
        assertNotNull(sessions, "Должен возвращаться не null список");
    }


    @Test
    void testDisplayTimetable_EmptyTimetable() {
        Timetable timetable = new Timetable();

        // Проверяем, что метод выполняется без ошибок для пустого расписания
        assertDoesNotThrow(timetable::displayTimetable,
                "displayTimetable не должен выбрасывать исключение для пустого расписания");
    }

    @Test
    void testDisplayTimetable_WithSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session);

        // Проверяем, что метод выполняется без ошибок
        assertDoesNotThrow(timetable::displayTimetable,
                "displayTimetable не должен выбрасывать исключение");
    }

    @Test
    void testDisplayTimetable_MultipleDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));

        // Проверяем, что метод выполняется без ошибок для нескольких дней
        assertDoesNotThrow(timetable::displayTimetable,
                "displayTimetable не должен выбрасывать исключение для нескольких дней");
    }


    @Test
    void testGetTimetable_ReturnsMap() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session);

        var map = timetable.getTimetable();
        assertNotNull(map, "Метод getTimetable должен возвращать не null");
        assertTrue(map.containsKey(DayOfWeek.MONDAY),
                "Должен содержать день MONDAY");
    }

    @Test
    void testSetTimetable_ReplacesSchedule() {
        Timetable timetable1 = new Timetable();
        Timetable timetable2 = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable1.addNewTrainingSession(session);

        // Устанавливаем расписание из timetable1 в timetable2
        timetable2.setTimetable(timetable1.getTimetable());

        List<TrainingSession> sessions =
                timetable2.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, sessions.size(),
                "После setTimetable должно быть 1 занятие");
    }

    @Test
    void testSetTimetable_WithEmptyMap() {
        Timetable timetable = new Timetable();

        // Создаём пустое расписание через конструктор
        Timetable emptyTimetable = new Timetable();

        // Копируем пустое расписание
        timetable.setTimetable(emptyTimetable.getTimetable());

        for (DayOfWeek day : DayOfWeek.values()) {
            List<TrainingSession> sessions =
                    timetable.getTrainingSessionsForDay(day);
            assertEquals(0, sessions.size(),
                    "После установки пустого расписания не должно быть занятий");
        }
    }

    @Test
    void testSetTimetable_WithNull() {
        Timetable timetable = new Timetable();

        // Проверяем, что установка null не вызывает ошибку
        assertDoesNotThrow(() -> timetable.setTimetable(null),
                "setTimetable(null) не должен выбрасывать исключение");
    }

    @Test
    void testComplexScenario_AddAndRetrieveMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Алексей", "Петрович");
        Coach coach2 = new Coach("Петрова", "Мария", "Сергеевна");

        Group group1 = new Group("Фитнес", Age.ADULT, 60);
        Group group2 = new Group("Йога", Age.ADULT, 60);
        Group group3 = new Group("Детская акробатика", Age.CHILD, 45);

        // Добавляем 5 занятий в разные дни
        timetable.addNewTrainingSession(new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group3, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group1, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(11, 0)));

        // Проверяем понедельник
        List<TrainingSession> monday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(2, monday.size());
        assertEquals(new TimeOfDay(10, 0), monday.get(0).getTimeOfDay());
        assertEquals(new TimeOfDay(12, 0), monday.get(1).getTimeOfDay());

        // Проверяем среду
        List<TrainingSession> wednesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);
        assertEquals(1, wednesday.size());
        assertEquals(new TimeOfDay(15, 0), wednesday.getFirst().getTimeOfDay());

        // Проверяем пятницу и субботу
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY).size());

        // Проверяем вторник и четверг (пустые)
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        // Проверяем поиск по дню и времени
        List<TrainingSession> monday10 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        assertEquals(1, monday10.size());
        assertEquals(group1, monday10.getFirst().getGroup());
    }

    @Test
    void testComplexScenario_SameTrainerMultipleDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        // Один тренер ведёт занятия в разные дни
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).size());

        // Проверяем, что во все дни одно и то же время
        for (DayOfWeek day : new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}) {
            List<TrainingSession> sessions =
                    timetable.getTrainingSessionsForDayAndTime(day, new TimeOfDay(10, 0));
            assertEquals(1, sessions.size());
            assertEquals(coach, sessions.getFirst().getCoach());
        }
    }

    @Test
    void testComplexScenario_DifferentGroupsSameDay() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Взрослая группа", Age.ADULT, 90);
        Group groupChild = new Group("Детская группа", Age.CHILD, 60);

        // В четверг две группы в разное время
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        List<TrainingSession> thursday =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursday.size());
        assertEquals(Age.CHILD, thursday.get(0).getGroup().getAge());
        assertEquals(Age.ADULT, thursday.get(1).getGroup().getAge());

        // Проверяем конкретное время
        List<TrainingSession> at13 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        assertEquals(1, at13.size());
        assertEquals(Age.CHILD, at13.getFirst().getGroup().getAge());
    }
}