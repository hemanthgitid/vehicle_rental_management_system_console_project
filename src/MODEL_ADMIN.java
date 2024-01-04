import java.sql.*;
import java.util.*;
public class MODEL_ADMIN {
    public static Connection getconnect() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vehicle_rental_management","root","");
        return conn;
    }
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

    public int sigin(String email,String password) throws SQLException {
        String insert_sign_up;
        insert_sign_up = "insert into login_details(login_email,login_password)values('" + email + "','" + password + "')";
        return st.executeUpdate(insert_sign_up);
    }
    public ResultSet  account_verify(String email,String password) throws SQLException {
        String check_email_password = "select login_email,login_password from login_details where login_email='" + email + "'AND login_password='" + password + "'";
        ResultSet rows = st.executeQuery(check_email_password);
        return rows;
    }
    public int getDeposit_inital_car() throws SQLException {
        String set_bike_deposit="select vechile_amount from security_deposit where Vechile_model='car'";
        ResultSet rs1=st.executeQuery(set_bike_deposit);
        while(rs1.next())
            return rs1.getInt(1);
        return 0;
    }
    public boolean setDeposit_inital_car(int deposit_inital_car) throws SQLException {
        Connection con = getconnect();
        Statement st= con.createStatement();
        String set_car_deposit = "UPDATE security_deposit set vechile_amount=" + deposit_inital_car + " where Vechile_model='car'";
        st.executeUpdate(set_car_deposit);
        return true;
    }
    public int getGetDeposit_inital_bike() throws SQLException {
        String set_bike_deposit="select vechile_amount from security_deposit where Vechile_model='bike'";
        ResultSet rs=st.executeQuery(set_bike_deposit);
        while(rs.next())
            return rs.getInt(1);
        return 0;
    }
    public Boolean setGetDeposit_inital_bike(int getDeposit_inital_bike) throws SQLException {
        String set_bike_deposit="UPDATE security_deposit set vechile_amount="+getDeposit_inital_bike+" where Vechile_model='bike'";
        st.executeUpdate(set_bike_deposit);
        return true;
    }
    public void service_check() throws SQLException {
        String query_check1="update bikes set vehicle_service_status='NOSERVICED' where vehicle_kilometer>1500";
        st.executeUpdate(query_check1);
        String query_check2="update cars set vehicle_service_status='NOSERVICED' where vehicle_kilometer>1500";
        st.executeUpdate(query_check2);
    }
        public int insert_bikes(String bike_model,String bike_number,String bike_service_status,int bike_rental_price,int bike_kilometer) throws SQLException {
            String query="insert into bikes (vehicle_model,vechile_number,vehicle_service_status,rental_price,vehicle_kilometer) values('"+bike_model+"','"+bike_number+"','"+bike_service_status+"','"+bike_rental_price+"','"+bike_kilometer+"');";
            return st.executeUpdate(query);
        }
        public int insert_cars(String car_model,String car_number,String car_service_status,int car_rental_price,int car_kilometer) throws SQLException{
            String query="insert into cars (vehicle_model,vechile_number,vehicle_service_status,rental_price,vehicle_kilometer)values('"+car_model+"','"+car_number+"','"+car_service_status+"','"+car_rental_price+"','"+car_kilometer+"');";
            return  st.executeUpdate(query);
        }

        public int update_vehicle_number(String update_num,String dbnum,String db) throws SQLException {
           String query="update "+db+" set vechile_number='"+update_num+"' where vechile_number='"+dbnum+"';";
            return  st.executeUpdate(query);
        }
        public int update_vehicle_service(String update_status,String dbnum,String db) throws SQLException {
            String query="update "+db+" set vehicle_service_status='"+update_status+"' where vechile_number='"+dbnum+"';";
            return  st.executeUpdate(query);
        }
        public int update_vehicle_rent(int update_prize,String dbnum,String db) throws SQLException {
            String query="update "+db+" set rental_price='"+update_prize+"' where vechile_number='"+dbnum+"';";
            return  st.executeUpdate(query);
        }
        public int update_vehicle_kilometer(int update_kilo,String dbnum,String db) throws SQLException {
            String query="update "+db+" set vehicle_kilometer='"+update_kilo+"' where vechile_number='"+dbnum+"';";
            return  st.executeUpdate(query);
        }
        public ResultSet table_check(String type) throws SQLException {
            String query = "SELECT COUNT(*) AS row_count FROM "+type+";";
            return st.executeQuery(query);
        }
        public List<String>  visualize_all_vehicle_view(String type) throws SQLException {
            ArrayList<String>list=new ArrayList<>();
            String display = "select vehicle_model,vechile_number,vehicle_service_status,rental_price,vehicle_kilometer,availability from "+type+" ORDER BY rental_price ASC";
            ResultSet res = st.executeQuery(display);
            while (res.next())
                list.add(res.getString(1) + " || " + res.getString(2) + " || " + res.getString(3) + " || " + res.getInt(4) + " || " + res.getInt(5)+" || "+res.getString(6));
            return list;
        }
        public List<String> vizualize_all_due_vehicle_view(String type) throws SQLException {
            ArrayList<String>list1=new ArrayList<>();
            String servi_detail="select vechile_number,vehicle_kilometer,vehicle_service_status from "+type+" where  vehicle_service_status='NOSERVICED'";
            ResultSet res = st.executeQuery(servi_detail);
            while (res.next())
               list1.add(res.getString(1) + " || " + res.getString(2) + " || " + res.getString(3));
            return list1;
        }
        public ResultSet available_check_view(String type) throws SQLException {
            String rentall="select count(*) as reww from "+type+" where availability='NONAVAILABLE'";
            return st.executeQuery(rentall);
        }
        public ResultSet nonavailable(String type) throws SQLException {
            String non_rental ="select count(*) as rew from "+type+" where availability='AVAILABLE'";
            return st.executeQuery(non_rental);
        }
        public ArrayList<String> rental_data(String type) throws SQLException {
        ArrayList<String>list3=new ArrayList<>();
            String rental="select vehicle_model,vechile_number,availability from "+type+" where availability='NONAVAILABLE' ORDER BY rental_price ASC";
            ResultSet resultSet2 = st.executeQuery(rental);
            while(resultSet2.next())
                list3.add(resultSet2.getString(1)+" || "+resultSet2.getString(2) +resultSet2.getString(3));
           return list3;
        }
        public ArrayList<String>  nonrental_data(String type) throws SQLException {
            String inner="select vehicle_model,vechile_number,availability from "+type+" where availability='AVAILABLE' ORDER BY rental_price ASC";
            ResultSet resultSet11 = st.executeQuery(inner);
            ArrayList<String>list4=new ArrayList<>();
            while(resultSet11.next())
                list4.add(resultSet11.getString(1)+" || "+resultSet11.getString(2) +" || "+resultSet11.getString(3));
            return list4;
        }
        public int delete_vehicle(String type,String bike_delnum) throws SQLException {
            String delete_bike="delete from "+type+" where vechile_number='"+bike_delnum+"';";
            return st.executeUpdate(delete_bike);
        }

        public ResultSet search_type(String type,String search_number) throws SQLException {
            String search="select count(*) as demo from "+type+" where vechile_number='"+search_number+"';";
            return st.executeQuery(search);
        }
        public ResultSet search_count(String type,String search_number) throws SQLException {
            String search1="select count(*) as dem from "+type+" where vechile_number='"+search_number+"';";
            return st.executeQuery(search1);
        }
        public ArrayList<String> vehicle_finding(String type,String search_number) throws SQLException {
            ArrayList<String>list=new ArrayList<>();
            String search12="select * from "+type+" where vechile_number='"+search_number+"';";
            ResultSet rs12=st.executeQuery(search12);
            while(rs12.next())
                list.add(" "+rs12.getString(1)+"  "+rs12.getString(2)+"  "+rs12.getString(3)+"  "+rs12.getInt(4)+"  "+rs12.getInt(5)+"  "+rs12.getString(6));
            return list;
        }
}
