import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class timeConverter {

    //Helper Methods
    public static String days(String time,int days)  {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DAY_OF_MONTH,days);
        //c.add(Calendar.MONTH,-days);

        String r = sdf.format(c.getTime());

        return r;
    }
    public static String minutes(String time,int min)  {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.MINUTE,min);

        String r = sdf.format(c.getTime());

        return r;

    }


    //returns true if two is after then one
    public static boolean between(String one, String two){
          Calendar c1 = Calendar.getInstance();
          Calendar c2 = Calendar.getInstance();
          SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            c1.setTime(sdf.parse(one));
            c2.setTime(sdf.parse(two));
        } catch (ParseException e) {
            e.printStackTrace();
        }

          if(c2.after(c1) == true){
              return true;
          }

        return false;
    }

}
