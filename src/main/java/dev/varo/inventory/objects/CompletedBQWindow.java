package dev.varo.inventory.objects;

import dev.varo.inventory.MonthTranslations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

@Document(collection = "completed-bq-windows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletedBQWindow {
    @Id
    private ObjectId id;
    private String name;
    private String month;
    private int quantity;
    private Date date;

    public CompletedBQWindow(String name, String month, int quantity) throws ParseException {
        this.name = name;
        this.month = month;
        this.quantity = quantity;
        createDate();
    }

    public void createDate() throws ParseException {
        String monthValue = MonthTranslations.convertToNumber(month);
        LocalDate currentDate = LocalDate.now();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("Estonia/Tallinn"));

        date = formatter.parse(String.format(
                "%s-%s-01", currentDate.getYear(), monthValue));
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
