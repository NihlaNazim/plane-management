
import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters and setters for Ticket attributes
    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket information
    public void printInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: $" + price);
        System.out.println("Person Information:");
        person.printInfo();
    }


    public void save() {
        String TicketSave = row + seat + ".txt";
        try (FileWriter writer = new FileWriter(TicketSave)) {
            writer.write("Ticket Information:\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: $ " + price + "\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("surname: " + person.getSurname() + "\n");
            writer.write("email: " + person.getEmail() + "\n");
            System.out.println("Ticket information saved to file: " + TicketSave);
        } catch (IOException e) {
            System.out.println("Error occurred." + e.getClass());
        }
    }
}