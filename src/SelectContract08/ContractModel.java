package SelectContract08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.lang.*;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Home
 */
class ContractModel {
    
    private ArrayList<Contract> theContracts;
    private ArrayList<Contract> theContractsAll;
    private SortedSet<String> originCityList;
    private int contractCounter;
    private URL optionsFilepath;
    private String contractsFilepath;
    private DataSource ds = null;
    private Connection con = null;
    private CallableStatement cstmt = null;
    private ResultSet rs = null;
    
    
    public ContractModel() throws SQLException {
        contractCounter = 0;
        theContracts = new ArrayList<>();
        originCityList = new TreeSet<>();
        try {
            ds = MyDataSourceFactory.getOracleDataSource();
            con = ds.getConnection();
            cstmt = con.prepareCall("BEGIN abishop.PKGREC.CONTRACT_LIST(?); END;");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            rs = ((OracleCallableStatement) cstmt).getCursor(1);
            while (rs.next()) {
                String contractID = rs.getString("CONTRACTID");
                String origCity = rs.getString("ORIGCITY");
                String destCity = rs.getString("DESTCITY");
                String orderItem = rs.getString("ORDERITEM");
                Contract a = new Contract(contractID, origCity, destCity, orderItem);
                theContracts.add(a);
                originCityList.add(origCity);
            } 
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//        contractCounter = 0;
//        theContracts = new ArrayList<>();
//        originCityList = new TreeSet<>();
//        try {
//            optionsFilepath = ContractModel.class.getResource("options.txt");
//            File f = new File(optionsFilepath.getFile());
//            FileReader fileReader1 = new FileReader(f);
//            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
//            String path = bufferedReader1.readLine();
//            String[] filepath = path.split("=");
//            contractsFilepath = filepath[1];
//        } catch (FileNotFoundException ex) {
//            System.out.println("File Not Found");
//            System.exit(0);
//        }
//        try {
//            FileReader fileReader = new FileReader(contractsFilepath);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line;
//            while((line = bufferedReader.readLine()) != null) {
//                
//                String[] tokens = line.split(",",4);
//                String contractID = tokens[0];
//                String originCity = tokens[1];
//                String destCity = tokens[2];
//                String orderItem = tokens[3];
//                Contract a = new Contract(contractID, originCity, destCity, orderItem);
//                theContracts.add(a);
//                originCityList.add(originCity);
//            }
//        theContractsAll = new ArrayList<>(theContracts);
//        originCityList.add("All");
//        } catch (IOException e) {
//            System.err.println("Caught IOException");
//        }
    
    boolean foundContracts() {
        return !theContracts.isEmpty();
    }
    
    public Contract getTheContract() {
        Contract contract = theContracts.get(contractCounter);
        return contract;
    }
    
    public void setFilepath(String location) {
        this.contractsFilepath = location;
    }
    
    public int getContractCount() {
        return theContracts.size();
    }
    
    public int getCurrentContractNum() {
        return contractCounter;
    }
    
    public void nextContract() {
        if (getContractCount() > getCurrentContractNum()) {
            contractCounter++;
        }
    }
    
    public void prevContract() {
        if (contractCounter > 0) {
            contractCounter--;
        }
    }
    
    public String[] getOriginCityList() {
        String[] a;
        a = originCityList.toArray(new String[originCityList.size()]);
        return a;
    }
    
    public void updateContractList(String city) {
        theContracts = new ArrayList<>(theContractsAll);
        if (city != "All") {
            theContracts.removeIf(s -> !s.contains(city));
        }
        contractCounter = 0;
    }
    
    
}
