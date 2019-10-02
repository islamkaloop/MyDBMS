package BestDBMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BitMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9193183394264530856L;
	private String strTableName;
	private String strColumeName;
	private int lastIndex;
	
	public BitMap(String strTableName,String strColumeName) throws DBAppException, ClassNotFoundException, IOException{
		this.strTableName=strTableName;
		this.strColumeName=strColumeName;
		this.createBitMap();
		JOptionPane.showMessageDialog(new JFrame(),"Index on Column "+strColumeName+" in "+strTableName+" has been created");
	}
	
	public void createBitMap() throws DBAppException, ClassNotFoundException, IOException{
		ArrayList<String []> metaData =tools.getMetaData();
		boolean found =false;
		for(int i=0;i<metaData.size();i++){
			String[] attri =metaData.get(i);
			if(attri[0].equals(strTableName)&&attri[1].equals(strColumeName)){
				found=true;
			}
		}
		if(!found){
			throw new DBAppException("We Can not found table name or the colume you want");
		}
		ArrayList<Object> allValues = getValues(strTableName,strColumeName);
		ArrayList<Object> uniqeValues=new ArrayList<Object>();
		for(int i = 0;i<allValues.size();i++){
			if(!uniqeValues.contains(allValues.get(i))){
				uniqeValues.add(allValues.get(i));
			}
		}		
		for(int i = 0;i<uniqeValues.size();i++){
			String index ="";
			for(int c =0 ;c<allValues.size();c++){
				if(uniqeValues.get(i).equals(allValues.get(c))){
					index+="1";
				}else{
					index+="0";
				}
			}
			Hashtable<String,Object> htblindex = new Hashtable<String,Object>();
			htblindex.put("Attribute",uniqeValues.get(i));
			htblindex.put("Value", tools.runlengthencoding(index));
			this.insertIntoBitMap(htblindex);
		}
	
		for(int i=0;i<metaData.size();i++){
			String[] atrr =metaData.get(i);
			if(atrr[0].equals(strTableName)&&atrr[1].equals(strColumeName)){
				atrr[4]="True";
			}
		}
		tools.setMetaData(metaData);
	}
	
	public void updateBitMap() throws DBAppException, ClassNotFoundException, IOException{
		ArrayList<String []> metaData =tools.getMetaData();
		boolean found =false;
		for(int i=0;i<metaData.size();i++){
			String[] attri =metaData.get(i);
			if(attri[0].equals(strTableName)&&attri[1].equals(strColumeName)){
				found=true;
			}
		}
		if(!found){
			throw new DBAppException("We Can not found table name or the colume you want");
		}
		ArrayList<Object> allValues = getValues(strTableName,strColumeName);
		ArrayList<Object> uniqeValues=new ArrayList<Object>();
		for(int i = 0;i<allValues.size();i++){
			if(!uniqeValues.contains(allValues.get(i))){
				uniqeValues.add(allValues.get(i));
			}
		}		
		for(int i = 0;i<uniqeValues.size();i++){
			String index ="";
			for(int c =0 ;c<allValues.size();c++){
				if(uniqeValues.get(i).equals(allValues.get(c))){
					index+="1";
				}else{
					index+="0";
				}
			}
			Hashtable<String,Object> htblindex = new Hashtable<String,Object>();
			htblindex.put("Attribute",uniqeValues.get(i));
			htblindex.put("Value", tools.runlengthencoding(index));
			updateIntoBitMap(htblindex);
		}
	}
	
	public void removeBitMap() {
		File tableFile =new File(strTableName+"/"+strTableName+" "+strColumeName+".class");
		if(tableFile.exists()){
			tableFile.delete();
		}
		for(int i=0;i < lastIndex+1;i++){
			File tableFile1 =new File(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
			if(tableFile1.exists()){
				tableFile1.delete();
			}
		}
		lastIndex=0;
	}

	public void updateIntoBitMap(Hashtable<String,Object> indexRow) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		for(int i=0;i < lastIndex+1;i++){
			File tableFile =new File(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
			if(tableFile.exists()){
				page page=tools.getPage(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
				if(tools.checkZero((String) indexRow.get("Value"))){
					page.removByID((String) indexRow.get("Attribute"));
				}else{
					int k = tools.combareTo(page.getlasttuple().get("Attribute"), indexRow.get("Attribute"));
					if(k>0){
						if(!page.updateRow((String)indexRow.get("Attribute"),indexRow)){
							Hashtable<String,Object> tuple=page.addRow(indexRow);
							if(tuple != null){
								insertIntoBitMap(tuple);
							}
						}
					}else if(k==0){
						page.updateRow((String)indexRow.get("Attribute"),indexRow);
					}
					tools.writePage(page, strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
					break;
				}
			}
		}
	}
	public void insertIntoBitMap(Hashtable<String,Object> indexRow) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
			boolean insearted=false;
			for(int i=0;i < lastIndex+1;i++){
				File tableFile =new File(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
				if(tableFile.exists()){
					page page=tools.getPage(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
					int k = tools.combareTo(page.getlasttuple().get("Attribute"), indexRow.get("Attribute"));
					if(k>0){
						Hashtable<String,Object> tuple=page.addRow(indexRow);
						insearted=true;
						tools.writePage(page, strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
						if(tuple != null){
							insertIntoBitMap(tuple);
						}
					}else if(k==0){
						insearted=true;
					}
				}
			}
			if(!insearted){
				File tableFile3 =new File(strTableName+"/"+strTableName+" "+strColumeName+" "+lastIndex+".class");
				if(tableFile3.exists()){
					page page= tools.getPage(strTableName+"/"+strTableName+" "+strColumeName+" "+lastIndex+".class");
					Hashtable<String,Object> tuple=page.addRow(indexRow);
					tools.writePage(page, strTableName+"/"+strTableName+" "+strColumeName+" "+lastIndex+".class");;
					if(tuple != null){
						lastIndex=lastIndex+1;
						page page1 =new page(strTableName,"Attribute");
						page1.addRow(tuple);
						tools.writePage(page1, strTableName+"/"+strTableName+" "+strColumeName+" "+lastIndex+".class");
				}
				}else{
						page page1 =new page(strTableName,"Attribute");
						page1.addRow(indexRow);
						tools.writePage(page1, strTableName+"/"+strTableName+" "+strColumeName+" "+lastIndex+".class");
				}
		}
	}
	
	public String getvalueOf(Object value,String Operation) throws FileNotFoundException, IOException, ClassNotFoundException, DBAppException{
		String output ="";
		ArrayList<String> myrelatedValue =new ArrayList<String>();
		for(int i=0;i < lastIndex+1;i++){
			File tableFile =new File(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
			if(tableFile.exists()){
				page page=tools.getPage(strTableName+"/"+strTableName+" "+strColumeName+" "+i+".class");
				
				for(int c=0;c<page.getSize();c++){
					int k = tools.combareTo(page.tuples.get(c).get("Attribute"),value);
					switch (Operation) {
					case ">":
						if(k<0){
							myrelatedValue.add(tools.runlengthdecoding(page.tuples.get(c).get("Value")+""));
						}
						break;
					case ">=":
						if(k<=0){
							myrelatedValue.add(tools.runlengthdecoding(page.tuples.get(c).get("Value")+""));
						}
						break;
					case "<":
						if(k>0){
							myrelatedValue.add(tools.runlengthdecoding(page.tuples.get(c).get("Value")+""));
						}
						break;
					case "<=":
						if(k>=0){
							myrelatedValue.add(tools.runlengthdecoding(page.tuples.get(c).get("Value")+""));
						}
						break;

					case "!=":
						if(k!=0){
							myrelatedValue.add(tools.runlengthdecoding(page.tuples.get(c).get("Value")+""));
						}
						break;
					case "=":
						if(k==0){
							myrelatedValue.add(tools.runlengthdecoding(page.tuples.get(c).get("Value")+""));
						}
						break;
					default:
						throw new DBAppException(Operation+"is not supported");
					}
				}
			}
		}
		if(myrelatedValue.isEmpty()){
			return "";
		}
		output=tools.combineValuesWithOr(myrelatedValue);
		return output;
	}

	private ArrayList<Object> getValues(String strTableName,String strColumneName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ArrayList<Object> output =new ArrayList<Object>(); 
		table table =tools.getTable(strTableName+"/"+strTableName+".class");
		int lastIndex =table.getLastIndex();
		for(int i=0;i < lastIndex+1;i++){
			File tableFile =new File(strTableName+"/"+strTableName+" "+i+".class");
			if(tableFile.exists()){
				page page=tools.getPage(strTableName+"/"+strTableName+" "+i+".class");
				int size=page.tuples.size();
				for(int c=0;c<size;c++){
					output.add(page.tuples.get(c).get(strColumneName));
				}
			}
		}
		return output;
	}
	
}
