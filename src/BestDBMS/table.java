package BestDBMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
	public ArrayList<int[]> pageContent =new ArrayList<int[]>();

	public table(String strTableName, String strClusteringKeyColumn) throws DBAppException{
		this.strTableName=strTableName;
		this.strClusteringKeyColumn=strClusteringKeyColumn;
		JOptionPane.showMessageDialog(new JFrame(),"Table "+strTableName+" with primaryKey "+strClusteringKeyColumn+" has been successfully Created");
	}
	
	public void insertIntoTable(Hashtable<String,Object> htblColNameValue) throws DBAppException, FileNotFoundException, IOException, ClassNotFoundException{
		boolean insearted=false;
		for(int i=0;i < lastIndex+1;i++){
			File tableFile =new File(strTableName+"/"+strTableName+" "+i+".class");
			if(tableFile.exists()){
				page page=tools.getPage(strTableName+"/"+strTableName+" "+i+".class");
				int k = tools.combareTo(page.getlasttuple().get(strClusteringKeyColumn), htblColNameValue.get(strClusteringKeyColumn));
				if(k>0){
					Hashtable<String,Object> tuple=page.addRow(htblColNameValue);
					pageContent = tools.setPageSize(lastIndex, page.getSize(), pageContent);
					insearted=true;
					tools.writePage(page, strTableName+"/"+strTableName+" "+i+".class");
					if(tuple != null){
						insertIntoTable(tuple);
					}
					break;
				}else if(k==0){
					throw new DBAppException("There are entry with this primary key :"+ htblColNameValue.get(strClusteringKeyColumn));
				}
			}
		}
		if(!insearted){
			File tableFile3 =new File(strTableName+"/"+strTableName+" "+lastIndex+".class");
			if(tableFile3.exists()){
				page page=tools.getPage(strTableName+"/"+strTableName+" "+lastIndex+".class");
				Hashtable<String,Object> tuple=page.addRow(htblColNameValue);
				pageContent = tools.setPageSize(lastIndex, page.getSize(), pageContent);
				tools.writePage(page, strTableName+"/"+strTableName+" "+lastIndex+".class");
				if(tuple != null){
					lastIndex=lastIndex+1;
					page page1 =new page(strTableName,strClusteringKeyColumn);
					page1.addRow(tuple);
					pageContent = tools.setPageSize(lastIndex, page1.getSize(), pageContent);
					tools.writePage(page, strTableName+"/"+strTableName+" "+lastIndex+".class");
				}
			}else{
					page page =new page(strTableName,strClusteringKeyColumn);
					page.addRow(htblColNameValue);
					pageContent = tools.setPageSize(lastIndex, page.getSize(), pageContent);
					tools.writePage(page, strTableName+"/"+strTableName+" "+lastIndex+".class");
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
				page page=tools.getPage(strTableName+"/"+strTableName+" "+i+".class");
				if(page.updateRow(strKey, htblColNameValue)){
					pageContent = tools.setPageSize(lastIndex, page.getSize(), pageContent);
					isUpdated=true;
					tools.writePage(page, strTableName+"/"+strTableName+" "+i+".class");
					break;
				}
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
				page page=tools.getPage(strTableName+"/"+strTableName+" "+i+".class");
				page.removeRow(htblColNameValue);
				pageContent = tools.setPageSize(lastIndex, page.getSize(), pageContent);
				if(page.getSize()==0){
					tableFile.delete();
				}else{
					tools.writePage(page, strTableName+"/"+strTableName+" "+i+".class");
				}
			}
		}
		JOptionPane.showMessageDialog(new JFrame(),"your entery has been succsesfuly deleted");
	}
	
	public String getStrTableName() {
		return strTableName;
	}
}
