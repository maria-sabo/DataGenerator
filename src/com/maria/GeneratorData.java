package com.maria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


import static java.lang.Integer.max;
import static java.sql.Date.valueOf;
//import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class GeneratorData {
    private static final int DATE_BIRTH_FROM = 1400;
    private static final int DATE_BIRTH_TO = 1940;
    private static final int COUNT_CITY = 10;
    private static final int COUNT_COUNTRY = 10;
    private static final int COUNT_ADDRESS = 10;
    private static final int DATE_PLUS_BIRTH = 20;
    private static final int DATE_PLUS_DEATH = 80;
    private static final int COUNT_NOTE = 10000;
    private static final int DATE_PLUS_PIC = 20;
    private static final int SIZE_PIC_FROM = 20;
    private static final int SIZE_PIC_TO = 200;
    private static final int COUNT_MATERIAL = 10;
    private static final int COUNT_PIC = 1000;
    private static final int COUNT_AUCTION = 50;
    private static final int COUNT_EXHIBITION = 1000;


    private static final int NOWDAY = 2018;
    private static final int MAX_COST = 2000000;

    private Connection conn = null;


    public void connect() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/pictures";
        String username = "postgres";
        String password = "123451";
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Database connection established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database disconnection was done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public java.sql.Date randomDate(int min, int max) {
        int y = randBetween(min, max);
        int m = randBetween(1, 12);
        int d = randBetween(1, 30);
        Calendar cr = new GregorianCalendar();
        cr.set(y, m, d);
        java.sql.Date date = new java.sql.Date(cr.getTime().getTime());
        return date;
    }

    public void insertTableAuthor(String name, Date dB, Date dD, String note, String p1, String p2) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Author\" (name, \"Date of Birth\"," +
                "\"Date of death\", note, \"place of Birth\", \"place of death\") values (?, ?, ?, ?, ?, ?)");

        ps.setString(1, name);
        ps.setDate(2, dB);
        ps.setDate(3, dD);
        ps.setString(4, note);
        ps.setString(5, p1);
        ps.setString(6, p2);

        ps.executeUpdate();
        ps.close();
    }
    public void insertTableAuthor(String name, Date dB, String note, String p1) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Author\" (name, \"Date of Birth\"," +
                "note, \"place of Birth\") values (?, ?, ?, ?)");

        ps.setString(1, name);
        ps.setDate(2, dB);
        ps.setString(3, note);
        ps.setString(4, p1);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTableGenre(String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Genre\" (name) values (?)");
        ps.setString(1, name);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTableArtDir(String name, int from, int to) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"ArtDirection\" (name, \"from\", \"to\") values (?, ?, ?)");
        ps.setString(1, name);
        ps.setInt(2, from);
        ps.setInt(3, to);
        ps.executeUpdate();
        ps.close();
    }
    void insertTableArtDir(String name, int from) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"ArtDirection\" (name, \"from\") values (?, ?)");
        ps.setString(1, name);
        ps.setInt(2, from);

        ps.executeUpdate();
        ps.close();
    }
    void insertTableArtDir(String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"ArtDirection\" (name) values (?)");
        ps.setString(1, name);
        ps.executeUpdate();
        ps.close();
    }

    public void insertTableMuseum(String name, String city, String country, String address, int dateOfFound) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Museum\" (name, city, country, address, \"date of foundation\") values (?, ?, ?, ?, ?)");

        ps.setString(1, name);
        ps.setString(2, city);
        ps.setString(3, country);
        ps.setString(4, address);
        ps.setInt(5, dateOfFound);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTableExhibitions(String name, int museum, Date from, Date to) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Exhibitions\" (name, museum, \"from\", \"to\") values (?, ?, ?, ?)");

        ps.setString(1, name);
        ps.setInt(2, museum);
        ps.setDate(3, from);
        ps.setDate(4, to);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTablePicture(String name, int author, int genre, int art, int year, int height, int width, String material, String path) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Picture\" (name, author, genre, \"art direction\", \"year of painting\", height, width, material, pathtoimage) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, name);
        ps.setInt(2, author);
        ps.setInt(3, genre);
        ps.setInt(4, art);
        ps.setInt(5, year);
        ps.setInt(6, height);
        ps.setInt(7, width);
        ps.setString(8, material);
        ps.setString(9, path);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTablePictureAuthor(int author, int picture) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"PictureAuthor\" (author, picture) values (?,?)");
        ps.setInt(1, author);
        ps.setInt(2, picture);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTablePictureExhibition(int picture, int exhibition) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"PictureExhibition\" (picture, exhibition) values (?,?)");
        ps.setInt(1, picture);
        ps.setInt(2, exhibition);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTablePictureMuseum(int picture, int museum, Date from) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"PictureMuseum\" (picture, museum, \"from\") values (?, ?, ?)");
        ps.setInt(1, picture);
        ps.setInt(2, museum);
        ps.setDate(3, from);

        ps.executeUpdate();
        ps.close();
    }
    public void insertTablePictureMuseum(int picture, int museum) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"PictureMuseum\" (picture, museum) values (?, ?)");
        ps.setInt(1, picture);
        ps.setInt(2, museum);

        ps.executeUpdate();
        ps.close();
    }

    public void insertTableTheft(int picture, Date from, Date to) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Theft\" (picture, \"from\", \"to\") values (?,?,?)");
        ps.setInt(1, picture);
        ps.setDate(2, from);
        ps.setDate(3, to);

        ps.executeUpdate();
        ps.close();
    }
    public void insertTableTheft(int picture, Date from) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Theft\" (picture, \"from\") values (?,?)");
        ps.setInt(1, picture);
        ps.setDate(2, from);
        ps.executeUpdate();
        ps.close();
    }

    public void insertTableAuction(String name, int picture, int seller, int buyer, long cost, Date date) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into \"Auction\" (name, picture, seller, buyer, cost, \"date\") values (?, ?, ?, ?, ?, ?)");
        ps.setString(1, name);
        ps.setInt(2, picture);
        ps.setInt(3, seller);
        ps.setInt(4, buyer);
        ps.setLong(5, cost);
        ps.setDate(6, date);

        ps.executeUpdate();
        ps.close();
    }

    public ResultSet selectFromTables(String select) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public int getCountInSelect(ResultSet rs) throws SQLException {
        int count = 0;
        while (rs.next()) {
            count++;
        }
        return count;
    }

    /* Дата рождения художника в пределах (1400, 1940)
       Дата смерти художника – рандомно в пределах (год рождения художника + 20, год рождения художника + 80)
       или null для ~2/3 родившихся после 1940
       Примечания рандомно в (1, 10000)
       Место рождения, место смерти рандомно из рандомной страны и рандомного города
       Имя художника из даты рождения и места рождения
       Если добавление полностью не произошло, то rollback
    */
    public void addToAuthor(int count) throws SQLException {
        conn.setAutoCommit(false);
        try {
            for (int i = 0; i < count; i++) {
                java.sql.Date date = randomDate(DATE_BIRTH_FROM, DATE_BIRTH_TO);
                int year = Integer.parseInt(date.toString().substring(0, 4));
                java.sql.Date dated;
                if (year > DATE_BIRTH_TO - 10 && randBetween(1, 3) == 1)
                    dated = null;
                else
                    dated = randomDate(year + DATE_PLUS_BIRTH, year + DATE_PLUS_DEATH);
                String p2 = " ";
                String note = "Notes" + randBetween(1, COUNT_NOTE);
                String p1 = "City_" + randBetween(1, COUNT_COUNTRY) + "_" + randBetween(1, COUNT_CITY);
                String name = "Author_" + date.toString() + " born in " + p1;
                if (dated != null) {
                    p2 = "City_" + randBetween(1, COUNT_COUNTRY) + "_" + randBetween(1, COUNT_CITY);
                    insertTableAuthor(name, date, dated, note, p1, p2);
                }
                else {
                    insertTableAuthor(name, date, note, p1);
                }
            }
            conn.commit();
            System.out.println("added Authors " + count);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Страна рандомно (1, 10), город в стране рандомно (1, 10)
       Год основания музея рандомно в пределах дат рождения художников (1400, 1940)
       Адрес из страны, города и рандомного номера (1, 100) (Нарушение первой номральной формы...)
       Наименование музея использует адрес
       Если добавление полностью не произошло, то rollback
    */
    public void addToMuseum(int count) throws SQLException {

        conn.setAutoCommit(false);
        try {
            for (int i = 0; i < count; i++) {
                ResultSet rsN = selectFromTables("SELECT MAX(name) as mn FROM \"Museum\" Where name like 'Museum_%'");
                String name = "";
                while (rsN.next()) {
                    name = rsN.getString("mn");
                }
                if (name == null) name = "Museum_" + String.format("%06d",1);
                else {
                    int mnn = (Integer.parseInt(name.substring(7)) + 1);
                    name = "Museum_" + String.format("%06d",mnn);
                }
                int yrb = 0, yrd = 0;
                String country = "Country_" + randBetween(1, COUNT_COUNTRY);
                String city = country + " City_" + randBetween(1, COUNT_CITY);
                int dFoundation = randBetween(DATE_BIRTH_FROM, DATE_BIRTH_TO);
                String addr = country + ',' + city + "," + randBetween(1, COUNT_ADDRESS);
                //String name = "Museum in " + addr;

                insertTableMuseum(name, city, country, addr, dFoundation);
            }
            conn.commit();
            System.out.println("added Museums " + count);

        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }

    }

    /* Жанр берем рандомно из таблицы жанров
       Автора берем рандомно из таблицы авторов
       Год создания картины берем рандомно (год рождения автора + 20, год смерти автора)
       Направление живописи выбираем рандомно из таблицы направлений, если дата картины лежит в периоде существования направления
       (Все направление перекрывают период создания всех картин)
       Высоту и ширину картины выбираем рандомно (20, 200)
       Материал картины выбираем рандомно (1, 10)
       Название картины использует рандомное число (1, 1000) и имя автора
       Если добавление полностью не произошло, то rollback
    */
    public void addToPicture(int count) throws SQLException {

        conn.setAutoCommit(false);
        try {
            for (int i = 0; i < count; i++) {
                ResultSet rsA = selectFromTables("SELECT * FROM \"Author\" ORDER BY RANDOM() LIMIT 1");
                ResultSet rsG = selectFromTables("SELECT * FROM \"Genre\" ORDER BY RANDOM() LIMIT 1");
                int yrb = 0, yrd = 0;
                String nameA = " ";
                int author = 0;
                rsA.beforeFirst();
                Date db;
                Date dd = null;
                while (rsA.next()) {
                    author = rsA.getInt("id");
                    nameA = rsA.getString("name");
                    db = rsA.getDate("Date of Birth");
                    dd = rsA.getDate("Date of death");
                    yrb = Integer.parseInt(db.toString().substring(0, 4));
                    if (dd != null) yrd = Integer.parseInt(dd.toString().substring(0, 4));
                }

                int year;
                if (dd != null) year = randBetween(yrb + DATE_PLUS_PIC, yrd);
                else year = randBetween(yrb + DATE_PLUS_PIC, NOWDAY);

                int genre = 0;
                rsG.beforeFirst();
                while (rsG.next()) {
                    genre = rsG.getInt("id");
                }

                ResultSet rsArt = selectFromTables("SELECT * FROM \"ArtDirection\" WHERE \"from\" <= " + year
                        + " AND (\"to\" > " + year + " OR \"to\" is NULL) ORDER BY RANDOM() LIMIT 1");

                int countArtDir = getCountInSelect(rsArt);
                int artDir = 0;
                if (countArtDir != 0) {
                    rsArt.beforeFirst();
                    while (rsArt.next()) {
                        artDir = rsArt.getInt("id");
                    }
                } else System.out.println("There is no art direction for the year of painting: " + year);

                rsArt.close();
                int height = randBetween(SIZE_PIC_FROM, SIZE_PIC_TO);
                int width = randBetween(SIZE_PIC_FROM, SIZE_PIC_TO);
                String material = "Material" + randBetween(1, COUNT_MATERIAL);
                String name = "Picture" + randBetween(1, COUNT_PIC) + " " + nameA;
                String path = "";

                insertTablePicture(name, author, genre, artDir, year, height, width, material, path);
                rsA.close();
                rsG.close();
            }
            conn.commit();
            System.out.println("added Pictures " + count);

        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Размещаем в музеях только не размещенные картины
       Для этого выбираем все не размещенные картины и каждую помещаем в рандомный музеей
       Если добавление полностью не произошло, то rollback
    */
    public void addToPictureMuseum() throws SQLException {
        conn.setAutoCommit(false);
        try {
            ResultSet rsP = selectFromTables("select \"Picture\".id from \"Picture\" LEFT JOIN \"PictureMuseum\" " +
                    "ON \"Picture\".id = \"PictureMuseum\".picture " +
                    "WHERE \"PictureMuseum\".picture is NULL");
            int count = 0;
            while (rsP.next()) {
                int picture = rsP.getInt("id");
                ResultSet rsM = selectFromTables("SELECT * FROM \"Museum\" ORDER BY RANDOM() LIMIT 1");
                int museum = 0;
                rsM.beforeFirst();
                while (rsM.next()) {
                    museum = rsM.getInt("id");
                }
                insertTablePictureMuseum(picture, museum);
                count++;
            }
            conn.commit();
            System.out.println("added Picture in Museums " + count);

        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Выбираем заданное количество картин, для каждой из них рандомно выбираем соавтора != главному автору
       Если добавление полностью не произошло, то rollback
    */
    public void addToPictureAuthor(int count) throws SQLException {
        conn.setAutoCommit(false);
        try {
            ResultSet rsP = selectFromTables("SELECT * FROM \"Picture\" ORDER BY RANDOM() LIMIT " + count);
            while (rsP.next()) {
                int picture = rsP.getInt("id");
                int chief_author = rsP.getInt("author");
                ResultSet rsA = selectFromTables("SELECT * FROM \"Author\" WHERE id != " + chief_author + "ORDER BY RANDOM() LIMIT 1");
                int author = 0;
                rsA.beforeFirst();
                while (rsA.next()) {
                    author = rsA.getInt("id");
                }
                insertTablePictureAuthor(author, picture);
            }
            conn.commit();
            System.out.println("added soAuthors " + count);

        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Берем рандомно картину из таблицы картина-музей, у которой null дата конца пребывания в этом музее и которая не в краже
       Этот музей - продавец
       Дата аукциона рандомная, но позже даты написания картины, даты основания музея-продавца и даты, с которой картина находится у музея-продавца
       Покупателя выбираем из списка музеев, так чтобы год основания музея была меньше года проведения аукциона
       Меняем три таблицы: добавляем запись в таблицу аукционов, ставим дату окончания пребывания у продавшего музея в таблице картина-музей,
       добавляем дату в таблицу картина-музей о начале владения этой картиной другим музеем
       Если добавление полностью не произошло, то rollback
    */
    public void addToAuction(int count) throws SQLException {
        conn.setAutoCommit(false);
        try {
            for (int i = 0; i < count; i++) {
                ResultSet rsP = selectFromTables("SELECT pm.id, pm.picture, pm.museum, pm.\"from\", pi.\"year of painting\" as yp, mu.\"date of foundation\" as ym\n" +
                        "from \"Picture\" pi\n" +
                        "LEFT JOIN \"Theft\" th ON pi.id = th.picture \n" +
                        "LEFT JOIN \"PictureMuseum\" pm ON pi.id = pm.picture, \"Museum\" mu \n" +
                        "WHERE th.picture IS NULL AND pm.id IS NOT NULL AND pm.to IS NULL AND mu.id = pm.museum ORDER BY RANDOM() LIMIT 1");
                int picture = 0;
                int seller = 0;
                int buyer = 0;
                int id = 0;
                String name = "Auction_" + randBetween(1, COUNT_AUCTION);
                int c = randBetween(1, MAX_COST);
                Long cost = 1000 * Long.valueOf(c);
                java.sql.Date date = randomDate(1, NOWDAY);
                while (rsP.next()) {
                    picture = rsP.getInt("picture");
                    seller = rsP.getInt("museum");
                    int yp = rsP.getInt("yp");
                    int ym = rsP.getInt("ym");
                    id = rsP.getInt("id");
                    java.sql.Date from = rsP.getDate("from");
                    int yr;
                    if (from == null) {
                        yr = 0;
                    } else {
                        yr = Integer.parseInt(from.toString().substring(0, 4));
                    }
                    date = randomDate(max(ym, max(yp, yr)), NOWDAY);
                    int yearBayer = Integer.parseInt(date.toString().substring(0, 4));
                    ResultSet rsM = selectFromTables("SELECT * FROM \"Museum\" WHERE \"Museum\".\"date of foundation\" < " +
                            yearBayer + " ORDER BY RANDOM() LIMIT 1");
                    while (rsM.next()) {
                        buyer = rsM.getInt("id");
                    }
                }
                insertTableAuction(name, picture, seller, buyer, cost, date);

                PreparedStatement rsPM = conn.prepareStatement("UPDATE \"PictureMuseum\" SET \"to\" = " + "'" + date + "'" + " WHERE id = " + id);
                rsPM.executeUpdate();

                insertTablePictureMuseum(picture, buyer, date);
                rsPM.close();
            }
            conn.commit();
            System.out.println("added Auctions " + count);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Берем рандомно картину, которой нет в таблице краж
       Дата кражи берется рандомно от года написания картины до текущей
       Дата возврата картины берется либо равной null, либо рандомно от года кражи + 1 до текущего года
       Если добавление полностью не произошло, то rollback
    */
    public void addToTheft(int count) throws SQLException {
        conn.setAutoCommit(false);
        try {
            for (int i = 0; i < count; i++) {
                ResultSet rsP = selectFromTables("SELECT \"Picture\".* FROM \"Picture\" LEFT JOIN \"Theft\" ON \"Picture\".id = \"Theft\".picture " +
                        "WHERE \"Theft\".picture IS NULL ORDER BY RANDOM() LIMIT 1");
                while (rsP.next()) {
                    int picture = rsP.getInt("id");
                    int year = rsP.getInt("year of painting");
                    java.sql.Date from = randomDate(year, NOWDAY);
                    java.sql.Date to = null;
                    int t = randBetween(1, 2);

                    if (t == 2) {
                        to = randomDate(Integer.parseInt(from.toString().substring(0, 4)) + 1, NOWDAY);
                        insertTableTheft(picture, from, to);
                    } insertTableTheft(picture, from);

                }
            }
            conn.commit();
            System.out.println("added Thefts " + count);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Берем рандомно картину рандомно музей
       Название выставки составляем из рандомного числа (1, 1000) и музея, в котором проводится
       Дату начала берем рандомно от года основания музея до текущей
       Дату окончания берем рандомно (год начала выставки + 1, год начала выставки + 2)
       Если добавление полностью не произошло, то rollback
    */
    public void addToExhibitions(int count) throws SQLException {
        conn.setAutoCommit(false);
        try {
            for (int i = 0; i < count; i++) {
                int museum = 0;
                String nameM = "";
                int yrM = 0;

                ResultSet rsM = selectFromTables("SELECT * FROM \"Museum\" ORDER BY RANDOM() LIMIT 1");
                rsM.beforeFirst();
                while (rsM.next()) {
                    museum = rsM.getInt("id");
                    nameM = rsM.getString("name");
                    yrM = rsM.getInt("date of foundation");
                    java.sql.Date from = randomDate(yrM, NOWDAY);
                    java.sql.Date to = randomDate(Integer.parseInt(from.toString().substring(0, 4)) + 1, Integer.parseInt(from.toString().substring(0, 4)) + 2);
                    String name = "Exhibition_" + randBetween(1, COUNT_EXHIBITION) + " in " + nameM;
                    insertTableExhibitions(name, museum, from, to);
                }
            }
            conn.commit();
            System.out.println("added Exhibitions " + count);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    /* Берем картины, которые не на выставках и заносим в рандомную выставку, дата начала которой позже даты написания картины
       Если такой выставки не нашлось, не вставляем
       Если добавление полностью не произошло, то rollback
       Выводим, сколько реально вставлено
    */
    public void addToPictureExhibition(int count) throws SQLException {
        conn.setAutoCommit(false);
        try {
            ResultSet rsP = selectFromTables("SELECT \"Picture\".* FROM \"Picture\" LEFT JOIN \"PictureExhibition\" ON \"Picture\".id = \"PictureExhibition\".picture " +
                    "WHERE \"PictureExhibition\".picture IS NULL ORDER BY RANDOM() LIMIT " + count);
            int cnt = 0;
            while (rsP.next()) {
                int picture = rsP.getInt("id");
                int yr = rsP.getInt("year of painting");

                ResultSet rsE = selectFromTables("SELECT *, EXTRACT(YEAR FROM \"from\") FROM \"Exhibitions\" WHERE EXTRACT(YEAR FROM \"from\") > " + yr + " ORDER BY RANDOM() LIMIT 1");
                int exhibition = 0;
                rsE.beforeFirst();
                while (rsE.next()) {
                    exhibition = rsE.getInt("id");
                }
                if (exhibition != 0) {
                    insertTablePictureExhibition(picture, exhibition);
                    cnt++;
                }
            }
            conn.commit();
            System.out.println("added Picture in Exhibitions " + cnt);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    public ArrayList<String> fileToList(String filename) {
        ArrayList<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("File not found \n");
        }
        return arr;
    }

    public void addToGenre(String filename) throws SQLException {
        // C:\Users\808625\IdeaProjects\firstproj\src\genre.txt
        conn.setAutoCommit(false);
        try {
            ArrayList<String> arr = fileToList(filename);
            int count = 0;
            for (int i = 0; i < arr.size(); i++) {
                String element = arr.get(i);
                ResultSet rsM = selectFromTables("SELECT count(*) as cnt FROM \"Genre\" WHERE name = " + "'" + element + "'");
                int cnt = 0;
                while (rsM.next()) {
                    cnt = rsM.getInt("cnt");
                }
                if (cnt == 0) {
                    insertTableGenre(element);
                    count++;
                }
            }
            conn.commit();
            System.out.println("added  Genres " + count);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();

        }
    }

    public void addToArtDirection(String filename) throws SQLException {
        conn.setAutoCommit(false);
        try {
            ArrayList<String> arr = fileToList(filename);
            int count = 0;
            for (int i = 0; i < arr.size(); i++) {
                String element = arr.get(i);
                element = element.trim();
                if (element.length() != 0) {
                    String[] str = element.split(";");
                    ResultSet rsM = selectFromTables("SELECT count(*) AS cnt FROM \"ArtDirection\" WHERE name = " + "'" + str[0] + "'");
                    int cnt = 0;
                    while (rsM.next()) {
                        cnt = rsM.getInt("cnt");
                    }
                    if (cnt == 0) {
                        if (str.length == 3)
                            insertTableArtDir(str[0], Integer.valueOf(str[1]), Integer.valueOf(str[2]));
                        if (str.length == 2)
                            insertTableArtDir(str[0], Integer.valueOf(str[1]));
                        if (str.length == 1)
                            insertTableArtDir(str[0]);
                        count++;
                    }
                }
            }
            conn.commit();
            System.out.println("added ArtDirections " + count);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void createTables(String filename) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String contents = readUsingFiles(filename);
            PreparedStatement ps = conn.prepareStatement(contents);
            ps.executeUpdate();
            ps.close();
            //System.out.println(contents);
            conn.commit();
            System.out.println("created ");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("File not found \n");
        }
    }
}