package tr.com.myasir.dal;
import tr.com.myasir.complex.types.StokContractComplex;
import tr.com.myasir.complex.types.StokContractTotalComplex;
import tr.com.myasir.contract.StokContract;
import tr.com.myasir.core.ObjectHelper;
import tr.com.myasir.interfaces.DALInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StokDAL extends ObjectHelper implements DALInterfaces<StokContract> {

    @Override
    public void Insert(StokContract entity) {
        Connection connection=getConnection();

        try {
            Statement statement=connection.createStatement();
            statement.executeUpdate("insert into Stok (urunId,personelId,tarih,adet) values ("+entity.getUrunId()+","+entity.getPersonelId()+",'"+entity.getTarih()+"',"+entity.getAdet()+") ");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<StokContractComplex> GetAllStok(){
        List<StokContractComplex> datacontract =new ArrayList<StokContractComplex>();
        Connection connection=getConnection();
        StokContractComplex contract;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select stok.id, adisoyadi,adi,adet,stok.tarih from stok left join urunler on stok.urunid=urunler.id left join personel2 on stok.personelid=personel2.id order by stok.id");
            while (resultSet.next()){
                contract= new StokContractComplex();
                contract.setId(resultSet.getInt("Id"));
                contract.setPersonelAdi(resultSet.getString("adisoyadi"));
                contract.setUrunadi(resultSet.getString("adi"));
                contract.setAdet(resultSet.getInt("adet"));
                contract.setTarih(resultSet.getString("tarih"));

                datacontract.add(contract);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datacontract;
    }

    public List<StokContractTotalComplex> GetTotalStok(){
        List<StokContractTotalComplex> datacontract =new ArrayList<StokContractTotalComplex>();
        Connection connection=getConnection();
        StokContractTotalComplex contract;

        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select urunler.adi,sum(adet) as toplam from stok left joın urunler on stok.urunid=urunler.id left joın  personel2 on stok.personelid=personel2.id group by urunler.id,urunler.adı");
            while (resultSet.next()) {
                contract = new StokContractTotalComplex();
                contract.setUrunadi(resultSet.getString("Adi"));
                contract.setToplam(resultSet.getInt("Toplam"));
                datacontract.add(contract);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datacontract;

    }

      @Override
    public List<StokContract> GetAll() {
        return null;
    }

    @Override
    public void Delete(String entity) {

    }

    @Override
    public void Update(StokContract entity) {

    }

    @Override
    public StokContract GetById(int id) {
        return null;
    }
}