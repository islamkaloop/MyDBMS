package BestDBMS;



public class SQLTerm {
	String _strTableName ;
	String _strColumnName ;
	String _strOperator ;
	Object _objValue;
	
	
	public SQLTerm(String tableName, String columnname , String operator , Object obj) {
		this._strTableName = tableName;
		this._strColumnName = columnname;
		this._strOperator = operator;
		this._objValue = obj;
	}
	
public SQLTerm() {
		
	}

public static void main(String[] args) {
	SQLTerm[] a = new SQLTerm[1];
	a[0] = new SQLTerm();
	a[0]._strColumnName = "Student";
	a[0]._strColumnName = "name";
	a[0]._strOperator = "=";
	a[0]._objValue = new Double(1.5);
	
	System.out.print(a[0]._strColumnName);
}
}
