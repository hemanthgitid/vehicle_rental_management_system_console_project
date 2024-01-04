import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VIEW_USER {
    MODEL_USER modelUser=new MODEL_USER();
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String todayString = currentDateTime.format(formatter);
    static String vechile_model=null;
    static String user_name=null;

        Scanner sc=new Scanner(System.in);
    public void rent_a_vechile(String email) throws SQLException {
        int  vechile_rental_price = 0;
        System.out.println("RENTING A VECHILE.............");
        sc.nextLine();
        System.out.println("ENTER YOUR NAME:");
             user_name = sc.nextLine().toLowerCase();
        ResultSet out_result_set = modelUser.outer_email_check(email,todayString);
        if (out_result_set.next()) {
            int count_outer_email = out_result_set.getInt("start_count");
            if (count_outer_email >= 2) {
                System.out.println(user_name + " you have done rent for a day :" + todayString);
            } else {
                System.out.println("ENTER THE VECHILE NUMBER");
                String vechile_number = sc.nextLine();
                ResultSet rs = modelUser.select_all(vechile_number,"bikes");
                if (rs.next()) {
                    int count = rs.getInt("demo");
                    if (count == 0) {
                        ResultSet rs1 =  modelUser.select_all(vechile_number,"cars");
                        if (rs1.next()) {
                            int count1 = rs1.getInt("demo");
                            if (count1 == 0)
                                System.out.println("VECHILE NOT FOUND....");
                            else {
                                vechile_model = "cars";
                            }
                        }
                    } else {
                        vechile_model = "bikes";
                    }
                    System.out.println("begin");
                    ResultSet check_availablity_vechile = modelUser.vechile_available(vechile_model,vechile_number);
                    if (check_availablity_vechile.next()) {
                        int check_availablity_vechile_count = check_availablity_vechile.getInt("checking");
                        if (check_availablity_vechile_count == 0)
                            System.out.println("YOU CANNOT RENT A VECHILE ITS NOT AVAILABLE");
                        else {
                            System.out.println("hello");
                            ResultSet der = modelUser.price_find_out(vechile_model,vechile_number);
                            while (der.next())
                                vechile_rental_price = der.getInt(1);
                            System.out.println("ARE YOU WISH TO RENT A VECHILE OR ADD TO CART TYPE 'YES' OR 'NO' :::::::::");
                            if (sc.nextLine().equalsIgnoreCase("yes")) {
                                ResultSet fds = modelUser.already_find(email);
                                if (fds.next()) {
                                    int czz = fds.getInt("count_user");
                                    if (czz == 0) {
                                       modelUser.insert_query(user_name,email);
                                    }
                                }
                                ResultSet mismatch_query = modelUser.non_rent(email,vechile_number);
                                if (mismatch_query.next()) {
                                    int mismatch_value = mismatch_query.getInt("non_rentss");
                                    if (mismatch_value == 1) {  //
                                        System.out.println("YOU ALREADY ADD TO CART SO IT DOESN'T GET ADD AGAIN");
                                        System.out.println("ARE YOU WISH TO RENT THE VECHILE TYPE YES OR NO");
                                        if (sc.nextLine().equalsIgnoreCase("yes")) {
                                          modelUser.update_vehicle_true(vechile_number,email);
                                        }
                                    }
                                    else modelUser.insert_cart_details(user_name,vechile_model,vechile_number,vechile_rental_price,email);
                                    modelUser.non_available(vechile_model,vechile_number);
                                    System.out.println("VECHILE DETAILS:::::::::::");
                                    ResultSet vechile_rented_details_select= modelUser.vechile_details(vechile_model,vechile_number);
                                    while (vechile_rented_details_select.next()) {
                                        System.out.println("VECHILE MODEL :" + vechile_rented_details_select.getString(1) +
                                                "\nVECHILE NUMBER :" + vechile_rented_details_select.getString(2) +
                                                "\nVECHILE RUINED KILOMETER :" + vechile_rented_details_select.getString(3) +
                                                "\nVECHILE RENTAL PRICE :" + vechile_rented_details_select.getString(4));
                                    }
                                }
                            }
                            else {
                                ResultSet non_ren=modelUser.rent_false_check(email,vechile_number);
                                if(non_ren.next()){
                                    int count_non_rent=non_ren.getInt("non_rent");
                                    if(count_non_rent==1){
                                        System.out.println("YOU ALREADY ADD TO CART SO IT DOESN'T GET ADD AGAIN:::::");
                                    }
                                    else {
                                        System.out.println("YOU ARE ADDING INTO A CART::");
                                       modelUser.insert_false(user_name,vechile_model,vechile_number,vechile_rental_price,email);
                                    }
                                }
                            }
                            System.out.println("======HAVE A SAFE JOURNEY ======");
                        }
                    }
                }
            }
        }
    }

    public  void remove_cart_vechile(String email) throws SQLException{
        System.out.println("ARE YOU WISH TO REMOVE A VECHILE FROM CART TYPE YES OR NO");
        if(sc.nextLine().equalsIgnoreCase("yes")){
            System.out.println("ENTER THE NAME OF VECHILE NUMBER");
            String str_num=sc.nextLine();
            ResultSet remove_count_outer=modelUser.count_remove_vehicle(str_num);
            if(remove_count_outer.next()){
                int count_remove_value=remove_count_outer.getInt("count_remove");
                if(count_remove_value==0){
                    System.out.println("YOU RENTED THE VECHILE DOES NOT REMOVE IT====");
                }
                else{
                  int rows=modelUser.remove_vehicle(str_num,email);
                    System.out.println(rows==1?"REMOVED SUCCEFULLY":"CANNOT REMOVED IT I THINK ITS OTHERS");
                }
            }
        }

    }

    public void payfines(String email) throws SQLException {
        double payfines = 0;
        boolean flag = false;
        String vechile_return_no;
        System.out.println("ARE YOU WISH TO RENTURN THE VECHILE ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("VECHILE MODEL LIKE(BIKES OR CARS):");
            String vechile_type = sc.nextLine().toLowerCase();
            System.out.println("ENTER THE VECHILE NUMBER::");
             vechile_return_no = sc.nextLine().toLowerCase();
            System.out.println("ENTER YES TO CONTINUE THE PROCES....");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                ResultSet str_chh = modelUser.payfines_outer(vechile_return_no);
                if (str_chh.next()) {
                    int ccc = str_chh.getInt("str_check_return");
                    if (ccc == 0) {
                        System.out.println("THE VECHILE NOT FOUND OR YOU MAY RETURN BACK IT RE-CHECK AGAIN!!!!!!!!!!");
                    } else {
                        modelUser.payfines_upadte(vechile_type, vechile_return_no);
                        modelUser.return_date(todayString, email, vechile_return_no);
                        ResultSet der3 = modelUser.query1(vechile_return_no);
                        String vechile_rental_date_return = null;
                        while (der3.next())
                            vechile_rental_date_return = der3.getString(1);

                        ResultSet der2 = modelUser.query2(vechile_type, vechile_return_no);
                        int vechile_rental_price_return = 0;
                        while (der2.next())
                            vechile_rental_price_return = der2.getInt(1);
                        int num1 = Integer.parseInt(vechile_rental_date_return.substring(Math.max(vechile_rental_date_return.length() - 2, 0)));
                        int num2 = Integer.parseInt(todayString.substring(Math.max(todayString.length() - 2, 0)));
                        int sub = (int) Math.abs(num2 - num1);
                        System.out.println((sub == 0) ? "YOU RETURN WITHIN A DAY" : ("YOU RENTED MORE DAYS" + sub));
                        if(sub>0) {
                          System.out.println("DO YOU RUN MORE THAN 500kms A DAY:");
                            if (sc.nextLine().equalsIgnoreCase("yes")) {
                                double sum = 0;
                                for (int i = 1; i <= sub; i++)
                                    sum += vechile_rental_price_return + (vechile_rental_price_return * 0.15);
                                payfines = sum;
                                flag = true;
                            }
                            else{
                                double sum=0;
                                for(int i=1;i<=sub;i++)
                                    sum+=vechile_rental_price_return;
                                payfines=sum;
                            }
                        }
                        else {
                            System.out.println("YOU NEED TO PAY RENT FOR VEHICLE " + vechile_return_no + " Model " + vechile_type + " THE AMOUNT " + vechile_rental_price_return);
                        }
                        System.out.println("ARE YOU DAMAGE THE VECHILE:::");
                        if (sc.nextLine().equalsIgnoreCase("yes")) {
                            flag = true;
                            System.out.println("ENTER THE TYPE OF DAMAGE YOU CAUSE LIKE(LOW OR MEDIUM OR HIGH)");
                            if (sc.nextLine().equalsIgnoreCase("low"))
                                payfines = vechile_rental_price_return + (vechile_rental_price_return * 0.20);
                            else if (sc.nextLine().equalsIgnoreCase("medium"))
                                payfines = vechile_rental_price_return + (vechile_rental_price_return * 0.50);
                            else if (sc.nextLine().equalsIgnoreCase("high"))
                                payfines = vechile_rental_price_return + (vechile_rental_price_return * 0.75);
                        }
                        if (flag) {
                            System.out.println("WE HAVE REDUCED THE FINES IN YOU SECURITY DEPOSIT AMOUNT");
                            ResultSet money = modelUser.query3(email);
                            int depo_money = 0;
                            while (money.next())
                                depo_money = money.getInt(1);
                            double reduce = depo_money - payfines;
                            reduce = Math.abs(reduce);
                            modelUser.query4(reduce, email);
                            System.out.println(reduce);
                        }
                    }
                }
            }
        }
    }
    public void  display_user_details(String email)throws SQLException{
        System.out.println("USER DETAILS........");
        ResultSet di=modelUser.query5(email);
        while(di.next())
            System.out.println("USER NAME :" + di.getString(1) + "  USER VECHILE MODEL :" + di.getString(2) + "  USER VECHILE NUMBER :" + di.getString(3) + "  RENTED DATE :" + di.getDate(4) + "  RETURN DATE :" + di.getDate(5) + " USER EMAIL :" + email);
    }


    public void  close_account(String email) throws SQLException{
        System.out.println("ARE YOU WISH TO CLOSE THE ACCOUNT::::::");
        if(sc.nextLine().equalsIgnoreCase("yes")){
            ResultSet ssr=modelUser.query6(email);
            int amount_user=0;
            while(ssr.next())
                amount_user=ssr.getInt(1);
            System.out.println("COLLECT YOUR REMAINING BALANCE:  "+amount_user);
            modelUser.query7(email);
        }
    }

}
