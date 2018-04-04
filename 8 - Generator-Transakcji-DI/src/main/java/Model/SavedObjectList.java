package Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;




public class SavedObjectList {
    private List<SavedObject> savedObjects;


    public SavedObjectList(){
        savedObjects = new ArrayList<SavedObject>();
    }

    public void add(SavedObject savedObject){
        savedObjects.add(savedObject);
    }
}
