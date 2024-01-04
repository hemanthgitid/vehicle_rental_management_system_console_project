import java.sql.SQLException;
import java.util.Scanner;
//
public class CONTROL_USER {
    public void menu(String email) throws SQLException {
        VIEW_USER viewUser = new VIEW_USER();
        VIEW_ADMIN viewAdmin = new VIEW_ADMIN();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("-----SIGHT-----");
            System.out.println("1)VIEW ALL VECHILES: \n2)RENT A VECHILE: \n3)REMOVE A VECHILE FROM CART: \n4)PAYMENT: \n5)VIEW ALL YOUR DETAILS:  \n6)CLOSE ACCOUNT:  \n7)EXIT:");
            int USER_NO = sc.nextInt();
            switch (USER_NO) {
                case 1:
                    viewAdmin.visualize_all_vehicle("bikes");
                    viewAdmin.visualize_all_vehicle("cars");
                    break;
                case 2:
                    viewUser.rent_a_vechile(email);
                    break;
                case 3:
                    viewUser.remove_cart_vechile(email);
                    break;
                case 4:
                    viewUser.payfines(email);
                    break;
                case 5:
                    viewUser.display_user_details(email);
                    break;
                case 6:
                    viewUser.close_account(email);
                    break;
                case 7:
                    System.out.println("OUTED");
                    flag = false;
                    break;
            }
        }
    }
}
