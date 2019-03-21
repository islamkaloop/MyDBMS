package BestDBMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class tools {
	public static String combineValuesWithOr(ArrayList<String> myrelatedValue) {
		String output ="";
		String[] array=myrelatedValue.remove(0).split("");
		for(int i=0;i<array.length;i++){
			for(int c=0;c<myrelatedValue.size();c++){
				if(myrelatedValue.get(c).charAt(i)=='1'){
					array[i]="1";
				}else{
					array[i]="0";
				}
			}
			output+=array[i];
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
		for(int i=input.size()-1;i>=0;i--){
			String[] atrr =output.get(i);
			sr=atrr[0]+","+atrr[1]+","+atrr[2]+","+atrr[3]+","+atrr[4]+"\n"+sr;
		}
		fw.write(sr);
		fw.close();
	}
}
