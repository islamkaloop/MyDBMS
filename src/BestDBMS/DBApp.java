package BestDBMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DBApp {
	private ArrayList<String> supportedTypes =new ArrayList<String>();
	
	public void createMeta(){
		FileOutputStream newFile;
		try {
			newFile = new FileOutputStream("metadata.csv");
			newFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init( ){
			
			supportedTypes.add("java.lang.integer");
			supportedTypes.add("java.lang.string");
			supportedTypes.add("java.lang.double");
			supportedTypes.add("java.lang.boolean");
			supportedTypes.add("java.util.date");
	}
	
	public void createTable(String strTableName,String strClusteringKeyColumn, Hashtable<String,String> htblColNameType ) {
			try{	
			Set<String> ht = htblColNameType.keySet();
			Object[] s = ht.toArray();
			for(int i =0;i<s.length;i++){
				if(!supportedTypes.contains(htblColNameType.get(s[i]).toLowerCase())){
					throw new DBAppException(htblColNameType.get(s[i])+"is not supported type");
				}
			}
			File fr =new File("metaData.csv");
			Scanner sc =new Scanner(fr);
			Stack<String[]> myMeta =new Stack<String[]>();
			boolean found =false;
			while(sc.hasNextLine()){
				String[] attri =sc.nextLine().split(",");
				if(attri[0].equals(strTableName)){
					found=true;
				}
				myMeta.add(attri);
			}
			sc.close();
			if(found){
				throw new DBAppException(strTableName+" is already exist");
			}
			FileWriter fw = new FileWriter("metaData.csv");
			String sr =""; 
			while(!myMeta.isEmpty()){
				String[] atrr =myMeta.pop();
				sr=atrr[0]+","+atrr[1]+","+atrr[2]+","+atrr[3]+","+atrr[4]+"\n"+sr;
			}
			fw.write(sr);
			for(int i =s.length-1;i>=0;i--){
				if(s[i].equals(strClusteringKeyColumn)){
					fw.write(strTableName+","+s[i]+","+htblColNameType.get(s[i])+",True,False\n");
				}
				else{
					fw.write(strTableName+","+s[i]+","+htblColNameType.get(s[i])+",False,False\n");	
				}
			}
			fw.close();
			File file =new File(strTableName);
			file.mkdir();
			File tableFile =new File(strTableName+"/"+strTableName+".class");
			ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile));
			table table =new table(strTableName, strClusteringKeyColumn);
			os.writeObject(table);
			os.close();
			}catch(Exception e){
				JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
			}
	}
	
	public void createBitmapIndex(String strTableName, String strColName){
		try{
			BitMap bitMap=new BitMap(strTableName, strColName);
			tools.writeBitMap(bitMap, strTableName+"/"+strTableName+" "+strColName+".class");
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
	public void insertIntoTable(String strTableName,Hashtable<String,Object> htblColNameValue){
		try{
		File fr =new File("metaData.csv");
		Scanner sc =new Scanner(fr);
		boolean found =false;
		while(sc.hasNextLine()){
			String[] attri =sc.nextLine().split(",");
			if(attri[0].equals(strTableName)){
				found=true;
				Object o =htblColNameValue.get(attri[1]);
				if(o == null){sc.close(); throw new DBAppException("there messing Attribute");}
				if(!Class.forName(attri[2]).isInstance(o)){
					sc.close();
					throw new DBAppException(attri[1]+" should to be of type "+ attri[2]+" not "+o.getClass().getName());
				}
			}
		}		
		sc.close();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		htblColNameValue.put("TouchDate",""+sdf.format(new java.util.Date()));
		if(found){
			ObjectInputStream is =new ObjectInputStream(new FileInputStream(strTableName+"/"+strTableName+".class"));
			table table =(BestDBMS.table) is.readObject();
			is.close();
			table.insertIntoTable(htblColNameValue);
			ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(strTableName+"/"+strTableName+".class"));
			os.writeObject(table);
			os.close();
		}else{
			throw new DBAppException(strTableName+" is not found");
		}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
	public void updateTable(String strTableName,String strKey,Hashtable<String,Object> htblColNameValue ){
		try{
		File fr =new File("metaData.csv");
		Scanner sc =new Scanner(fr);
		boolean found =false;
		while(sc.hasNextLine()){
			String[] attri =sc.nextLine().split(",");
			if(attri[0].equals(strTableName)){
				found =true;
				Object o =htblColNameValue.get(attri[1]);
				if(o != null && !Class.forName(attri[2]).isInstance(o)){
					sc.close();
					throw new DBAppException(o.getClass().getName()+" should to be of type "+ attri[2]);
				}
			}
		}
		sc.close();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		htblColNameValue.put("TouchDate",""+sdf.format(new java.util.Date()));
		if(found){
			ObjectInputStream is =new ObjectInputStream(new FileInputStream(strTableName+"/"+strTableName+".class"));
			table table =(BestDBMS.table) is.readObject();
			is.close();
			table.updateTable(strKey, htblColNameValue);
		}else{
			throw new DBAppException(strTableName+" is not found");
		}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
	public void deleteFromTable(String strTableName,Hashtable<String,Object> htblColNameValue){
		try{
		File fr =new File("metaData.csv");
		Scanner sc =new Scanner(fr);
		boolean found =false;
		while(sc.hasNextLine()){
			String[] attri =sc.nextLine().split(",");
			if(attri[0].equals(strTableName)){
				found=true;
				Object o =htblColNameValue.get(attri[1]);
				if(o != null && !Class.forName(attri[2]).isInstance(o)){
					sc.close();
					throw new DBAppException(o.getClass().getName()+" should to be of type "+ attri[2]);
				}
			}
		}
		sc.close();
		if(found){
			ObjectInputStream is =new ObjectInputStream(new FileInputStream(strTableName+"/"+strTableName+".class"));
			table table =(BestDBMS.table) is.readObject();
			is.close();
			table.deleteFromTable(htblColNameValue);
		}else{
			throw new DBAppException(strTableName+" is not found");
		}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
}
