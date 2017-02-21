package us.conqr.simpletodo;


/**
 * Created by abhaygupta on 2/19/17.
 */

public class ToDoItem {
    //private variables
    Integer _id;
    String _item;

    // Empty constructor
    public ToDoItem() {
    }

    // constructor
    public ToDoItem(Integer id, String item) {
        this._id = id;
        this._item = item;
    }

    // constructor
    public ToDoItem(String item) {
        this._item = item;
    }

    // getting ID
    public Integer getID() {
        return this._id;
    }

    // setting id
    public void setID(Integer id) {
        this._id = id;
    }

    // getting item
    public String getItem() {
        return this._item;
    }

    // setting name
    public void setItem(String item) {
        this._item = item;
    }

    @Override
    public String toString() {
        return this._item;
    }
}
