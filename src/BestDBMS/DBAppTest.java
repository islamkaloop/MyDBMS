package BestDBMS;

import java.util.Hashtable;


public class DBAppTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] a){
		DBApp db =new DBApp();
		db.createMeta();
		db.init();
		String strTableName = "Student";
		@SuppressWarnings("rawtypes")
		Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.Double");
		db.createTable( strTableName, "id", htblColNameType );
		strTableName = "company";
		htblColNameType.clear();
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("address", "java.lang.String");
		htblColNameType.put("phone", "java.lang.String");
		db.createTable( strTableName, "id", htblColNameType );
		strTableName = "Student";
		Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>( );
		htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.put("gpa", new Integer( 5 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		db.insertIntoTable( "balabezooo" , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("gpa", new Double( 0.95  ) );
		db.deleteFromTable(strTableName, htblColNameValue);
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 453455 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("name", new String("Dalia Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.25 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("gpa", new Double( 1.25 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("name", new String("Dalia Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.0 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("phone", new String("55" ) );
		htblColNameValue.put("address", new String( "445" ) );
		db.insertIntoTable( "company" , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 23498 ));
		htblColNameValue.put("name", new String("John Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.5 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 78452 ));
		htblColNameValue.put("name", new String("Zaky Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.88 ) );
		db.insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("gpa", new Double( 1.0 ) );
		db.updateTable(strTableName, "78452", htblColNameValue);
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Zaky Noor" ) );
		db.updateTable(strTableName, "2343432", htblColNameValue);
		htblColNameValue.clear( );
		htblColNameValue.put("gpa", new Double( 1.0 ) );
		db.deleteFromTable(strTableName, htblColNameValue);
	}
	
}
