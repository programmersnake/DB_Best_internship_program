import java.sql.*;

// встроенная база данных

/*
0) Начало программы.
1) Чтение csv файлов.
2) Считывние с них данные в переменные.
3) Формирование графа.
4) Проход по графу Алгоритмом Дейкстры и нахождение true/false и длины (если true).
5) Сохранение результатов в новый csv файл.
6) Создание таблицы во встроенной БД H2 и запись в нее этих данные с файлов.
7) Конец.
 */


public class H2jdbcCreateDemo {
    private static final String CREATE_QUERY =
            "CREATE TABLE EXAMPLE (GREETING VARCHAR(6), TARGET VARCHAR(30))";
    /**
     * Query that populates table with data.
     */
    private static final String DATA_QUERY =
            "INSERT INTO EXAMPLE VALUES('Hello','World! it`s me!')";

    /**
     * Entry point.
     *
     * @param args Commans line args. Not used.
     */
    public static void main(final String[] args) {
        try (Connection db = DriverManager.getConnection("jdbc:h2:mem:")) {
            try (Statement dataQuery = db.createStatement()) {
                dataQuery.execute(CREATE_QUERY);
                dataQuery.execute(DATA_QUERY);
            }

            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM EXAMPLE")) {
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    System.out.println(String.format("%s, %s!",
                            rs.getString(1),
                            rs.getString("TARGET")));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }
    }
}