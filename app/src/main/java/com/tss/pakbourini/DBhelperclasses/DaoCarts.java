package com.tss.pakbourini.DBhelperclasses;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DaoCarts {
    //insert the carts item
    @Insert
    void InsertCarts(CartsTableModel cartsTableModel);
    // delete carts item single
    @Delete()
    void DeleteCart(CartsTableModel cartsTableModel);
    //delete all
    @Delete()
    void DeleteAllCarts(List< CartsTableModel > tableModelList);
//    ///update the the color and size and Quantity of carts items
//    @Query(" UPDATE cartsitem SET P_Size= :sP_Size ,quantity= :squnatity WHERE cart_Id= :sid")
//    void Update(int sid, String sColor, String sP_Size, int squnatity);
//    //update the the Quantity
//    @Query(" UPDATE cartsitem SET quantity= :squnatity WHERE cart_Id= :sid")
//    void Update_Quantity(int sid, int squnatity);
    /// get all data from query
    @Query("SELECT * FROM CartsItem")
    List< CartsTableModel > getAll();
}
