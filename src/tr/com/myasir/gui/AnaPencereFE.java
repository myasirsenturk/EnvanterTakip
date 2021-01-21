package tr.com.myasir.gui;

import com.toedter.calendar.JDateChooser;
import tr.com.myasir.complex.types.SatisContractComplex;
import tr.com.myasir.complex.types.StokContractComplex;
import tr.com.myasir.complex.types.StokContractTotalComplex;
import tr.com.myasir.dal.*;
import tr.com.myasir.interfaces.FeInterfaces;
import tr.com.myasir.contract.*;
import tr.com.myasir.utilities.MenulerCom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class AnaPencereFE extends JFrame implements FeInterfaces {
    public AnaPencereFE()
    {

        initPencere();


    }

    @Override
    public void initPencere() {

        JPanel panel=initPanel();
        JMenuBar bar= initBar();
        add(panel);
        setJMenuBar(bar);
        setTitle("ENVANTER TAKİP SİSTEMİ");
        setSize(1000,1000);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public JPanel initPanel() {
        JPanel panel=new JPanel(new BorderLayout());
        JTabbedPane pane=initTabs();
        panel.add(pane,BorderLayout.CENTER);
        return panel;
    }

    @Override
    public JMenuBar initBar() {
        JMenuBar bar= MenulerCom.initBar();

        return bar  ;
    }

    @Override
    public JTabbedPane initTabs() {

        JTabbedPane pane = new JTabbedPane();
        JPanel stokPanel= new JPanel(new BorderLayout());
        JPanel satisPanel = new JPanel(new BorderLayout());


        JPanel urunPanel = new JPanel(new BorderLayout());
        JPanel urunsolpanel= new JPanel(new BorderLayout());
        JPanel urunsolustPanel=new JPanel(new GridLayout(5,2 ));
        JPanel urunsolaltPanel= new JPanel();


        JPanel stoksolpanel= new JPanel(new BorderLayout());
        JPanel stoksolustPanel=new JPanel(new GridLayout(5,2 ));
        JPanel stoksolaltPanel= new JPanel();

        //Urun Paneli işlemleri
        urunsolpanel.setBorder(BorderFactory.createTitledBorder("Ürün İşlemleri"));
        Object [] urunKolonlar={"Id","Ürün Adı","Tarihi"};
        DefaultTableModel model1= new DefaultTableModel(urunKolonlar,0);
        JTable table1=new JTable(model1);
        JScrollPane uruntablepane=new JScrollPane(table1);

        for (UrunlerContract contract: new UrunlerDAL().GetAll()){

            model1.addRow(contract.getVeriler());
        }


        JButton urunYenileButon = new JButton("Yenile");
        urunsolustPanel.add(urunYenileButon);
        urunYenileButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int satir1=model1.getRowCount();
                for ( int i=0;i<satir1;i++){
                    model1.removeRow(0);
                }
                for (UrunlerContract contract: new UrunlerDAL().GetAll()){

                    model1.addRow(contract.getVeriler());
                }
            }
        });

        JButton urunSilButon = new JButton("Sil");
        urunsolustPanel.add(urunSilButon);
        urunSilButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int satir = table1.getSelectedRow();
                if (satir >=0){
                    String id = table1.getValueAt(satir,0).toString();
                    new UrunlerDAL().Delete(id);
                    model1.removeRow(satir);

                }
            }
        });

        JButton urunDuzenleButon = new JButton("Duzenle");
        urunsolustPanel.add(urunDuzenleButon);
        urunDuzenleButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int satir = table1.getSelectedRow();
                if (satir >=0){
                    String id = table1.getValueAt(satir,0).toString();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new UrunDuzenleFE(id);

                        }
                    });
                }
            }
        });
        //Stok Paneli İşlemleri

        stoksolpanel.setBorder(BorderFactory.createTitledBorder("Stok İşlemleri"));
        Object [] stokKolonlar={"Id","Ürün Adı","Personel Adı","Adeti","Tarihi","Toplam"};
        DefaultTableModel model= new DefaultTableModel(stokKolonlar,0);
        JTable table=new JTable(model);
        JScrollPane stoktablepane=new JScrollPane(table);

        for (StokContractComplex contract: new StokDAL().GetAllStok()){

            model.addRow(contract.getVeriler());
        }

        JLabel StokurunAdi= new JLabel("Ürün Adı",JLabel.RIGHT);
        stoksolustPanel.add(StokurunAdi);
        JComboBox StokadiBox= new JComboBox(new UrunlerDAL().GetAll().toArray());
        stoksolustPanel.add(StokadiBox);
        JLabel stokAdet= new JLabel("Adet",JLabel.RIGHT);
        stoksolustPanel.add(stokAdet);
        JTextField adetField= new JTextField(10);
        stoksolustPanel.add(adetField);
        JLabel stoktarihLabel= new JLabel("Stok Tarihi",JLabel.RIGHT);
        stoksolustPanel.add(stoktarihLabel);
        JDateChooser stokTarihi= new JDateChooser();
        stoksolustPanel.add(stokTarihi);

        JButton stokEkleButon= new JButton("Stok Ekle");
        stoksolustPanel.add(stokEkleButon);
        JButton stokYenileButon = new JButton("Yenile");
        stoksolustPanel.add(stokYenileButon);
        JButton ToplamButon = new JButton("Stok Toplam");
        stoksolustPanel.add(ToplamButon);

        stokYenileButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int satir1=model.getRowCount();
                for ( int i=0;i<satir1;i++){
                    model.removeRow(0);
                }

                for (StokContractComplex acontract: new StokDAL().GetAllStok()) {
                    model.addRow(acontract.getVeriler());
                }
                }
        });

        stokEkleButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StokContract contract= new StokContract();
                UrunlerContract uContract = (UrunlerContract) StokadiBox.getSelectedItem();
                PersonelContract pContract = (PersonelContract) LoginFE.emailBox.getSelectedItem();
                SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
                String date=format.format(stokTarihi.getDate());

                contract.setPersonelId(pContract.getId());
                contract.setUrunId(uContract.getId());
                contract.setTarih(date.toString());
                contract.setAdet(Integer.parseInt(adetField.getText()));

                new StokDAL().Insert(contract);
                JOptionPane.showMessageDialog(null,uContract.getAdi()+ "  adlı ürün eklenmiştir");
                int satir= model.getRowCount();

               for ( int i=0;i<satir;i++){
                    model.removeRow(0);
               }

               for (StokContractComplex acontract: new StokDAL().GetAllStok()) {
                    model.addRow(acontract.getVeriler());
               }
            }
        });
        ToplamButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int satir2=model.getRowCount();
                for (int i=0;i<satir2;i++){
                    model.removeRow(0);
                }
                for (StokContractTotalComplex total: new StokDAL().GetTotalStok()){
                    model.addRow(total.getVeriler());
                }

            }
        });

        //Satış Paneli İşlemleri

        JPanel satisSagPanel= new JPanel(new BorderLayout());
        JPanel satisSagUstPanel= new JPanel(new GridLayout(5,2));
        JPanel satisSagAltPanel= new JPanel(new BorderLayout());
        satisSagPanel.setBorder(BorderFactory.createTitledBorder("Satış İşlemleri"));

        Object [] satisKolonlar={"Id","Personel Adı","Müşteri Adı","Ürün Adı","Adeti","Tarihi"};
        DefaultTableModel satismodel= new DefaultTableModel(satisKolonlar,0);
        JTable satistable=new JTable(satismodel);
        JScrollPane satistablepane=new JScrollPane(satistable);
        JLabel musteriLabel= new JLabel("Müşteri Adı",JLabel.RIGHT);
        satisSagUstPanel.add(musteriLabel);
        JComboBox musteriadiBox= new JComboBox(new MusteriDAL().GetAll().toArray());
        satisSagUstPanel.add(musteriadiBox);
        JLabel satisurunAdi= new JLabel("Ürün Adı",JLabel.RIGHT);
        satisSagUstPanel.add(satisurunAdi);
        JComboBox satisadiBox= new JComboBox(new UrunlerDAL().GetAll().toArray());
        satisSagUstPanel.add(satisadiBox);
        JLabel satisAdet= new JLabel("Adet",JLabel.RIGHT);
        satisSagUstPanel.add(satisAdet);
        JTextField satisadetField= new JTextField(10);
        satisSagUstPanel.add(satisadetField);
        JLabel satistarihLabel= new JLabel("Satış Tarihi",JLabel.RIGHT);
        satisSagUstPanel.add(satistarihLabel);
        JDateChooser satisTarihi= new JDateChooser();
        satisSagUstPanel.add(satisTarihi);

        JButton satisEkleButon= new JButton("Satış Yap");
        satisSagUstPanel.add(satisEkleButon);
        satisEkleButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PersonelContract pcontract = (PersonelContract) LoginFE.emailBox.getSelectedItem();
                UrunlerContract ucontract = (UrunlerContract) satisadiBox.getSelectedItem();
                MusteriContract mcontract = (MusteriContract) musteriadiBox.getSelectedItem();
                SatisContract contract=new SatisContract();
                SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
                String date=format.format(satisTarihi.getDate());

                contract.setMusteriId(mcontract.getId());
                contract.setPersonelId(pcontract.getId());
                contract.setUrunId(ucontract.getId());
                contract.setAdet(Integer.parseInt(satisadetField.getText()));
                contract.setTarih(date);

                new SatisDAL().Insert(contract);

                StokContract stokContract=new StokContract();
                stokContract.setPersonelId(pcontract.getId());
                stokContract.setUrunId(ucontract.getId());
                stokContract.setAdet(-Integer.parseInt(satisadetField.getText()));
                stokContract.setTarih(date);

                new StokDAL().Insert(stokContract);
                JOptionPane.showMessageDialog(null,mcontract.getAdiSoyadi()+ " adlı müşteriye satış gerçekleşmiştir");

            }
        });

        JButton satisYenileButon = new JButton("Yenile");
        satisSagUstPanel.add(satisYenileButon);
        satisYenileButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int satir3=satismodel.getRowCount();
                for (int i=0;i<satir3;i++){
                    satismodel.removeRow(0);
                }
                for (SatisContractComplex contract: new SatisDAL().GetAllSatis()){
                    satismodel.addRow(contract.getVeriler());
                }

            }
        });

        urunPanel.add(urunsolpanel,BorderLayout.WEST);
        urunPanel.add(uruntablepane,BorderLayout.CENTER);

        urunsolpanel.add(urunsolustPanel,BorderLayout.NORTH);
        urunsolpanel.add(urunsolaltPanel,BorderLayout.SOUTH);




        stokPanel.add(stoksolpanel,BorderLayout.WEST);
        stokPanel.add(stoktablepane,BorderLayout.CENTER);

        satisPanel.add(satisSagPanel,BorderLayout.WEST);
        satisPanel.add(satistablepane,BorderLayout.CENTER);

        satisSagPanel.add(satisSagUstPanel,BorderLayout.NORTH);
        satisSagPanel.add(satisSagAltPanel,BorderLayout.SOUTH);

        stoksolpanel.add(stoksolustPanel,BorderLayout.NORTH);
        stoksolpanel.add(stoksolaltPanel,BorderLayout.SOUTH);

        ImageIcon icon3= new ImageIcon("ikon/Stok_ikon.png");
        pane.addTab("Urunler",icon3,urunPanel,"Does nothing");


        ImageIcon icon= new ImageIcon("ikon/Stok_ikon.png");
        pane.addTab("Stoklar",icon,stokPanel,"Does nothing");
        ImageIcon icon1= new ImageIcon("ikon/Stok_ikon.png");
        pane.addTab("Satışlar",icon1,satisPanel,"Does nothing");
        ImageIcon icon2= new ImageIcon("ikon/Stok_ikon.png");


        return pane;
    }
}
