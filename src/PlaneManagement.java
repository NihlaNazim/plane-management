import java.util.Scanner;
import java.util.InputMismatchException;
public class PlaneManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Ticket[] tickets= new Ticket[52];
        int[][] seats = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row A
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},       // Row B
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},       // Row C
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}  // Row D
        };
        System.out.println("Welcome to the Plane Management application");
        while (true) {
            System.out.println("***************************************************");
            System.out.println("*                  MENU OPTIONS                   *");
            System.out.println("***************************************************");
            System.out.println("     1) Buy a seat");
            System.out.println("     2) Cancel a seat");
            System.out.println("     3) Find first available seat");
            System.out.println("     4) Show seating plan");
            System.out.println("     5) Print tickets information and total sales");
            System.out.println("     6) Search tickets.");
            System.out.println("     0) Quit");
            System.out.println("***************************************************");

            System.out.print("Please select an option: ");
            int option;
            while (true) {
                if (input.hasNextInt()) {
                    option = input.nextInt();
                    if (option >= 0 && option <= 6) {
                        break;
                    } else {
                        System.out.println("Invalid option.please enter a number between 0 and 5");
                    }
                } else {
                    input.next();
                    System.out.println("Invalid option.please enter a number between 0 and 5.");
                }

        }

        switch (option) {
            case 0:
                System.out.println("Terminating program.");
                System.exit(0);
                break;
            case 1:
                buySeat(seats,tickets);
                break;
            case 2:
                cancelSeat(seats,tickets);
                break;
            case 3:
                findFirstAvailable(seats);
                break;
            case 4:
                showSeatingPlan(seats);
                break;
            case 5:
                printTicketInfo(tickets);
                break;
            case 6:
                searchTicket(tickets);
                break;
            default:
                System.out.println("Invalid option.");
                }
        }
    }

    public static void buySeat(int[][] seats,Ticket[] tickets) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Input the preferred row (A, B, C, or D): ");
            String row = input.next().toUpperCase();

            if (!row.matches("[ABCD]")) {
                System.out.println("Invalid row. Please enter a valid row (A, B, C, or D).");
                continue; // Allow user to re-enter row
            }

            System.out.print("Enter the seat number (1-14 for row A/D, 1-12 for row B/C): ");
            int seat;
            try {
                seat = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid seat number.");
                input.next(); // Consume invalid input
                continue; // Allow user to re-enter seat
            }

            if ((row.equals("A") || row.equals("D")) && (seat < 1 || seat > 14)) {
                System.out.println("Invalid seat number. Please enter a seat number between 1 and 14.");
                continue; // Allow user to re-enter seat
            } else if ((row.equals("B") || row.equals("C")) && (seat < 1 || seat > 12)) {
                System.out.println("Invalid seat number. Please enter a seat number between 1 and 12.");
                continue; // Allow user to re-enter seat
            }

            if (seats[row.charAt(0) - 'A'][seat - 1] == 0) {
                seats[row.charAt(0) - 'A'][seat - 1] = 1; // Mark seat as sold
                System.out.println("Booking confirmed for Row " + row + ", Seat " + seat + ".");

                //Promt user to enter name, surname and email
                System.out.println("Enter your name: ");
                String name=input.next();
                System.out.println("Enter your surname: ");
                String surname=input.next();
                System.out.println("Enter your email: ");
                String email=input.next();

                //creat a person object with user information
                Person person=new Person(name,surname,email);

                //you can store the Person object along with the seat booking
                System.out.println("Booking details: ");

                //Create a Ticket and add it to the array
                double price= calculatePrice(row,seat);
                Ticket booked=new Ticket(row,seat,price,person);

                for (int i=0;i<tickets.length;i++){
                    if (tickets[i]==null){
                        tickets[i]=booked;
                        break;
                    }
                }
                booked.save();
                booked.printInfo();
                break; // Exit loop once seat is booked
            } else {
                System.out.println("Sorry, the seat at Row " + row + ", Seat " + seat + " is already taken.");
            }
        }
    }

    private static double calculatePrice(String row,int seat) {
        if ((row.equals("A") || row.equals("D")) && (seat >= 1 || seat <=5)) {
            return 200.00;
        } else if ((row.equals("A") || row.equals("D")) && (seat >=6 || seat<=9)) {
            return 150.00;}
        else if ((row.equals("B")||row.equals("C")) && (seat >=1 || seat<=5)) {
            return 200.00;}
        else if ((row.equals("B")||row.equals("C")) && (seat >=6 || seat<=9)) {
            return 150.00;}
        else {
            return 180.00;
        }
    }

    public static void cancelSeat(int[][] seats,Ticket[] tickets) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the row letter (A, B, C, or D): ");
        String row = input.next().toUpperCase();

        if (!row.matches("[ABCD]")) {
            System.out.println("Invalid row. Please enter a valid row (A, B, C, or D).");
            return;
        }

        System.out.print("Enter the seat number (1-14 for row A/D, 1-12 for row B/C): ");
        int seat = input.nextInt();

        if ((row.equals("A") || row.equals("D")) && (seat < 1 || seat > 14)) {
            System.out.println("Invalid seat number. Please enter a seat number between 1 and 14.");
        } else if ((row.equals("B") || row.equals("C")) && (seat < 1 || seat > 12)) {
            System.out.println("Invalid seat number. Please enter a seat number between 1 and 12.");
        } else if (seats[row.charAt(0) - 'A'][seat - 1] == 1) {
            seats[row.charAt(0) - 'A'][seat - 1] = 0; // Mark seat as available

            for(int i=0;i< tickets.length;i++){
                if(tickets[i] !=null && tickets[i].getRow().equals(row)&& tickets[i].getSeat()== seat){
                    tickets[i]=null;
                    System.out.println("Seat cancellation confirmed.");
                    return;
                }
            }
            System.out.println("No ticket found for this seat.");
        } else {
            System.out.println("Invalid seat number or not reserved.");
        }
    }

    public static void findFirstAvailable(int[][] seats) {
        for (int row = 0; row < seats.length; row++) {
            for (int seat = 0; seat < seats[row].length; seat++) {
                if (seats[row][seat] == 0) {
                    char rowLetter = (char) ('A' + row);
                    System.out.println("First available seat: Row " + rowLetter + ", Seat " + (seat + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    public static void showSeatingPlan(int[][] seats) {
        System.out.println("Seating Plan:");
        for (int row = 0; row < seats.length; row++) {
            char rowLetter = (char) ('A' + row);
            System.out.print(rowLetter + " ");
            for (int seat = 0; seat < seats[row].length; seat++) {
                if (seats[row][seat] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
    public static void printTicketInfo(Ticket[] tickets){
        double totalSales=0;
        int totalTickets=0;

        System.out.println("Ticket Information:");
        for(Ticket ticket: tickets){
            if (ticket!=null){
                ticket.printInfo();
                totalSales+=ticket.getPrice();
                totalTickets++;
            }
        }
        if (totalTickets==0){
            System.out.println("No tickets have been booked yet.");
        }
        else{
            System.out.println("Total tickets sold: "+totalTickets);
            System.out.println("Total sales: $"+totalSales);
        }
    }
    public static void searchTicket(Ticket[] tickets){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the row letter (A,B,C, or D)");
        String row=input.next().toUpperCase();

        if (!row.matches("[ABCD]")){
            System.out.println("Invalid row. Please enter a valid row (A,B,C,or D)");
            return;
        }
        System.out.println("enter the seat number(1-14 for row A/D, 1-12 for B/C)");
        int seat=input.nextInt();

        boolean found = false;
        for (Ticket ticket:tickets){
            if (ticket !=null && ticket.getRow().equals(row) && ticket.getSeat()== seat){
                found=true;
                ticket.printInfo();
                break;
            }
        }
        if(!found){
            System.out.println("this seat is available.");
        }
    }
}
