package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.lang.reflect.*;
import javafx.scene.control.CheckBox;

/**
 * 
 * Record holds the specific fields of all of the four types of Records depending on the state.
 * The given collection will determine what type of record is created, Dance, Publication, Recording, or Album.
 * 
 *  - 	The fields of this class must be all of the fields desired to be shown in any of the columns of any table,
 * 		even if the fields are shared by multiple tables. 
 * 
 * 	-	The names of the fields must be identical to the fields of the query that is populating the table.
 * 
 * 	-	Each field must be used by a getter that returns the value of that field, 
 * 		whose name must also match the format getField_name() for any field field_name.
 * 
 * To change the columns of the Table, one must change the fields of this class according to the constraints above
 * as well as the colNameField mapping of column to field in the mapColumnNametoId() method in RecordTable.
 * 
 */
public class Record {
	
	private int id;
	//string fields based on schema field names of any type of Record
	private String name, barsperrepeat, type, artist_id, artist, devisor, repetitions, publication;
	//special cases
	private CheckBox ihave;
	private String tag;

	/**
	 * Creates a Record based on the given ResultSet pointer and the collection of field names
	 * that should be set for the particular Record. Fields ihave and tag are special cases that should not be set by Reflection.
	 * @param set - ResultSet containing the pointer of the row to be made into a record
	 * @param fieldNames - Collection of Strings that represent which fields should be set for the Record
	 * @throws SQLException
	 */
	public Record(ResultSet set, Collection<String> fieldNames) throws SQLException {
		try {
			Class<?> c = Class.forName(this.getClass().getCanonicalName());
			Field[] fieldList = c.getDeclaredFields();

			for(Field f : fieldList){
				if(f.getType() == Integer.TYPE){
					f.set(this, set.getInt(f.getName()));
				}
				else if (f.getType().isAssignableFrom(String.class)){
					if(fieldNames.contains(f.getName())){
						if(f.getName().equals("tag")) tag = (set.getString("tag") != null) ? set.getString("tag") : "";
						else f.set(this, set.getString(f.getName()));
					}
				}
				else{
					ihave = new CheckBox();
					if(set.getString("ihave").equals("1")) {
						ihave.setSelected(true);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS USED IN PROPERTY VALUE FACTORY IN RecordTable METHOD initializeTable()
	
	public int getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getArtist_id() {
		return artist_id;
	}

	public String getDevisor() {
		return devisor;
	}

	public String getRepetitions() {
		return repetitions;
	}

	public String getBarsperrepeat() {
		return barsperrepeat;
	}
	
	public CheckBox getIhave() {
		return ihave;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getPublication() {
		return publication;
	}
}
