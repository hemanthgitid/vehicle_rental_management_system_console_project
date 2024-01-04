import java.sql.*;

public class MODEL_USER {
    static Connection con;
    static Statement st;
    static {
        try {
            con = getconnect();
            st = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getconnect() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vehicle_rental_management","root","");
        return conn;
    }
    public ResultSet outer_email_check(String email,String todayString) throws SQLException {
        String outer_user_count = "select count(*) as start_count from cart where user_email='"+ email +"' AND RENT=true AND rented_date='"+todayString+"';";// only two more added into cart if rent taken
        return st.executeQuery(outer_user_count);
    }
    public  ResultSet select_all(String vechile_number,String type) throws SQLException {
        String search = "select count(*) as demo from "+type+" where vechile_number='" + vechile_number + "';";
        return  st.executeQuery(search);
    }
    public ResultSet vechile_available(String vechile_model,String vechile_number) throws SQLException {
        String chech_availablity_vechile = "select count(*) as checking from "+vechile_model+" where availability = 'AVAILABLE' AND vechile_number='" + vechile_number + "';";
        return  st.executeQuery(chech_availablity_vechile);
    }
    public ResultSet price_find_out(String vechile_model,String vechile_number) throws SQLException {
        String price_vechile = "select rental_price from "+vechile_model+ " where vechile_number='"+vechile_number+"';";
        return st.executeQuery(price_vechile);
    }
    public ResultSet already_find(String email) throws SQLException {
        String already = "select count(*) as count_user from user_security_deposit where user_email='"+ email +"';";
        return  st.executeQuery(already);
    }
    public void insert_query(String user_name,String email) throws SQLException {
        String rented_details = "insert into user_security_deposit(user_name,user_email,security_deposit) values ('" + user_name + "','" + email + "'," + 30000 +");";
        int user_security = st.executeUpdate(rented_details);
    }
    public ResultSet non_rent(String email,String vechile_number) throws SQLException {
        String mismatch = "select count(*) as non_rentss from cart where user_email='" + email + "'AND  RENT=false AND user_vechile_number='"+ vechile_number +"';";
        return st.executeQuery(mismatch);
    }
    public void update_vehicle_true(String vechile_number,String email) throws SQLException {
        String update_exit = "update cart set RENT="+true+" where user_vechile_number='"+vechile_number+"' AND user_email='"+email+"';";
        int rows_end = st.executeUpdate(update_exit);
    }
    public void insert_cart_details(String user_name,String vechile_model,String vechile_number,int vechile_rental_price,String email) throws SQLException {
        String cart_details = "insert into cart(user_name,user_vechile_model,user_vechile_number,vehicle_amount,user_email,RENT) values('" + user_name + "','" + vechile_model + "','" + vechile_number + "','" + vechile_rental_price + "','" + email + "'," + true + ")";
        int cart_affected = st.executeUpdate(cart_details);
    }
    public void non_available(String vechile_model,String vechile_number) throws SQLException {
        String update_bikes_query = "UPDATE " + vechile_model + " SET availability='NONAVAILABLE' WHERE vechile_number='" + vechile_number + "';";
        int cart_update_bikes = st.executeUpdate(update_bikes_query);
    }
    public ResultSet vechile_details(String vechile_model,String vechile_number) throws SQLException {
        String vechile_rented_details = "select vehicle_model,vechile_number,vehicle_kilometer,rental_price from " + vechile_model + " where vechile_number='" + vechile_number + "';";
       return st.executeQuery(vechile_rented_details);
    }
    public ResultSet rent_false_check(String email,String vechile_number) throws SQLException {
        String non_rent_check="select count(*) as non_rent from cart where user_email='"+email+"' AND RENT=false AND user_vechile_number='"+vechile_number+"';";
        return st.executeQuery(non_rent_check);
    }
    public void insert_false(String user_name,String vechile_model,String vechile_number,int vechile_rental_price,String email) throws SQLException {
        String cart_details = "insert into cart(user_name,user_vechile_model,user_vechile_number,vehicle_amount,user_email,RENT) values('" + user_name + "','" + vechile_model + "','" + vechile_number + "','" + vechile_rental_price + "','" + email + "'," + false + ")";
        int cart_affected = st.executeUpdate(cart_details);
    }
    public ResultSet  count_remove_vehicle(String str_num) throws SQLException {
        String remove_count="select count(*) as count_remove from cart where user_vechile_number='"+str_num+"' AND RENT=false;";
        return st.executeQuery(remove_count);
    }
    public int  remove_vehicle(String str_num,String email) throws SQLException {
        String query1="delete from cart where user_vechile_number='"+str_num+"' AND user_email='"+email+"';";
        return st.executeUpdate(query1);
    }
    public ResultSet payfines_outer(String vechile_return_no) throws SQLException {
        String str_check="select count(*) as str_check_return from cart where user_vechile_number='"+vechile_return_no+"' AND RENT=true;";
        return st.executeQuery(str_check);
    }
    public void payfines_upadte(String vechile_type,String vechile_return_no) throws SQLException {
        String str1_update_vechile_table="update "+vechile_type+" set availability='AVAILABLE' where vechile_number='"+vechile_return_no+"';";
        int rows_affected_update=st.executeUpdate(str1_update_vechile_table);
    }
    public void return_date(String todayString,String email,String vechile_return_no) throws SQLException {
        String str_return = "update cart set return_date='" + todayString + "' , RENT=false where user_email='" + email + "' AND user_vechile_number='" + vechile_return_no + "';";
        int rows_affected=st.executeUpdate(str_return);
    }
    public ResultSet query1(String vechile_return_no) throws SQLException {
        String date_vechile_return = "select rented_date from cart where user_vechile_number='"+vechile_return_no+"';";
        return st.executeQuery(date_vechile_return);
    }
    public ResultSet query2(String vechile_type,String vechile_return_no) throws SQLException {
        String price_vechile_return = "select rental_price from "+vechile_type+" where vechile_number='"+vechile_return_no+"';";
        return   st.executeQuery(price_vechile_return);
    }
    public ResultSet query3(String email) throws SQLException {
        String depo_mo = "select security_deposit from user_security_deposit where user_email='"+email+"';";
        return st.executeQuery(depo_mo);
    }
    public void query4(double reduce, String email) throws SQLException {
        String upadte_security="update user_security_deposit set security_deposit="+reduce+" where user_email='"+email+"';";
        int security_update_money=st.executeUpdate(upadte_security);
    }
    public ResultSet query5(String email) throws SQLException {
        String select ="select user_name,user_vechile_model,user_vechile_number,rented_date,return_date,user_email from cart where user_email='"+email+"';";
        return st.executeQuery(select);
    }
    public ResultSet query6(String email) throws SQLException {
        String out_acc="select security_deposit from user_security_deposit where user_email='"+email+"';";
        return  st.executeQuery(out_acc);
    }
    public void query7(String email) throws SQLException {
        String del="delete from user_security_deposit where user_email='"+email+"';";
        int del_fin=st.executeUpdate(del);
    }
}
