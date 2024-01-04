import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;
public class VIEW_ADMIN {
    Scanner sc=new Scanner(System.in);
    MODEL_ADMIN model=new MODEL_ADMIN();
//    //public VIEW_ADMIN view=new VIEW_ADMIN();
     CONTROL_ADMIN control=new CONTROL_ADMIN();
     CONTROL_USER co=new CONTROL_USER();
        static boolean flag=true;
    public void  dashborad_admin() throws SQLException {
        String value="";
        String email="";
        try {
            do {
                System.out.println("******WELCOME******");
                System.out.println("1) SIGN IN \n2) SIGN UP");
                int enter = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the email:");
                 email = sc.nextLine().toLowerCase();
                System.out.println("Enter the password");
                String password = sc.nextLine().toLowerCase();
                 value=control.check_user_admin(email, password, enter);
                flag = !value.equals("USER") && !value.equals("ADMIN");
            }while(flag);
                if (value.equalsIgnoreCase("ADMIN")) {
                    System.out.println("WELCOME :" + value);
                    control.menu();
                }
                else if (value.equalsIgnoreCase("USER")) {
                    System.out.println("WELCOME :" + value);
                   co.menu(email);
                }
        }
        catch (Exception e) {
            System.out.println("INPUT MISMATCHED..he..........");
            System.out.println(e);
        }
    }
    public void deposit_view_admin() throws SQLException{
        System.out.println("ARE YOU WISH TO VIZUALIZE SECURITY DEPOSIT FOR VECHILE");
        if (sc.next().equalsIgnoreCase("yes")) {
            System.out.println("1) BIKE \n2) CAR ");
            if (sc.nextInt() == 1) {
                System.out.println();
                System.out.println("the SECURITY DEPOSIT TO BIKE : " + model.getGetDeposit_inital_bike());
                System.out.println();
            } else {
                System.out.println();
                System.out.println("THE SECURITY DEPOSIT TO CAR : " + model.getDeposit_inital_car());
                System.out.println();
            }

        } else {
            System.out.println("ARE YOU WISH TO CHANGE THE SECURITY DEPOSIT FOR VECHILE");
            if (sc.next().equalsIgnoreCase("yes")) {
                System.out.println("1) BIKE \n2) CAR ");
                if (sc.nextInt() == 1) {
                    System.out.println("ENTER THE AMOUNT TO CHANGE BIKE");
                    int change_deposit = sc.nextInt();
                    System.out.println(model.setGetDeposit_inital_bike(change_deposit)?"UPDATED SUCCESSFULLY AMOUNT IN BIKE":"NOT UPDATED SUCCESSFULLY");
                } else {
                    System.out.println("ENTER THE AMOUNT TO CHANGE CAR:");
                    int change_car_deposit = sc.nextInt();
                    System.out.println(model.setDeposit_inital_car(change_car_deposit)?"UPDATED SUCCESSFULLY AMOUNT IN CAR":"NOT UPDATED SUCCESSFULLY");
                }
            }
        }
    }
    public void visualize_all_vehicle(String type) throws SQLException {
        System.out.println("ARE YOU WISH TO VIZUALIZE ALL "+type+" Type \"YES\" OR \"NO\"");
        if(sc.next().equalsIgnoreCase("yes")) {
            System.out.println("VEHICLE MODEL: "+type);
            System.out.println(type+" DETAILS............");
            System.out.println("VEHICLE_MODEL || VEHICLE_NUMBER || VEHICLE_SERVICE_STATUS || VEHICLE_RENTAL_PRICE || VEHICLE_KILOMETER || AVAILABILITY");
            System.out.println();
            ArrayList<String>list= (ArrayList<String>) model.visualize_all_vehicle_view(type);
            for(String m:list)
            System.out.println(m);
            System.out.println();
        }
    }
    public void vizualize_all_due_vehicle(String type) throws SQLException {
        System.out.println("ARE YOU WISH TO VIZUALIZE ALL DUE SERVICES "+type+" Type \"YES\" OR \"NO\"");
        if(sc.next().equalsIgnoreCase("yes")){
            System.out.println("VEHICLE MODEL: "+type);
            System.out.println("VEHICLE_NUMBER|| VEHICLE_KILOMETER  || VEHICLE_SERVICE_STATUS");
            System.out.println();
            ArrayList<String>list1= (ArrayList<String>) model.vizualize_all_due_vehicle_view(type);
            for(String m:list1)
                System.out.println(m);
            System.out.println();
        }
    }

    public void vizualize_all_rental_nonrental_bikes_view(String type) throws SQLException {
        System.out.println("VEHICLE TYPE:  "+type);
        System.out.println("ARE YOU WISH TO VIZUALIZE RENTAL AND NON RENTAL "+type+" Type \"YES\" OR \"NO\"");
        if(sc.next().equalsIgnoreCase("yes")){
            System.out.println("1)NON_AVAILABLE  "+type+" LIST \n2)AVAILABLE  "+type+" LIST ");
            if(sc.nextInt()==1){
                ResultSet resultSet22 = model.available_check_view(type);
                if (resultSet22.next()) {
                    int rowCount2 = resultSet22.getInt("reww");
                    if (rowCount2 == 0) {
                        System.out.println("The table is empty for non_AVAILABLE in "+type+" table.");
                    }
                    else{
                        System.out.println("RENTAL "+type+"...........");
                        System.out.println("vehicle_model || vehicle_number  || vehicle_availability ");
                        System.out.println();
                        ArrayList<String> list3=(ArrayList<String>)model.rental_data(type);
                        for(String m:list3)
                            System.out.println(m);
                        System.out.println();
                    }
                }
            }
            else{
                System.out.println("AVAILABLE  "+type+"..........");
                ResultSet resultSet1=model.nonavailable(type);
                if (resultSet1.next()) {
                    int rowCount1 = resultSet1.getInt("rew");
                    if (rowCount1 == 0) {
                        System.out.println("The table is empty for AVAILABLE in "+type+" table.");
                    }
                    else{
                        System.out.println("vehicle_model || vehicle_number || vehicle_availability");
                        System.out.println();
                        ArrayList<String> list4= (ArrayList<String>) model.nonrental_data(type);
                        for(String m:list4)
                            System.out.println(m);
                        System.out.println();
                    }
                }
            }
        }
    }

    public void insert_vechile() throws SQLException {
        System.out.println("ARE YOU WISH TO ADD\n 1)BIKE \n 2)CAR ");
        int vechile_num=sc.nextInt();
        if(vechile_num==1){
            sc.nextLine();
            System.out.println("BIKE MODEL NAME:");
            String bike_model=sc.nextLine();
            System.out.println("BIKE NUMBER:");
            String bike_number=sc.nextLine();
            System.out.println("BIKE RENTAL PRICE:");
            int bike_rental_price=sc.nextInt();
            System.out.println("BIKE RUNNED KILOMETER TILL DATE");
            int bike_kilometer = sc.nextInt();
            System.out.println("BIKE SERIVE STATUS");
            String bike_service_status = sc.next();
            int rows=model.insert_bikes(bike_model,bike_number,bike_service_status,bike_rental_price,bike_kilometer);
            System.out.println(rows==1?"Bike Inserted succefully":"ERROR OCCURES SOME MISMATCH DATATYPE GIVEN:");
        }
        else{
            sc.nextLine();
            System.out.println("CAR MODEL NAME:");
            String car_model=sc.nextLine();
            System.out.println("CAR NUMBER:");
            String car_number=sc.nextLine();
            System.out.println("CAR RENTAL PRICE:");
            int car_rental_price=sc.nextInt();
            System.out.println("CAR RUNNED KILOMETER TILL DATE");
            int car_kilometer = sc.nextInt();
            System.out.println("CAR SERIVE STATUS");
            String car_service_status = sc.next();
            int rows=model.insert_cars(car_model,car_number,car_service_status,car_rental_price,car_kilometer);
            System.out.println(rows==1?"CAR Inserted succefully":"ERROR OCCURES SOME MISMATCH DATATYPE GIVEN:");
        }
        model.service_check();
    }

    public void modify_vechile() throws SQLException{
        System.out.println("ARE YOU WISH TO MODIFY \n 1)BIKE \n 2)CAR ");
        int vechile_num=sc.nextInt();
        String type=vechile_num==1?"bikes":"cars";
        System.out.println("WHICH DATA DO YOU NEED TO CHANGE \n1)VEHICLE_NUMBER \n2)VEHICLE_SERVICE_STATUS \n3)VEHICLE_RENTAL_PRIZE \n4)VEHICLE_KILOMETER");
        int a=sc.nextInt();
        sc.nextLine();
        switch (a){
            case 1:
                System.out.println("ENTER THE VEHICLE_NUMBER");
                String dbnum=sc.nextLine();
                System.out.println("ENTER THE UPDATE OF VEHICLE_NUMBER");
                String update_num=sc.nextLine();
                int rows1= model.update_vehicle_number(update_num,dbnum,type);
                System.out.println(rows1==1?type+" NUMBER UPDATE SUCCESSFULLY":" VECHILE NOT FOUND ");
                break;
            case 2:
                System.out.println("ENTER THE VEHICLE_NUMBER");
                String dbnum1=sc.nextLine();
                System.out.println("ENTER THE VEHICLE_SERVICE_STATUS");
                String update_status=sc.nextLine();
                int rows2=model.update_vehicle_service(update_status,dbnum1,type);
                System.out.println(rows2==1?type+" SERVICE UPDATE SUCCESSFULLY":" VECHILE NOT FOUND ");
                break;
            case 3:
                System.out.println("ENTER THE VEHICLE_NUMBER");
                String dbnum2=sc.nextLine();
                System.out.println("ENTER THE VEHICLE_RENTAL_PRIZE");
                int update_prize=sc.nextInt();
                int rows3=model.update_vehicle_rent(update_prize,dbnum2,type);
                System.out.println(rows3==1?type+" RENTAL PRICE UPDATE SUCCESSFULLY":" VECHILE NOT FOUND ");
                break;
            case 4:
                System.out.println("ENTER THE VEHICLE_NUMBER");
                String dbnum3=sc.nextLine();
                System.out.println("ENTER THE VEHICLE_KILOMETER");
                int update_kilo=sc.nextInt();
                int rows4=model.update_vehicle_kilometer(update_kilo,dbnum3,type);
                System.out.println(rows4==1?type+" KILOMETER UPDATE SUCCESSFULLY":" VECHILE NOT FOUND  ");
                break;
            default:
                System.out.println("ENTER THE VALID NUMBER CASE ");
        }
    }

    public void display_vechile() throws SQLException {
        String type="";
        System.out.println("ARE YOU WISH TO VIZUALIZE \n 1)BIKE \n 2)CAR");
        int vechile_num=sc.nextInt();
        type=vechile_num==1?"bikes":"cars";
        ResultSet resultSet = model.table_check(type);
        if (resultSet.next()) {
            int rowCount = resultSet.getInt("row_count");
            if (rowCount == 0) {
                System.out.println("The table is empty in vehicle table.");
            }
            else {
                visualize_all_vehicle(type);
                vizualize_all_due_vehicle(type);
                vizualize_all_rental_nonrental_bikes_view(type);
            }
        }
    }
//
    public void delete_vechile() throws SQLException{
        System.out.println("ARE YOU WISH TO DELETE VEHICLE FROM TABLE \n 1)BIKE \n 2)CAR ");
        int vechile_num=sc.nextInt();
        String type=vechile_num==1?"bikes":"cars";
            sc.nextLine();
            System.out.println("ENTER THE "+type+" NUMBER:");
            String bike_delnum=sc.nextLine();
            int rows=model.delete_vehicle(type,bike_delnum);
            System.out.println(rows==1?type+" VEHICLE DELETED SUCCESSFULLY FROM DATABASE":"VEHICLE NOT FOUND");
    }
    public void search() throws SQLException {
        System.out.println("SEARCH BY VECHILE NUMBER");
        String search_number=sc.next();
        ResultSet rs=model.search_type("bikes",search_number);
        if(rs.next()){
            int count=rs.getInt("demo");
            if(count==0){
                ResultSet rs1=model.search_count("cars",search_number);
                if(rs1.next()){
                    int count1=rs1.getInt("dem");
                    if(count1==0)
                        System.out.println("VECHILE NOT FOUND....");
                    else{
                        System.out.println("VECHILE TYPE = 'CAR'");
                        System.out.println();
                        System.out.println("CAR_MODEL || CAR_NUMBER || CAR_SERVICE_STATUS || CAR_RENTAL_PRICE || CAR_KILOMETER || AVAILABILITY");
                        ArrayList<String> list=model.vehicle_finding("cars",search_number);
                        for(String m:list)
                            System.out.println(m);
                        System.out.println();
                    }
                }
            }
            else{
                System.out.println("VECHILE TYPE = 'BIKE'");
                System.out.println();
                System.out.println("BIKE_MODEL || BIKE_NUMBER || BIKE_SERVICE_STATUS || BIKE_RENTAL_PRICE || BIKE_KILOMETER || AVAILABILITY");
                ArrayList<String> list=model.vehicle_finding("bikes",search_number);
                for(String m:list)
                System.out.println(m);
                System.out.println();
            }
        }

    }
}
