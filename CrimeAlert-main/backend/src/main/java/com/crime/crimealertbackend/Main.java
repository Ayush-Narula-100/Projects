package com.crime.crimealertbackend;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import com.crime.crimealertbackend.models.*;
import com.crime.crimealertbackend.exceptionHandling.*;
import com.crime.crimealertbackend.services.*;
import java.util.*;

public class Main {
    /*public static void main(String[] args) {
        //AadharInformation.saveAadhaar("123456789012", "Rishu Raj", "7667250711","2005-14-01");
        //AadharInformation.saveAadhaar("123456789345", "Ronak Aggarwal", "7777777777","1947-10-02");
        Scanner scanner = new Scanner(System.in);
        String aadhaarNumber= scanner.nextLine();;
        try {
            for(char a:aadhaarNumber.toCharArray()) {
                if(a>96&&a<123) {
                    throw new WrongAadhaarException();
                }
            }
        }catch(WrongAadhaarException e){
            System.out.println(e.getMessage());
        }
        Aadhaar aadhar=AadharInformation.fetchAadhaar(aadhaarNumber);
        if(aadhar==null) {
            System.out.println("Invalid Aadhaar Number");
        }else {
            Handler obj = new Handler();
            User citizen=obj.userCitizen("rajRishu", aadhar.getName(),"73846hdk","Ludiana",aadhar.getAadhaarNumber());
            System.out.println("User Details");
            citizen.displayUser();
        }



        //System.out.println(aadhar);
    }*/
}
