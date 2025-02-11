package tests;

import models.BookDataModel;
import models.CredentialsModel;

public class TestData {
    private static String login = System.getProperty("USERNAME");
    private static String password = System.getProperty("PASSWORD");
    public static CredentialsModel credentials = new CredentialsModel(login, password);
    private static String book_isbn = "9781449325862";
    private static String book_title = "Git Pocket Guide";
    public static final BookDataModel book = new BookDataModel(book_isbn, book_title);
}