package BestDBMS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;


public class page implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4828185772414595861L;
	private String strClusteringKeyColumn;
	Vector<Hashtable<String,Object>> tuples =new Vector<Hashtable<String,Object>>();
	
	public page(String tableName,int pageIndex,String strClusteringKeyColumn) throws FileNotFoundException, IOException{
		this.strClusteringKeyColumn=strClusteringKeyColumn;
	    tuples =new Vector<Hashtable<String,Object>>();
	}
	
	public Hashtable<String,Object> addRow(Hashtable<String,Object> tuple) throws FileNotFoundException, IOException, ClassNotFoundException, DBAppException{
			if(tuples.size()==0){
				tuples.add(tuple);
			}else{
				boolean insearted =false;
				int size =tuples.size();
				for (int first =size-1;first>=0;first--) {
					int i=combareTo(tuples.get(first).get(strClusteringKeyColumn), tuple.get(strClusteringKeyColumn));
					if(i<0){
						tuples.add(first+1, tuple);
						insearted=true;
						break;
					}else if(i==0){
						throw new DBAppException("There are entry with this primary key :"+tuple.get(strClusteringKeyColumn));
					}
				}
				if(!insearted){
					tuples.add(0,tuple);
				}
				if(tuples.size()>3){
					Hashtable<String,Object> ss = tuples.lastElement();
					tuples.remove(tuples.lastElement());
					return ss;
				}
			}
		return null;
	}
	
	public void removeRow(Hashtable<String,Object> tuple) throws ClassNotFoundException, IOException, DBAppException{
		Set<String> ht = tuple.keySet();
		Object[] s = ht.toArray();
		int size =s.length;
		for (int first =0;first<tuples.size();) {
			int i=0;
			for(int k=0;k<size ;k++){
				if(tuple.get(s[k]).equals(tuples.get(first).get(s[k]))){
					i++;
				}
			}
			if(i==size){
				tuples.remove(first);
			}else{
				first++;
			}
		}
	}
	
	public  boolean updateRow(String strKey,Hashtable<String,Object> tuple) throws ClassNotFoundException, IOException, DBAppException{
		boolean isUpdated=false;
		for (int first =0;first<tuples.size();first++) {
			Object s=tuples.get(first).get(strClusteringKeyColumn);
			if(s.toString().equals(strKey)){
				Set<String> ht = tuple.keySet();
				Object[] s1 = ht.toArray();
				Hashtable<String,Object> toUpdate = tuples.get(first);
				for(int i=0;i<s1.length;i++){
					toUpdate.put((String) s1[i], tuple.get(s1[i]));
				}
				tuples.remove(first);
				tuples.add(first, toUpdate);
				isUpdated=true;
			}
		}
		return isUpdated;
	}
	public int getSize(){
		return tuples.size();
	}
	public Hashtable<String,Object> getlasttuple(){
		return tuples.lastElement();
	}
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
}
