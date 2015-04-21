/**
 * Created by litemn on 21.04.15.
 */
public class Car {
    public Car(int id_car, int id_model, int id_engine, int id_body_type, int mileage, int price, int final_price, String date_release, String pts, String color, String photo_id) {
        this.id_car = id_car;
        this.id_model = id_model;
        this.id_engine = id_engine;
        this.id_body_type = id_body_type;
        this.mileage = mileage;
        this.price = price;
        this.final_price = final_price;
        this.date_release = date_release;
        this.pts = pts;
        this.color = color;
        this.photo_id = photo_id;
    }

    private int id_car;

    public int getId_model() {
        return id_model;
    }

    public void setId_model(int id_model) {
        this.id_model = id_model;
    }

    public int getId_car() {
        return id_car;
    }

    public void setId_car(int id_car) {
        this.id_car = id_car;
    }

    public int getId_engine() {
        return id_engine;
    }

    public void setId_engine(int id_engine) {
        this.id_engine = id_engine;
    }

    public int getId_body_type() {
        return id_body_type;
    }

    public void setId_body_type(int id_body_type) {
        this.id_body_type = id_body_type;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFinal_price() {
        return final_price;
    }

    public void setFinal_price(int final_price) {
        this.final_price = final_price;
    }

    public String getDate_realease() {
        return date_release;
    }

    public void setDate_realease(String date_realease) {
        this.date_release = date_realease;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    private int id_model;
    private int id_engine;
    private int id_body_type;
    private int mileage;
    private int price;
    private int final_price;
    private String date_release, pts, color, photo_id;
    Car(){

    }
}
