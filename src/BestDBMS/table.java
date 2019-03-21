package BestDBMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class table implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strTableName;
	private String strClusteringKeyColumn;
	private int lastIndex;

	public table(String strTableName, String strClusteringKeyColumn) throws DBAppException{
		this.strTableName=strTableName;
		this.strClusteringKeyColumn=strClusteringKeyColumn;
		JOptionPane.showMessageDialog(new JFrame(),"Table "+strTableName+" with primaryKey "+strClusteringKeyColumn+" has been successfully Created");
	}
	
	public void insertIntoTable(Hashtable<String,Object> htblColNameValue) throws DBAppException, FileNotFoundException, IOException, ClassNotFoundException{
		File file = new File(strTableName);
		File[] directories = file.listFiles();
		if(directories.length==1){
			lastIndex=0;
			File tableFile =new File(strTableName+"/"+strTableName+" 0.class");
			page page =new page(strTableName,lastIndex,strClusteringKeyColumn);
			page.addRow(htblColNameValue);
			ObjectOutputStream os1 =new ObjectOutputStream(new FileOutputStream(tableFile));
			os1.writeObject(page);
			os1.close();
		}else{
			boolean insearted=false;
			for(int i=0;i < lastIndex+1;i++){
				File tableFile =new File(strTableName+"/"+strTableName+" "+i+".class");
				if(tableFile.exists()){
					ObjectInputStream is =new ObjectInputStream(new FileInputStream(tableFile));
					page page=(BestDBMS.page) is.readObject();
					is.close();
					int k = BestDBMS.page.combareTo(page.getlasttuple().get(strClusteringKeyColumn), htblColNameValue.get(strClusteringKeyColumn));
					if(k>0){
						Hashtable<String,Object> tuple=page.addRow(htblColNameValue);
						insearted=true;
						ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile));
						os.writeObject(page);
						os.close();
						if(tuple != null){
							insertIntoTable(tuple);
						}
					}else if(k==0){
					throw new DBAppException("There are entry with this primary key :"+ htblColNameValue.get(strClusteringKeyColumn));
					}
				}
			}
			if(!insearted){
				File tableFile3 =new File(strTableName+"/"+strTableName+" "+lastIndex+".class");
				if(tableFile3.exists()){
					ObjectInputStream is =new ObjectInputStream(new FileInputStream(tableFile3));
					page page=(BestDBMS.page) is.readObject();
					is.close();
					Hashtable<String,Object> tuple=page.addRow(htblColNameValue);
					ObjectOutputStream os1 =new ObjectOutputStream(new FileOutputStream(tableFile3));
					os1.writeObject(page);
					os1.close();
					if(tuple != null){
						lastIndex=lastIndex+1;
						File tableFile2 =new File(strTableName+"/"+strTableName+" "+lastIndex+".class");
						page page1 =new page(strTableName,lastIndex,strClusteringKeyColumn);
						page1.addRow(tuple);
						ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile2));
						os.writeObject(page1);
						os.close();
				}
				}else{
						lastIndex=lastIndex+1;
						File tableFile2 =new File(strTableName+"/"+strTableName+" "+lastIndex+".class");
						page page1 =new page(strTableName,lastIndex,strClusteringKeyColumn);
						page1.addRow(htblColNameValue);
						ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile2));
						os.writeObject(page1);
						os.close();
				}
			}
		
	}
		JOptionPane.showMessageDialog(new JFrame(),"your entery has been succsesfuly insearted");
	}
	
	public int getLastIndex() {
		return lastIndex;
	}

	public void updateTable(String strKey,Hashtable<String,Object> htblColNameValue )throws DBAppException, ClassNotFoundException, IOException{
		boolean isUpdated=false;
		for(int i=0;i < lastIndex+1;i++){
			File tableFile =new File(strTableName+"/"+strTableName+" "+i+".class");
			if(tableFile.exists()){
				ObjectInputStream is =new ObjectInputStream(new FileInputStream(tableFile));
				page page=(BestDBMS.page) is.readObject();
				is.close();
				if(page.updateRow(strKey, htblColNameValue)){
					isUpdated=true;
					ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(new File(strTableName+"/"+strTableName+" "+i+".class")));
					os.writeObject(page);
					os.close();
					break;
				}
				ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(new File(strTableName+"/"+strTableName+" "+i+".class")));
				os.writeObject(page);
				os.close();
			}
		}
		if(!isUpdated){
			throw new DBAppException("there is no row have "+strClusteringKeyColumn+" = "+strKey);
		}
		JOptionPane.showMessageDialog(new JFrame(),"your entery has been succsesfuly updated");
	}
	
	public void deleteFromTable(Hashtable<String,Object> htblColNameValue)throws DBAppException, ClassNotFoundException, IOException{
		for(int i=0;i < lastIndex+1;i++){
			File tableFile =new File(strTableName+"/"+strTableName+" "+i+".class");
			if(tableFile.exists()){
				FileInputStream fo=new FileInputStream(tableFile);
				ObjectInputStream is =new ObjectInputStream(fo);
				page page=(BestDBMS.page) is.readObject();
				is.close();
				fo.close();
				page.removeRow(htblColNameValue);
				if(page.getSize()==0){
					tableFile.delete();
				}else{
					ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(tableFile));
					os.writeObject(page);
					os.close();
				}
			}
		}
		JOptionPane.showMessageDialog(new JFrame(),"your entery has been succsesfuly deleted");
	}
	
	public String getStrTableName() {
		return strTableName;
	}
}
