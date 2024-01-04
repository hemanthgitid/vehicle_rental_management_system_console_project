import java.sql.*;
import java.util.Scanner;

 class CONTROL_ADMIN {
    public String  check_user_admin(String email,String password,int enter) throws SQLException {
   MODEL_ADMIN model=new MODEL_ADMIN() ;
   boolean val=false;
        String value="";
        if (enter == 1) {
            if (email.equals("hemanthkumarr563@gmail.com") && password.equals("hemanth2004")) {
                value = "ADMIN";
            }
            else {
               ResultSet rows=model.account_verify(email,password);
                    if (rows.next()) {
                        String email1=rows.getString(1);
                        String password1= rows.getString(2);
                        if (email1.equals(email) &&password1.equals(password)) {
                        value = "USER";
                        val=true;
                        }
                    }
                    if(!val)
                    System.out.println("You need to create a account: ");
            }
        }
        else{
                int rows=model.sigin(email,password);
                System.out.println(rows==1?"SIGN_UP DETAILS INSERTED SUCCESSFULLY":"SIGN_UP FAILED");
        }
     return value;
    }

    public void menu(){
        VIEW_ADMIN view=new VIEW_ADMIN();
        Scanner sc=new Scanner(System.in);
        boolean flag=true;
        try{
            while(flag){
                System.out.println("-----SIGHT-----");
                System.out.println("1)ADD VECHILE: \n2)MODIFY DETAILS: \n3)DISPLAY VECHILE: \n4)DELETE VECHILE: \n5)CHANGE SECURITY DEPOSIT: \n6)SEARCH VECHILE: \n7)EXIT");
                int sight_no=sc.nextInt();
                switch (sight_no){
                    case 1:
                        view.insert_vechile();
                        break;
                    case 2:
                        view.modify_vechile();
                        break;
                    case 3:
                        view.display_vechile();
                        break;
                    case 4:
                        view.delete_vechile();
                        break;
                    case 5:
                        view.deposit_view_admin();
                        break;
                    case 6:
                        view.search();
                        break;
                    case 7:
                        flag=false;
                        System.out.println("OUTED");
                        break;
                    default:
                        System.out.println("ENTER THE VALID NUMBER:");
                }
            }
        }catch (Exception e){
            System.out.println("THE ERROR MAY OCCUR BY DUPLICATE NUMBER AND SYNTAX ERROR ETC....");
            System.out.println(e);
        }
    }
}
