import java.time.*;

public class SmallCalendar {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now(); //current date
        int month = date.getMonthValue(); //current month
        int today = date.getDayOfMonth(); //current day

        date = date.minusDays(today - 1); //the first day of current month
        int week = date.getDayOfWeek().getValue(); //1=mon, 7=sun

        System.out.println("Mon Tue Wed THU fri sat sun");
        for (int i=1; i<week; i++)
            System.out.print("    "); //四空格(Mon )
        
        //outprint the calender
        while (date.getMonthValue() == month){
            System.out.printf("%3d",date.getDayOfMonth()); //每个日期占四空格，对齐
            if (date.getDayOfMonth()==today)
                System.out.print("*");
            else
                System.out.print(" ");
            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() == 1) //newline if it's Monday
                System.out.println();
        }
        
        if (date.getDayOfWeek().getValue() != 1)
            System.out.println();
    }
}
