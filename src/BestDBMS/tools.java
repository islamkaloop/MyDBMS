package BestDBMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class tools {
	public static int combareTo(Object first,Object second){
		int i=0;
		if (first instanceof String) {
			i=((String) first).compareTo((String) second);
		}
		else if (first instanceof Integer) {
			i=((Integer) first).compareTo((Integer) second);
		}
		else if (first instanceof Double) {
			i=((Double) first).compareTo((Double)second);
		}
		else if (first instanceof Boolean) {
			i=((Boolean) first).compareTo((Boolean) second);
		}
		else if (first instanceof Date) {
			i=((Date) first).compareTo((Date)second);
		}
		return i;
	}
	public static String combineValuesWithOr(ArrayList<String> myrelatedValue) {
		String output ="";
		String[] array=myrelatedValue.remove(0).split("");
		for(int i=0;i<array.length;i++){
			if(array[i].equals("0")){
				for(int c=0;c<myrelatedValue.size();c++){
					if(myrelatedValue.get(c).charAt(i)=='1'){
						array[i]="1";
						break;
					}else{
						array[i]="0";
					}
				}
			}
			output+=array[i];
		}
		return output;
	}
	public static String combineValuesWithAnd(ArrayList<String> myrelatedValue) {
		String output = "";
		String[] array = myrelatedValue.remove(0).split("");
		for (int i = 0; i < array.length; i++) {
			for (int c = 0; c < myrelatedValue.size(); c++) {
				if (myrelatedValue.get(c).charAt(i) == '1' && array[i].equals("1"))
					array[i] = "1";
				else
					array[i] = "0";

			}
			output += array[i];
		}
		return output;
	}

	public static String combineValuesWithXor(ArrayList<String> myrelatedValue) {
		String output = "";
		String[] array = myrelatedValue.remove(0).split("");
		for (int i = 0; i < array.length; i++) {

			int counter = 0;

			if (array[i].equals("1"))
				counter++;

			for (int c = 0; c < myrelatedValue.size(); c++) {

				if (myrelatedValue.get(c).charAt(i) == '1')
					counter++;

			}

			if (counter % 2 == 0)
				array[i] = "0";

			else
				array[i] = "1";
			output += array[i];
		}
		return output;
	}

	public static String runlengthencoding(String value){
		String[] bits = value.split("");
		String result ="";
		int run =1;
		for(int i=0;i<bits.length;i++){
			if(i<bits.length-1 && bits[i].equals(bits[i+1])){
				run++;
			}else{
				if(run!=0){
					result+=run+bits[i];
					run=1;
				}
			}
		}
		return result;
	}
	public static String runlengthdecoding(String value){
		String[] bits = value.split("");
		String result ="";
		for(int i=0;i<bits.length;i=i+2){
			int run =Integer.parseInt(bits[i]);
			for(int c=0;c<run;c++){
				result+=bits[i+1];
			}
		}
		return result;
	}
	public static page getPage(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		File tableFile =new File(path);
		ObjectInputStream is =new ObjectInputStream(new FileInputStream(tableFile));
		page page=(BestDBMS.page) is.readObject();
		is.close();
		return page;
	}
	public static void writePage(page myPage,String path) throws FileNotFoundException, IOException{
		File tableFile2 =new File(path);
		ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile2));
		os.writeObject(myPage);
		os.close();
	}
	
	public static table getTable(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		File tableFile =new File(path);
		ObjectInputStream is =new ObjectInputStream(new FileInputStream(tableFile));
		table table=(BestDBMS.table) is.readObject();
		is.close();
		return table;
	}
	public static void writeTable(table myTable,String path) throws FileNotFoundException, IOException{
		File tableFile2 =new File(path);
		ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile2));
		os.writeObject(myTable);
		os.close();
	}
	public static BitMap getBitMap(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		File tableFile =new File(path);
		ObjectInputStream is =new ObjectInputStream(new FileInputStream(tableFile));
		BitMap BitMap=(BitMap) is.readObject();
		is.close();
		return BitMap;
	}
	public static void writeBitMap(BitMap myBitMap,String path) throws FileNotFoundException, IOException{
		File tableFile2 =new File(path);
		ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile2));
		os.writeObject(myBitMap);
		os.close();
	}
	
	public static ArrayList<String[]> getMetaData() throws IOException{
		ArrayList<String[]> output =new ArrayList<String[]>();
		File fr =new File("metaData.csv");
		Scanner sc =new Scanner(fr);
		while(sc.hasNextLine()){
			String[] attri =sc.nextLine().split(",");
			output.add(attri);
		}
		sc.close();
		FileWriter fw = new FileWriter("metaData.csv");
		String sr ="";
		for(int i=output.size()-1;i>=0;i--){
			String[] atrr =output.get(i);
			sr=atrr[0]+","+atrr[1]+","+atrr[2]+","+atrr[3]+","+atrr[4]+"\n"+sr;
		}
		fw.write(sr);
		fw.close();
		return output;
	}
	public static void setMetaData(ArrayList<String[]> input /*This method will remove all values and 
	add what in input*/) throws IOException{
		File fr =new File("metaData.csv");
		Scanner sc =new Scanner(fr);
		while(sc.hasNextLine()){
			sc.nextLine().split(",");
		}
		sc.close();
		FileWriter fw = new FileWriter("metaData.csv");
		String sr ="";
		for(int i=input.size()-1;i>=0;i--){
			String[] atrr =input.get(i);
			sr=atrr[0]+","+atrr[1]+","+atrr[2]+","+atrr[3]+","+atrr[4]+"\n"+sr;
		}
		fw.write(sr);
		fw.close();
	}
	public static ArrayList<int[]> setPageSize (int index, int pageSize ,ArrayList<int[]> pageContent){
		for(int i=0;i<pageContent.size();i++){
			if(pageContent.get(i)[0]==index){
				pageContent.remove(i);
			}
		}
		if(pageSize != 0)
			pageContent.add(new int []{index,pageSize});
		return pageContent;
	}
	public static void checkSupported(Hashtable<String,String> entry,ArrayList<String> supportedTypes) throws DBAppException{
		Set<String> ht = entry.keySet();
		Object[] s = ht.toArray();
		for(int i =0;i<s.length;i++){
			if(!supportedTypes.contains(entry.get(s[i]).toLowerCase())){
				throw new DBAppException(entry.get(s[i])+"is not supported type");
			}
		}
	}
	public static void updateAllBitMaps(String tableName) throws ClassNotFoundException, FileNotFoundException, DBAppException, IOException{
		ArrayList<String[]> metaData =getMetaData();
		for(int i=0;i<metaData.size();i++){
			String[] attri =metaData.get(i);
			if(attri[0].equals(tableName)&&attri[4].equals("True")){
				getBitMap(tableName+"/"+tableName+" "+attri[1]+".class").updateBitMap();
			}
		}
	}
	public static boolean checkZero(String indexValue){
		String[] indexValues = indexValue.split("");
		for(int i=0;i<indexValue.length();i++){
			if(indexValues[i].equals("1"))
				return false;
		}
		return true;
	}
	static boolean checkMatch(SQLTerm myCondition,Object myValue) throws DBAppException{
		String ope = myCondition._strOperator;
		Object Value =myCondition._objValue;
		int k =tools.combareTo(myValue, Value);
		switch (ope) {
		case ">":
			if(k<0){
				return true;
			}
			break;
		case ">=":
			if(k<=0){
				return true;			
			}
			break;
		case "<":
			if(k>0){
				return true;			
			}
			break;
		case "<=":
			if(k>=0){
				return true;
			}			
			break;

		case "!=":
			if(k!=0){
				return true;		
			}
			break;
		case "=":
			if(k==0){
				return true;	
			}
			break;
		default:
			throw new DBAppException(ope+"is not supported");
		}
		return false;
	}
}
