import java.util.Scanner;
import java.util.Vector;

public class App_Assignment2{
    
    private static final Scanner SCANNER=new Scanner(System.in);
    public static void main(String[] args) {
        final String CLEAR="\033[H\033[2J";
        final String COLOR_BLUE_BOLD="\033[34;1m";
        final String COLOR_GREEN_BOLD="\033[32;1m";
        final String COLOR_RED_BOLD="\033[31;1m";
        final String RESET="\033[0m";
        final String DASHBOARD="💰 Welcome to Smart Banking App";
        final String OPEN_ACCOUNT="📂 Open New Account";
        final String DEPOSIT_MONEY="⏬ Deposit Money";
        final String WITHDRAW_MONEY="⏫ Deposit Money";
        final String TRANSFER_MONEY="🔁 Transfer Money";
        final String ACCOUNT_BALANCE="💲 Check Account Balance";
        final String DELETE_ACCOUNT="❌ Drop Existing Account";
        String screen=DASHBOARD;
        final String LINE="-".repeat(40);
        final String ERROR_MSG=String.format("%s%s%s",COLOR_RED_BOLD,"%s",RESET);
        final String SUCCESS_MSG=String.format("%s%s%s",COLOR_GREEN_BOLD,"%s",RESET);

        // String[][] accountDetails=new String[0][];
        Vector<String> accountNumber =new Vector<>();
        Vector<String> accountHoldername=new Vector<>();
        Vector<String> accountBalance=new Vector<>();

        do{
            final String APP_TITLE=String.format("%s%s%s",COLOR_BLUE_BOLD,screen,RESET);

            System.out.println(CLEAR);
            System.out.println(LINE);
            System.out.println(" ".repeat((40-APP_TITLE.length()+11)/2).concat(APP_TITLE));
            System.out.println(LINE);

            switch(screen){
                case DASHBOARD:
                System.out.println("\n[1]. Open New Account");
                System.out.println("[2]. Deposit Money");
                System.out.println("[3]. Withdraw Money");
                System.out.println("[4]. Transfer Money");
                System.out.println("[5]. Check Account Balance");
                System.out.println("[6]. Drop Existing Account");
                System.out.println("[7]. Exit \n");
                System.out.print("Enter an option to continue: ");
                int option=SCANNER.nextInt();
                SCANNER.nextLine();
                switch(option){
                    case 1: screen=OPEN_ACCOUNT;break;
                    case 2: screen=DEPOSIT_MONEY;break;
                    case 3: screen=WITHDRAW_MONEY;break;
                    case 4: screen=TRANSFER_MONEY;break;
                    case 5: screen=ACCOUNT_BALANCE;break;
                    case 6: screen=DELETE_ACCOUNT;break;
                    case 7: System.out.println(CLEAR); System.exit(0);
                    default: continue;
                }
                break;
///////////////////////////////////////////////////////////////////////////////////////////////////////                
                case OPEN_ACCOUNT:
                    String name;
                    String deposit;
                    boolean valid;
                    System.out.printf("ID : SDB-%05d \n",accountHoldername.size()+1);
                    //Name Validation
                    do{
                    valid=true;
                    System.out.print("Name : ");
                    name=SCANNER.nextLine().strip();

                    if(name.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Name can't be empty");
                        valid=false;
                        continue;
                        
                    }

                    for(int i=0;i<name.length();i++){
                        if(!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))){
                            System.out.printf(ERROR_MSG+"\n","Invalid name");
                            valid=false;
                            break;
                        }
                    }
                }while(!valid);

                //Initial Deposit Validation
                do{
                    valid=true;
                    System.out.print("Initial Deposit : ");
                    deposit=SCANNER.nextLine();

                    if(deposit.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Deposit can't be empty");
                        valid=false;
                        continue;
                        
                    }else if((checkLetter(deposit))){
                        System.out.printf(ERROR_MSG+"\n","Invalid Value");
                        valid=false;
                        continue;

                    }else if(Integer.valueOf(deposit)<5000){
                        System.out.printf(ERROR_MSG+"\n","Please deposit at least Rs.5,000.00");
                        valid=false;
                        continue;
                    }

                }while(!valid);

                accountNumber.add("SBD-"+String.format("%05d",accountBalance.size()+1));
                accountHoldername.add(name);
                accountBalance.add(deposit);

                System.out.printf(SUCCESS_MSG,String.format("Account SDB-%05d for %s has been created sucessfully.\n",accountHoldername.size(),name));
                System.out.print("Do you want to add new student (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                screen = DASHBOARD;

                break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //Deposit money
                
                case DEPOSIT_MONEY:
                String accountNum;
                int index=0;
                String newDeposit="";
                valid=true;
                case1:
                do{
                    System.out.print("Enter your Account No : ");
                    accountNum=SCANNER.nextLine().strip();
                do{ 
                    valid=false;

                    if(accountNum.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Account Number can't be empty");
                        break;
                        
                    }else if(!accountNum.startsWith("SBD-") || !(accountNum.length()==9 && checkNum(accountNum))){
                        System.out.printf(ERROR_MSG+"\n","Invalid format");
                        break;
                    }else if(!accountNumber.contains(accountNum)){
                        System.out.printf(ERROR_MSG+"\n","Not found");
                        break;
                    }else{
                        valid=true;
                    }

                }while(!valid);

                if(valid==false){
                System.out.print("Do you want to try again (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                screen=DEPOSIT_MONEY;
                break;}
                else {screen = DASHBOARD;
                break;}
                }

                String balance;
                index=accountNumber.indexOf(accountNum);
                balance=accountBalance.get(index);
                System.out.printf("Current account balance :Rs.%,.2f \n",Double.valueOf(balance));

                do{
                    valid=true;
                    System.out.print("Deposit amount : ");
                    newDeposit=SCANNER.nextLine();

                    if(newDeposit.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Deposit can't be empty");
                        valid=false;
                        continue;
                        
                    }else if((checkLetter(newDeposit))){
                        System.out.printf(ERROR_MSG+"\n","Invalid Value");
                        valid=false;
                        continue;

                    }else if(Integer.valueOf(newDeposit)<500){
                        System.out.printf(ERROR_MSG+"\n","Please deposit at least Rs.500.00");
                        valid=false;
                        continue;
                    }

                }while(!valid);
            
                String total=Integer.valueOf(accountBalance.get(index))+Integer.valueOf(newDeposit)+"";
                accountBalance.set(index, total);
                System.out.printf("New balance : Rs.%,.2f \n",Double.valueOf(total));
                
                System.out.print("Do you want to continue (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                screen = DASHBOARD;


                }while(!valid);
                    
            break;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



            //Don't change
            default:
                  System.exit(0);
            } 
        }while(true);

        
    }
    public static boolean checkDigit(String deposit){
            for(int i=0;i<deposit.length();i++){
                if(Character.isDigit(deposit.charAt(i))){
                    return true;
                } 
            }return false;
        
        }

    public static boolean checkLetter(String deposit){
            for(int i=0;i<deposit.length();i++){
                if(Character.isLetter(deposit.charAt(i))){
                    return true;
                } 
            }return false;
        
        }
     public static boolean checkNum(String deposit){
            int count=0;
            for(int i=4;i<deposit.length();i++){
                if(Character.isDigit(deposit.charAt(i))){
                    count++;
                    if(count==5)return true;
                } 
            }return false;
        
        }
    // public static boolean checkAccount(String account,Vector<String> accountNumber){
    //     for(int i=0;i<accountNumber.size();i++){
    //         if(accountNumber.get(i).equals(account)) return true;

    //     }return false;
    // }
        


}