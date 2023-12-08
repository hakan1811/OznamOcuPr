package sk.kusnierr.pushnotifyprochot;

public class CustomModel {
    private int id;
    private String date;
    private String predmet;
    private String body;

    //konstruktor


    public CustomModel(int id, String date, String predmet, String body) {
        this.id = id;
        this.date = date;
        this.predmet = predmet;
        this.body = body;
    }

    public CustomModel() {
    }

    //toString

    @Override
    public String toString() {
        return "{"+date+"}{"+predmet+"}{"+body+"}";
    }


    //getter a setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
