package BestDBMS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

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
			tools.checkSupported(htblColNameType, supportedTypes);
			boolean found =false;
			ArrayList<String []> metaData =tools.getMetaData();
			for(int i=0;i<metaData.size();i++){
				String[] attri =metaData.get(i);
				if(attri[0].equals(strTableName)){
					found=true;
				}
			}
			if(found){
				throw new DBAppException(strTableName+" is already exist");
			}
			Set<String> entrySey = htblColNameType.keySet();
			Object[] entryArray = entrySey.toArray();
			for(int i =entryArray.length-1;i>=0;i--){
				if(entryArray[i].equals(strClusteringKeyColumn)){
					metaData.add(new String[]{strTableName,entryArray[i]+"",htblColNameType.get(entryArray[i]),"True","False"});
				}
				else{
					metaData.add(new String[]{strTableName,entryArray[i]+"",htblColNameType.get(entryArray[i]),"False","False"});
				}
			}
			tools.setMetaData(metaData);
			File file =new File(strTableName);
			file.mkdir();
			table table =new table(strTableName, strClusteringKeyColumn);
			tools.writeTable(table, strTableName+"/"+strTableName+".class");
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
			boolean found =false;
			ArrayList<String []> metaData =tools.getMetaData();
			for(int i=0;i<metaData.size();i++){
				String[] attri =metaData.get(i);
				if(attri[0].equals(strTableName)){
					found=true;
					Object o =htblColNameValue.get(attri[1]);
					if(o == null){throw new DBAppException("there messing Attribute");}
					if(!Class.forName(attri[2]).isInstance(o)){
						throw new DBAppException(attri[1]+" should to be of type "+ attri[2]+" not "+o.getClass().getName());
					}
				}
			}
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			htblColNameValue.put("TouchDate",""+sdf.format(new java.util.Date()));
			if(found){
				table table =tools.getTable(strTableName+"/"+strTableName+".class");
				table.insertIntoTable(htblColNameValue);
				tools.writeTable(table, strTableName+"/"+strTableName+".class");
				tools.updateAllBitMaps(strTableName);
			}else{
				throw new DBAppException(strTableName+" is not found");
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
	public void updateTable(String strTableName,String strKey,Hashtable<String,Object> htblColNameValue ){
		try{
			boolean found =false;
			ArrayList<String []> metaData =tools.getMetaData();
			for(int i=0;i<metaData.size();i++){
				String[] attri =metaData.get(i);
				if(attri[0].equals(strTableName)){
					found =true;
					Object o =htblColNameValue.get(attri[1]);
					if(o != null && !Class.forName(attri[2]).isInstance(o)){
						throw new DBAppException(o.getClass().getName()+" should to be of type "+ attri[2]);
					}
				}
			}
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			htblColNameValue.put("TouchDate",""+sdf.format(new java.util.Date()));
			if(found){
				table table =tools.getTable(strTableName+"/"+strTableName+".class");
				table.updateTable(strKey, htblColNameValue);
				tools.writeTable(table, strTableName+"/"+strTableName+".class");
				tools.updateAllBitMaps(strTableName);
			}else{
				throw new DBAppException(strTableName+" is not found");
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
	public void deleteFromTable(String strTableName,Hashtable<String,Object> htblColNameValue){
		try{
			boolean found =false;
			ArrayList<String []> metaData =tools.getMetaData();
			for(int i=0;i<metaData.size();i++){
				String[] attri =metaData.get(i);
				if(attri[0].equals(strTableName)){
					found=true;
					Object o =htblColNameValue.get(attri[1]);
					if(o != null && !Class.forName(attri[2]).isInstance(o)){
						throw new DBAppException(o.getClass().getName()+" should to be of type "+ attri[2]);
					}
				}
			}
			if(found){
				table table =tools.getTable(strTableName+"/"+strTableName+".class");
				table.deleteFromTable(htblColNameValue);
				tools.writeTable(table, strTableName+"/"+strTableName+".class");
				tools.updateAllBitMaps(strTableName);
			}else{
				throw new DBAppException(strTableName+" is not found");
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	@SuppressWarnings("rawtypes")
	public Iterator selectFromTable(SQLTerm[] arrSQLTerms,String[] strarrOperators){
			try{
				Selector selector = new Selector();
				ArrayList<String>  bitTerms =new ArrayList<>();
				String TableName=arrSQLTerms[0]._strTableName;
				ArrayList<String []> metaDat =tools.getMetaData();
				for(int c = 0; c< arrSQLTerms.length; c++) {
					boolean found =false;
					for(int i = 0; i< metaDat.size(); i++) {
						String[] attri =metaDat.get(i);
						if(attri[0].equals(arrSQLTerms[c]._strTableName)){
							//checking for index && Getting bitValues
							if(attri[1].equals(arrSQLTerms[c]._strColumnName) && attri[4].equals("True")){
								found=true;
								String tableName = arrSQLTerms[c]._strTableName;
								String ColumnName = arrSQLTerms[c]._strColumnName;
								Object objectValue = arrSQLTerms[c]._objValue;
								String operator = arrSQLTerms[c]._strOperator;
								BitMap bitMap = tools.getBitMap(tableName+"/"+tableName+" "+ColumnName+".class");
								bitTerms.add(c, bitMap.getvalueOf(objectValue , operator));
								break;
							}
						}
					}
					if(!found){
						bitTerms.add(c,"");
					}
				}
				ArrayList<SQLTerm> ArrSQLTerms = new ArrayList<>();
				ArrayList<String> StrarrOperators = new ArrayList<>();
				for(int i=0;i<arrSQLTerms.length;i++){
					ArrSQLTerms.add(arrSQLTerms[i]);
					StrarrOperators.add(strarrOperators[i]);
				}
				//handling precedence
				for(int i=0;i<StrarrOperators.size();){
					ArrayList<String> valuesToCombine = new ArrayList<>();
					if(i+1<ArrSQLTerms.size() ){
						if(StrarrOperators.get(i).equals("AND")){
								if(!bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
									valuesToCombine.add(bitTerms.get(i));
									valuesToCombine.add(bitTerms.get(i+1));
									bitTerms.add(i,tools.combineValuesWithAnd(valuesToCombine));
								    bitTerms.remove(i+1);
									ArrSQLTerms.add(null);
									ArrSQLTerms.remove(i+1);
								    StrarrOperators.remove(i);
								}else if(!bitTerms.get(i).equals("")&&bitTerms.get(i+1).equals("")){
									bitTerms.add(i,selector.compineWithAndOneIndex((String)bitTerms.get(i+1), (SQLTerm)ArrSQLTerms.get(i)));
									bitTerms.remove(i+1);
									ArrSQLTerms.add(null);
									ArrSQLTerms.remove(i+1);
									StrarrOperators.remove(i);
								}else if(bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
									bitTerms.add(i,selector.compineWithAndOneIndex((String)bitTerms.get(i), (SQLTerm)ArrSQLTerms.get(i+1)));
									bitTerms.remove(i+1);
									ArrSQLTerms.add(null);
									ArrSQLTerms.remove(i+1);
									StrarrOperators.remove(i);
								}else if(!bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
									bitTerms.add(i,selector.compineWithAndNoIndex(((SQLTerm)ArrSQLTerms.get(i)), (SQLTerm)ArrSQLTerms.get(i+1)));
									bitTerms.remove(i+1);
									ArrSQLTerms.add(null);
									ArrSQLTerms.remove(i+1);
									StrarrOperators.remove(i);
								}
							}else{
								i++;
							}
					}
				}
				for(int i=0;i<StrarrOperators.size();){
					ArrayList<String> valuesToCombine = new ArrayList<>();
					if(i+1<ArrSQLTerms.size() ){
						if(StrarrOperators.get(i).equals("OR")){
							if(!bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
								valuesToCombine.add(bitTerms.get(i));
								valuesToCombine.add(bitTerms.get(i+1));
								bitTerms.add(i,tools.combineValuesWithOr(valuesToCombine));
							    bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
							    StrarrOperators.remove(i);
							}else if(!bitTerms.get(i).equals("")&&bitTerms.get(i+1).equals("")){
								bitTerms.add(i,selector.compineWithOrOneIndex((String)bitTerms.get(i+1), (SQLTerm)ArrSQLTerms.get(i)));
								bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
								StrarrOperators.remove(i);
							}else if(bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
								bitTerms.add(i,selector.compineWithOrOneIndex((String)bitTerms.get(i), (SQLTerm)ArrSQLTerms.get(i+1)));
								bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
								StrarrOperators.remove(i);
							}else if(!bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
								bitTerms.add(i,selector.compineWithOrNoIndex(((SQLTerm)ArrSQLTerms.get(i)), (SQLTerm)ArrSQLTerms.get(i+1)));
								bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
								StrarrOperators.remove(i);
							}
					}else{
						i++;
					}
				}
				}
				for(int i=0;i<StrarrOperators.size();){
					ArrayList<String> valuesToCombine = new ArrayList<>();
					if(i+1<ArrSQLTerms.size() ){
						if(StrarrOperators.get(i).equals("XOR")){
							if(!bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
								valuesToCombine.add(bitTerms.get(i));
								valuesToCombine.add(bitTerms.get(i+1));
								bitTerms.add(i,tools.combineValuesWithXor(valuesToCombine));
							    bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
							    StrarrOperators.remove(i);
							}else if(!bitTerms.get(i).equals("")&&bitTerms.get(i+1).equals("")){
								bitTerms.add(i,selector.compineWithXorOneIndex((String)bitTerms.get(i+1), (SQLTerm)ArrSQLTerms.get(i)));
								bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
								StrarrOperators.remove(i);
							}else if(bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
								bitTerms.add(i,selector.compineWithXorOneIndex((String)bitTerms.get(i), (SQLTerm)ArrSQLTerms.get(i+1)));
								bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
								StrarrOperators.remove(i);
							}else if(!bitTerms.get(i).equals("")&&!bitTerms.get(i+1).equals("")){
								bitTerms.add(i,selector.compineWithXorNoIndex(((SQLTerm)ArrSQLTerms.get(i)), (SQLTerm)ArrSQLTerms.get(i+1)));
								bitTerms.remove(i+1);
								ArrSQLTerms.add(null);
								ArrSQLTerms.remove(i+1);
								StrarrOperators.remove(i);
							}
						}
						else{
							i++;
						}
					}
				}
				if(bitTerms.size()==1){
					return selector.FillIter(TableName, bitTerms.get(0));
				}else{
					return null;
				}
			}catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
			}
			return null;
		}
	
	
}
