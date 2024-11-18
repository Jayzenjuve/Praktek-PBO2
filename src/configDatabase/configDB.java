package configDatabase;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class configDB {

    private String jdbcUrl = "jdbc:mysql://localhost:3306/2210010125_pbo2";
    private String username = "root";
    private String password = "";
    
    private DefaultTableModel Modelnya;
    private TableColumn Kolomnya;
    
    public configDB(){
        
        
        
    }
    
    public Connection getKoneksi() throws SQLException{
        try{
            Driver mysqldriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            System.out.println("Koneksi DB Berhasil");
        } catch (Exception e){
            System.out.println(e.toString());
        }   
        
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
    
    public boolean duplikatKey(String NamaTabel, String PrimaryKey, String isiData){
        boolean hasil = false;

        int baris = 0;
        
        try{
            
            String SQL = "SELECT * FROM "+NamaTabel+" WHERE "+PrimaryKey+" ='"+isiData+"'";
            Statement perintah = getKoneksi().createStatement();
            ResultSet hasilData = perintah.executeQuery(SQL);
            
            while (hasilData.next()) {                
                baris++;
            }
            if(baris == 0){
                hasil = false;
            }else{
                hasil = true;
            }
            
        } catch(Exception e){
            System.out.println(e.toString());
        }
        
        return hasil;
    }
    
    public void SimpanDVD(String Kode, String Judul, String Genre, String stok, String tahun){
        try {
            if (duplikatKey("DVD", "KodeDVD", Kode)){
                JOptionPane.showMessageDialog(null, "Kode sudah terdaftar");
            } else{
                String SQL = "INSERT INTO DVD (KodeDvd, Judul, genre, stok, tahun) VALUE('"+Kode+"','"+Judul+"','"+Genre+"','"+stok+"','"+tahun+"')";
                Statement perintah = getKoneksi().createStatement();
                
                perintah.executeUpdate(SQL);
                perintah.close();
                getKoneksi().close();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }   
    }
    
    public String getFieldTabel(String[] FieldTabelnya){
        String hasilnya = "";
        int deteksiIndexAkhir = FieldTabelnya.length-1;
        try {
            for (int i = 0; i < FieldTabelnya.length; i++){
                
                if(i == deteksiIndexAkhir){
                    hasilnya = hasilnya+FieldTabelnya[i];
                }else{
                    hasilnya = hasilnya+FieldTabelnya[i] + ", ";
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "("+hasilnya+")";
    }
    
    public String getIsiTabel(String[] IsiTabelnya){
        String hasilnya = "";
        int DeteksiIndex = IsiTabelnya.length-1;
        try {
            for (int i = 0; i < IsiTabelnya.length; i++){
                if (i == DeteksiIndex){
                    hasilnya = hasilnya+"'"+IsiTabelnya[i]+"'";
                }else{
                    hasilnya = hasilnya+"'"+IsiTabelnya[i]+"', ";
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "("+hasilnya+")";
    }
    
    public String getFieldValueEdit(String[] Field, String[] value){
        String hasil = "";
        int deteksi = Field.length-1;
        try {
            for (int i = 0; i < Field.length; i++) {
                if (i == deteksi){
                    hasil = hasil +Field[i]+" ='"+value[i]+"'";
                }else{
                   hasil = hasil +Field[i]+" ='"+value[i]+"',";  
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return hasil;
    }

    
    public void SimpanDinamis(String NamaTabel, String[] Fieldnya, String[] Isinya){
        try {
            String SQLSave = "INSERT INTO "+NamaTabel+" "+getFieldTabel(Fieldnya)+" VALUES "+getIsiTabel(Isinya);
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQLSave);
            perintah.close();
            JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void UbahDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)+" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUbah);
           perintah.close();
           getKoneksi().close();
           JOptionPane.showMessageDialog(null,"Data Berhasil Diedit");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public void HapusDinamis(String NamaTabel, String PK, String isi){
        try {
            String SQL="DELETE FROM "+NamaTabel+" WHERE "+PK+"='"+isi+"'";
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQL);
            perintah.close();
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    
}