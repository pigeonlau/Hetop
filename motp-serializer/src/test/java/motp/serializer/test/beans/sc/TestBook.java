package motp.serializer.test.beans.sc;

import java.util.Date;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-17 20:24
 **/
public class TestBook {
    private String bookName;
    private int bookId;
    private int author;
    private Date publicDate;
    private double price;



    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
