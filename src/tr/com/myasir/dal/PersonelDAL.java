package tr.com.myasir.dal;

import tr.com.myasir.contract.KategoriContract;
import tr.com.myasir.contract.PersonelContract;
import tr.com.myasir.core.ObjectHelper;
import tr.com.myasir.interfaces.DALInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonelDAL extends ObjectHelper implements DALInterfaces<PersonelContract> {
    @Override
    public void Insert(PersonelContract entity) {
        Connection connection=getConnection();
        try {
            Statement statement=connection.createStatement();
            statement.executeUpdate("insert into  PERSONEL2 ( adiSoyadi,email) values ('"+entity.getAdiSoyadi()+"','"+entity.getEmail()+"')");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PersonelContract> GetAll() {
        List<PersonelContract> datacontract =new ArrayList<PersonelContract>();
        Connection connection=getConnection();
        PersonelContract contract;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from PERSONEL2");
            while (resultSet.next()){
                contract= new PersonelContract();
                contract.setId(resultSet.getInt("Id"));
                contract.setAdiSoyadi(resultSet.getString("Adisoyadi"));
                contract.setEmail(resultSet.getString("Email"));

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
    public void Update(PersonelContract entity) {

    }

    @Override
    public PersonelContract GetById(int id) {
        return null;
    }
}


