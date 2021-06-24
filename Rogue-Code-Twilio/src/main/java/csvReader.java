import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class csvReader {
    File fileName;
    DateTimeFormatter myDateTime;
    LocalDateTime myDateObj;
    String date;

    public csvReader(String name){
        //try catch block for file name
        try {
            fileName = new File(name);
        }
        catch(Exception e){
                System.out.println("error no such file : "+name);
            }
        //end try catch
        myDateTime = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        myDateObj = LocalDateTime.now();
        date = myDateObj.format(myDateTime);
    }


    // some sort of filting to make sure a phone number and a date is aways returned?
    public String[] scanAppointment(int days,int mins) {
        myDateObj = LocalDateTime.now();
        date = myDateObj.format(myDateTime);
        String[] r = new String[10];
        int idx =0;

        String row;
        Scanner sc = null;
        try {
            sc = new Scanner(fileName);
            sc.useDelimiter(",");
            while(sc.hasNext()){
                row = sc.nextLine();
                int commaL = row.indexOf(',');

                //sanitation
                String app =row.substring(0,commaL);
                String number = row.substring(commaL+1,row.length());
                number = number.replaceAll("[^0-9]","");
                app = app.replace("/","-");



                //TODO add validation for date
                if(number.length() != 10 ){
                    System.out.println("Bad phone number : "+number);
                }else {

                    //reassembly
                    row = app + "," + number;
                    //checks if date is between to times
                    if (timeConverter.between(timeConverter.days(date, days), app) == true && timeConverter.between(timeConverter.minutes(timeConverter.days(date, days), mins), app) == false) {
                        //adds to return array
                        if(r[r.length-1] != null){
                            r = arrayDouble(r);
                        }
                        r[idx] = row;
                        idx++;
                    }
                }

            }

            sc.close();
            return r;
        } catch (FileNotFoundException e) {
            System.out.println("reminder scanner failed to scan code");
            return null;
        }

    }

    public String[] massAlert(){
        String[] r = new String[10];
        String row;
        int idx = 0;


        Scanner sc = null;
        try {
            sc = new Scanner(fileName);

            sc.useDelimiter(",");
            while(sc.hasNext()) {
                row = sc.nextLine();
                int commaL = row.indexOf(',');
                if(r[r.length-1] != null){
                    r = arrayDouble(r);
                }
                r[idx] = row;
                idx++;

            }

        } catch (FileNotFoundException e) {
            System.out.println("was unable to open file : "+ fileName + " for Mass alert");
        }

        return r;
    }

    private String[] arrayDouble(String[]x ){
        String[] r = new String[x.length*2];
        System.arraycopy(x,0,r,0,x.length);
        return r;
    }


}
