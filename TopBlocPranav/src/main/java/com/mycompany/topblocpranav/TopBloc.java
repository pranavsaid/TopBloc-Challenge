/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.topblocpranav;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
/**
 *
 * @author pranavdeenumsetti
 */
public class TopBloc {

    private static final String First_FILE_NAME = "Data1.xlsx";
    private static final String Second_FILE_NAME = "Data2.xlsx";

    public static void main(String[] args) {

        // Creating JSONObject to build JSON data
         JSONObject response = new JSONObject();
         //Lists help in identifying index of specific label
         ArrayList<String> list1=new ArrayList<String>();
         ArrayList<String> list2=new ArrayList<String>();
         //Creating JSONArray object for each lable to store array values 
         JSONArray numberSetOne = new JSONArray(); 
         JSONArray numberSetTwo = new JSONArray();
         JSONArray wordSetOne = new JSONArray();
         CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // TODO code application logic here
        try {
            //Fil IO to read Excell
            FileInputStream excelFile1 = new FileInputStream(new File(First_FILE_NAME));
            FileInputStream excelFile2 = new FileInputStream(new File(Second_FILE_NAME));
            //XSSF object to read excel file foramt
            Workbook workbook1 = new XSSFWorkbook(excelFile1);
            Workbook workbook2 = new XSSFWorkbook(excelFile2);
            //importing datafrom workbook
            Sheet datatypeSheet1 = workbook1.getSheetAt(0);
            Sheet datatypeSheet2 = workbook2.getSheetAt(0);
            //iterator to parse the data
            Iterator<Row> iterator1 = datatypeSheet1.iterator();
            Iterator<Row> iterator2 = datatypeSheet2.iterator();
            int i = 0;
            while (iterator1.hasNext() && iterator2.hasNext()) {
                //reading row by row using row object
                Row currentRow_Data1 = iterator1.next();
                Row currentRow_Data2 = iterator2.next();
                //we skip header which contains index labels
                if (i == 0) {
                    /* We need to identify the indexes of numberSetOne,
                    numberSetTwo and wordSetOne coloumns in two data sheets*/
                    Iterator<Cell> cellIterator1 = currentRow_Data1.iterator();
                    Iterator<Cell> cellIterator2 = currentRow_Data2.iterator();  
                    /*Adding the labels to a list to identify the index of 
                      particular label*/
                    while (cellIterator1.hasNext() && cellIterator2.hasNext())
                    {
                        Cell currentCell_Data1 = cellIterator1.next();
                        Cell currentCell_Data2 = cellIterator2.next();
                        list1.add(currentCell_Data1.getStringCellValue());
                        list2.add(currentCell_Data2.getStringCellValue());
                    }
                    i++;
                    continue;//Skippping First row with index names
                }

                 /* We retrieve the value of particular coloumn using the index
                  that we stored earlier */
                 numberSetOne.add((int)(currentRow_Data1.getCell(list1.indexOf("numberSetOne")).getNumericCellValue() * currentRow_Data2.getCell(list2.indexOf("numberSetOne")).getNumericCellValue()));
                 numberSetTwo.add((int)(currentRow_Data1.getCell(list1.indexOf("numberSetTwo")).getNumericCellValue() / currentRow_Data2.getCell(list2.indexOf("numberSetTwo")).getNumericCellValue()));
                 wordSetOne.add((currentRow_Data1.getCell(list1.indexOf("wordSetOne")).getStringCellValue() + " " +currentRow_Data2.getCell(list2.indexOf("wordSetOne")).getStringCellValue()));
                 // Added all data to the JSON array Object    
            }
          
            response.put("id", "pdeenumsetti@hawk.iit.edu");
            response.put("numberSetOne", numberSetOne);
            response.put("numberSetTwo", numberSetTwo);
            response.put("wordSetOne", wordSetOne);
            HttpPost request = new HttpPost("http://34.239.125.159:5000/challenge");
            StringEntity params = new StringEntity(response.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse listentoserver = httpClient.execute(request);
            
            System.out.print(listentoserver);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 

    }

    
}
