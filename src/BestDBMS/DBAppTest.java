package BestDBMS;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;


public class DBAppTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] a){
		DBApp db =new DBApp();
		db.init();	
//		String strTableName = "Student";
//		db.createBitmapIndex(strTableName, "gpa");
		SQLTerm[] arrSQLTerms;
		arrSQLTerms = new SQLTerm[2];
		arrSQLTerms[0] = new SQLTerm();
		arrSQLTerms[0]._strTableName =  "Student";
		arrSQLTerms[0]._strColumnName=  "name";
		arrSQLTerms[0]._strOperator  =  "=";
		arrSQLTerms[0]._objValue     =  "John Noor"; 
		 arrSQLTerms[1] = new SQLTerm();
		arrSQLTerms[1]._strTableName =  "Student"; 
		arrSQLTerms[1]._strColumnName=  "gpa";
		arrSQLTerms[1]._strOperator  =  "="; 
		arrSQLTerms[1]._objValue     =  new Double( 1.5 );
		String[]strarrOperators = new String[1];
		strarrOperators[0] = "OR"; 
		Iterator resultSet = db.selectFromTable(arrSQLTerms, strarrOperators);
		System.out.println(resultSet.toString());
//		while (resultSet.hasNext()) {
//			Hashtable<String,Object> sd =(Hashtable<String, Object>) resultSet.next();
//			//System.out.println(sd.get(arg0));
//		}
//		String strTableName = "Student";
//		Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>( );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		db.deleteFromTable(strTableName, htblColNameValue);
//		String strTableName = "Student";
//		Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>( );
//		htblColNameValue.put("name", new String("Mohanad Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		db.updateTable(strTableName, "516", htblColNameValue);
//		db.insertIntoTable( strTableName , htblColNameValue );
//		String strTableName = "Company";
//		@SuppressWarnings("rawtypes")
//		Hashtable htblColNameType = new Hashtable( );
//		htblColNameType.put("id", "java.lang.Integer");
//		htblColNameType.put("name", "java.lang.String");
//		htblColNameType.put("Rate", "java.lang.Double");
//		db.createTable( strTableName, "id", htblColNameType );
//		db.createMeta();
//		String strTableName = "Student";
//		Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>( );
//		htblColNameValue.put("id", new Integer( 12534 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		db.createBitmapIndex("Student", "gpa");
//		db.createMeta();
//		db.init();
//		String strTableName = "Student";
//		@SuppressWarnings("rawtypes")
//		Hashtable htblColNameType = new Hashtable( );
//		htblColNameType.put("id", "java.lang.Integer");
//		htblColNameType.put("name", "java.lang.String");
//		htblColNameType.put("gpa", "java.lang.Double");
//		db.createTable( strTableName, "id", htblColNameType );
//		strTableName = "company";
//		htblColNameType.clear();
//		htblColNameType.put("id", "java.lang.Integer");
//		htblColNameType.put("address", "java.lang.String");
//		htblColNameType.put("phone", "java.lang.String");
//		db.createTable( strTableName, "id", htblColNameType );
//		strTableName = "Student";
//		Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>( );
//		htblColNameValue.put("id", new Integer( 2343288 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.put("gpa", new Integer( 5 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		db.insertIntoTable( "balabezooo" , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("gpa", new Double( 0.95  ) );
//		db.deleteFromTable(strTableName, htblColNameValue);
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 453455 ));
//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.95 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 5674567 ));
//		htblColNameValue.put("name", new String("Dalia Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.25 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 5674567 ));
//		htblColNameValue.put("gpa", new Double( 1.25 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 5674567 ));
//		htblColNameValue.put("name", new String("Dalia Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.0 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 5674567 ));
//		htblColNameValue.put("phone", new String("55" ) );
//		htblColNameValue.put("address", new String( "445" ) );
//		db.insertIntoTable( "company" , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 23498 ));
//		htblColNameValue.put("name", new String("John Noor" ) );
//		htblColNameValue.put("gpa", new Double( 1.5 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 7847752 ));
//		htblColNameValue.put("name", new String("Zaky Noor" ) );
//		htblColNameValue.put("gpa", new Double( 0.88 ) );
//		db.insertIntoTable( strTableName , htblColNameValue );
//		htblColNameValue.clear( );
//		htblColNameValue.put("gpa", new Double( 1.0 ) );
//		db.updateTable(strTableName, "78452", htblColNameValue);
//		htblColNameValue.clear( );
//		htblColNameValue.put("id", new Integer( 2343432 ));
//		htblColNameValue.put("name", new String("Zaky Noor" ) );
//		db.updateTable(strTableName, "2343432", htblColNameValue);
//		htblColNameValue.clear( );
//		htblColNameValue.put("gpa", new Double( 1.0 ) );
//		db.deleteFromTable(strTableName, htblColNameValue);
	}
	
}
