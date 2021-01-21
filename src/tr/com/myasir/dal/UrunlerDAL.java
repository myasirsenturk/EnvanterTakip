package tr.com.myasir.dal;

import tr.com.myasir.contract.UrunlerContract;
import tr.com.myasir.core.ObjectHelper;
import tr.com.myasir.interfaces.DALInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UrunlerDAL extends ObjectHelper implements DALInterfaces<UrunlerContract> {

    public void Insert(UrunlerContract entity) {
        Connection connection=getConnection();
        try {
            Statement statement=connection.createStatement();
            statement.executeUpdate("insert into Urunler(Adi,KategoriId,Tarih,Fiyat) values ('"+entity.getAdi()+"',"+entity.getKategoriId()+",'"+entity.getTarih()+"',"+entity.getFiyat()+")");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UrunlerContract> GetAll() {

        List<UrunlerContract> datacontract =new ArrayList<UrunlerContract>();
        Connection connection=getConnection();
        UrunlerContract contract;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from urunler ");
            while (resultSet.next()){
                contract= new UrunlerContract();
                contract.setId(resultSet.getInt("id"));
                contract.setAdi(resultSet.getString("adi"));
                contract.setKategoriId(resultSet.getInt("kategoriId"));
                contract.setTarih(resultSet.getString("tarih"));
                contract.setFiyat(resultSet.getInt("fiyat"));

                datacontract.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datacontract;
    }

    @Override
    public void Delete(String id) {
        Connection connection=getConnection();
        try {
            Statement statement=connection.createStatement();
            statement.executeQuery("delete from Urunler where id="+id);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update(UrunlerContract entity) {
        Connection connection=getConnection();
        try {
            Statement statement=connection.createStatement();
            statement.executeUpdate("Update Urunler Set Adi = '"+ entity.getAdi()+"' ,KategoriId="+entity.getKategoriId()+",Tarih='"+entity.getTarih()+"',Fiyat="+entity.getFiyat()+" where id ="+entity.getId());
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UrunlerContract GetById(int id) {
        Connection connection=getConnection();
        UrunlerContract contract;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from urunler where id="+id);
            while (resultSet.next()){
                contract= new UrunlerContract();
                contract.setId(resultSet.getInt("id"));
                contract.setAdi(resultSet.getString("adi"));
                contract.setKategoriId(resultSet.getInt("kategoriId"));
                contract.setTarih(resultSet.getString("tarih"));
                contract.setFiyat(resultSet.getInt("fiyat"));
                return contract;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
