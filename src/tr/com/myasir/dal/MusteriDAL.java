package tr.com.myasir.dal;

import oracle.sql.DATE;
import tr.com.myasir.contract.MusteriContract;
import tr.com.myasir.core.ObjectHelper;
import tr.com.myasir.interfaces.DALInterfaces;


import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MusteriDAL extends ObjectHelper implements DALInterfaces<MusteriContract> {
    @Override
    public void Insert(MusteriContract entity) {

            Connection connection= getConnection();

        try {

            Statement statement=connection.createStatement();
            statement.executeUpdate("insert into musteri (adi,telefon,adres) values ('"+entity.getAdiSoyadi()+"','"+entity.getTelefon()+"','"+entity.getAdres()+"') ");

            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<MusteriContract> GetAll() {
        List<MusteriContract> datacontract =new ArrayList<MusteriContract>();
        Connection connection=getConnection();
        MusteriContract contract;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Musteri");
            while (resultSet.next()){
                contract= new MusteriContract();
                contract.setId(resultSet.getInt("Id"));
                contract.setAdiSoyadi(resultSet.getString("adi"));
                contract.setAdres(resultSet.getString("adres"));
               // contract.setSehirId(resultSet.getInt("sehirId"));
                contract.setTelefon(resultSet.getString("telefon"));

                datacontract.add(contract);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return datacontract;

    }

    @Override
    public void Delete(String entity) {

    }

    @Override
    public void Update(MusteriContract entity) {

    }

    @Override
    public MusteriContract GetById(int id) {
        return null;
    }

}
