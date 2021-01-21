package tr.com.myasir.dal;


import tr.com.myasir.contract.YetkilerContract;
import tr.com.myasir.core.ObjectHelper;
import tr.com.myasir.interfaces.DALInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public  class YetkilerDAL extends ObjectHelper implements DALInterfaces<YetkilerContract> {

 public void Insert(YetkilerContract entity) {
  Connection connection= getConnection();
  try {
   Statement statement= connection.createStatement();
   statement.executeUpdate("insert into yetkiler (adi) values ('"+entity.getAdi()+"')");
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }

 public List<YetkilerContract> GetAll() {

  List<YetkilerContract> datacontract =new ArrayList<YetkilerContract>();
  Connection connection=getConnection();
  YetkilerContract contract;

  try {
   Statement statement = connection.createStatement();
   ResultSet resultSet = statement.executeQuery("select * from Yetkiler");

   while (resultSet.next()){

    contract= new YetkilerContract();
    contract.setId(resultSet.getInt("Id"));
    contract.setAdi(resultSet.getString("Adi"));


    datacontract.add(contract);

   }
  } catch (SQLException e) {
   e.printStackTrace();
  }


  return datacontract;
 }


 public void Delete(String entity) {

 }

 public void Update(YetkilerContract entity) {

 }

 @Override
 public YetkilerContract GetById(int id) {
  return null;
 }

}
