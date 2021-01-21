package tr.com.myasir.interfaces;

import tr.com.myasir.contract.KategoriContract;

import java.util.List;

public interface DALInterfaces<KategoriContract>
{
    void Insert (KategoriContract entity);
    List<KategoriContract> GetAll();
    void Delete(String entity);
    void Update(KategoriContract entity);
    KategoriContract GetById(int id);



}