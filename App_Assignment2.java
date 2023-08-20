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
        final String WITHDRAW_MONEY="⏫ Withdraw Money";
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
        int deleteAccCount=0;

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
                    System.out.printf("ID : SDB-%05d \n",(accountHoldername.size()+1+deleteAccCount));
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

                    }else if(Double.valueOf(deposit)<5000){
                        System.out.printf(ERROR_MSG+"\n","Please deposit at least Rs.5,000.00");
                        valid=false;
                        continue;
                    }

                }while(!valid);

                accountNumber.add("SBD-"+String.format("%05d",(accountBalance.size()+1+deleteAccCount)));
                accountHoldername.add(name);
                accountBalance.add(deposit);

                System.out.printf(SUCCESS_MSG,String.format("Account SDB-%05d for %s has been created sucessfully.\n",(accountHoldername.size()+deleteAccCount),name));
                System.out.print("Do you want to add another (Y/n)? ");
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

                    }else if(Double.valueOf(newDeposit)<500){
                        System.out.printf(ERROR_MSG+"\n","Please deposit at least Rs.500.00");
                        valid=false;
                        continue;
                    }

                }while(!valid);
            
                String total=Double.valueOf(accountBalance.get(index))+Double.valueOf(newDeposit)+"";
                accountBalance.set(index, total);
                System.out.printf("New balance : Rs.%,.2f \n",Double.valueOf(total));
                
                System.out.print("Do you want to continue (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                screen = DASHBOARD;


                }while(!valid);
                    
            break;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //Withdraw

            case WITHDRAW_MONEY:
                index=0;
                String withdraw="";
                valid=true;
                
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
                String total;

                do{
                    valid=false;
                    System.out.print("Withdrawal amount : ");
                    withdraw=SCANNER.nextLine();

                    if(withdraw.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Withdrawal can't be empty");
                        // valid=false;
                        // continue;
                        break;
                        
                    }else if((checkLetter(withdraw))){
                        System.out.printf(ERROR_MSG+"\n","Invalid Value");
                        // valid=false;
                        // continue;
                        break;

                    }else if(Integer.valueOf(withdraw)<100){
                        System.out.printf(ERROR_MSG+"\n","Minimum withdrawal amount is Rs.100.00");
                        // valid=false;
                        // continue;
                        break;
                    }else if((Integer.valueOf(accountBalance.get(index))-Integer.valueOf(withdraw))<500){
                        System.out.printf(ERROR_MSG+"\n","Insufficient account balance");
                        break;

                    }else{
                        valid=true;
                    }

                }while(!valid);

                
                if(valid==true){
                total=Double.valueOf(accountBalance.get(index))-Double.valueOf(withdraw)+"";
                accountBalance.set(index, total);
                System.out.printf("New balance : Rs.%,.2f \n",Double.valueOf(total));
                }
               
                System.out.print("Do you want to continue (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    screen=WITHDRAW_MONEY;
                    break;
                }else{
                    screen = DASHBOARD;
                    break;
                }
                }while(!valid);
                    
            break;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
         case TRANSFER_MONEY:
                String fromAccountNum;
                String toAccountNum;
                int fromIndex=0;
                int toIndex=0;

                String transferAmount="";
                valid=true;
                
                do{
                    System.out.print("Enter From Account No : ");
                    fromAccountNum=SCANNER.nextLine().strip();
                do{ 
                    valid=false;

                    if(fromAccountNum.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Account Number can't be empty");
                        break;
                        
                    }else if(!fromAccountNum.startsWith("SBD-") || !(fromAccountNum.length()==9 && checkNum(fromAccountNum))){
                        System.out.printf(ERROR_MSG+"\n","Invalid format");
                        break;
                    }else if(!accountNumber.contains(fromAccountNum)){
                        System.out.printf(ERROR_MSG+"\n","Not found");
                        break;
                    }else{
                        valid=true;
                    }

                }while(!valid);

                if(valid==false){
                System.out.print("Do you want to try again (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                screen=TRANSFER_MONEY;
                break;}
                else {screen = DASHBOARD;
                break;}
                }


                System.out.print("Enter To Account No : ");
                toAccountNum=SCANNER.nextLine().strip();

                ///////////////////////////////////////////////////////////////////////////////////////////
                do{ 
                    valid=false;

                    if(toAccountNum.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Account Number can't be empty");
                        break;
                        
                    }else if(!toAccountNum.startsWith("SBD-") || !(toAccountNum.length()==9 && checkNum(toAccountNum))){
                        System.out.printf(ERROR_MSG+"\n","Invalid format");
                        break;
                    }else if(!accountNumber.contains(toAccountNum)){
                        System.out.printf(ERROR_MSG+"\n","Not found");
                        break;
                    }else{
                        valid=true;
                    }

                }while(!valid);

                if(valid==false){
                System.out.print("Do you want to try again (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                screen=TRANSFER_MONEY;
                break;}
                else {
                screen = DASHBOARD;
                break;}
                }
                //////////////////////////////////////////////////////////////////////////////////////////

                String balance;
                fromIndex=accountNumber.indexOf(fromAccountNum);
                toIndex=accountNumber.indexOf(toAccountNum);
                balance=accountBalance.get(fromIndex);
                System.out.printf("From account name : %s \n",accountHoldername.get(fromIndex));
                System.out.printf("Current account balance :Rs.%,.2f \n",Double.valueOf(balance));

                 do{
                    valid=false;
                    System.out.print("Transfer amount : ");
                    transferAmount=SCANNER.nextLine();

                    if(transferAmount.isBlank()){
                        System.out.printf(ERROR_MSG+"\n","Transfer amount can't be empty");
                        // valid=false;
                        // continue;
                        break;
                        
                    }else if((checkLetter(transferAmount))){
                        System.out.printf(ERROR_MSG+"\n","Invalid Value");
                        // valid=false;
                        // continue;
                        break;

                    }else if(Double.valueOf(transferAmount)<100){
                        System.out.printf(ERROR_MSG+"\n","Minimum withdrawal amount is Rs.100.00");
                        // valid=false;
                        // continue;
                        break;
                    }else if((Double.valueOf(accountBalance.get(fromIndex))-Double.valueOf(transferAmount))<500){
                        System.out.printf(ERROR_MSG+"\n","Insufficient account balance");
                        break;

                    }else{
                        valid=true;
                    }

                }while(!valid);

                if(valid==true){
                     String totalFromAccount=(Double.valueOf(accountBalance.get(fromIndex))-(1.02*Double.valueOf(transferAmount)))+"";
                     accountBalance.set(fromIndex, totalFromAccount);
                     System.out.printf("New from account balance : Rs.%,.2f \n",Double.valueOf(totalFromAccount));

                     String totalTOAccount=(Double.valueOf(accountBalance.get(toIndex))+Double.valueOf(transferAmount))+"";
                     accountBalance.set(toIndex, totalTOAccount);
                     System.out.printf("New to account balance : Rs.%,.2f \n",Double.valueOf(totalTOAccount));

                }

                
                System.out.print("Do you want to continue (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                    screen=TRANSFER_MONEY;
                    break;
                }else{
                    screen = DASHBOARD;
                    break;
                }


                }while(!valid);
                    
            break;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            case ACCOUNT_BALANCE:
            
                index=0;
                valid=true;
                
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
                screen=ACCOUNT_BALANCE;
                break;}
                else {screen = DASHBOARD;
                break;}
                }

                String balance;
                index=accountNumber.indexOf(accountNum);
                balance=accountBalance.get(index);
                double availableWithdraw=Double.valueOf(balance)-500.00;
                String owner=accountHoldername.get(index);
                System.out.printf("Name : %s \n",owner);
                System.out.printf("Current account balance :Rs.%,.2f \n",Double.valueOf(balance));
                System.out.printf("Available balance to withdraw :Rs.%,.2f \n",availableWithdraw);
                
                
                System.out.print("Do you want to continue (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                  screen=ACCOUNT_BALANCE;
                  break;
                }else{
                  screen = DASHBOARD;
                  break;
                }

                }while(!valid);
                    
            break;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            case DELETE_ACCOUNT:
                index=0;
                valid=true;
                
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
                screen=ACCOUNT_BALANCE;
                break;}
                else {screen = DASHBOARD;
                break;}
                }

                String balance;
                index=accountNumber.indexOf(accountNum);
                balance=accountBalance.get(index);
                String owner=accountHoldername.get(index);
                System.out.printf("Name : %s \n",owner);
                System.out.printf("Current account balance :Rs.%,.2f \n",Double.valueOf(balance));
                
                
                System.out.print("Are you sure to delete (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                  System.out.printf(SUCCESS_MSG,String.format("Account %s of %s has been deleted sucessfully.\n",accountNumber.get(index),accountHoldername.get(index)));
                  accountHoldername.remove(index);
                  accountNumber.remove(index);
                  accountBalance.remove(index);
                  deleteAccCount++;
                  
                }else{
                  screen = DASHBOARD;
                  break;
                }

                System.out.print("Do you want to continue (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
                  screen=ACCOUNT_BALANCE;
                  break;
                }else{
                  screen = DASHBOARD;
                  break;
                }


                }while(!valid);
                    
            break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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