package BestDBMS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Selector {
	public String compineWithAndOneIndex(String indexedColumen,SQLTerm NotIndexing) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		String TableName =NotIndexing._strTableName;
		String[] ArrindexedColumen =indexedColumen.split("");
		table table =tools.getTable(TableName+"/"+TableName+".class");
		ArrayList<int[]> pageContent =table.pageContent;
		String Output="";
		for(int i =0;i<ArrindexedColumen.length;i++){
			if(ArrindexedColumen[i].equals("1")){
				int x=0;
				int pageIndex = 0;
				int numperOfarray=0;
				for(int c=0;c<pageContent.size();c++){
					x+=pageContent.get(c)[1];
					if(i+1<=x){
						pageIndex=pageContent.get(c)[0];
						break;
					}else{
						numperOfarray=pageContent.get(c)[1];
					}
				}
				page page =tools.getPage(TableName+"/"+TableName+" "+pageIndex+".class");
				int indexInPage = (i+1)-numperOfarray;
				Hashtable<String,Object> MyRow = page.tuples.get(indexInPage);
				Object Value = MyRow.get(NotIndexing._strColumnName);
				if(tools.checkMatch(NotIndexing, Value)){
					Output+="1";
				}else{
					Output+="0";
				}
			}else{
				Output+="0";
			}
		}
		return Output;
	}
	public String compineWithAndNoIndex(SQLTerm NotIndexing_1,SQLTerm NotIndexing_2) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		String TableName =NotIndexing_1._strTableName;
		table table =tools.getTable(TableName+"/"+TableName+".class");
		int pageLAstIndex =table.getLastIndex();
		String Output="";
		for(int i =0;i<pageLAstIndex+1;i++){
				page page =tools.getPage(TableName+"/"+TableName+" "+i+".class");
				for(int c=0;c<page.tuples.size();c++){
					Hashtable<String,Object> MyRow = page.tuples.get(c);
					Object Value1 = MyRow.get(NotIndexing_1._strColumnName);
					Object Value2 = MyRow.get(NotIndexing_2._strColumnName);
					if(tools.checkMatch(NotIndexing_1, Value1)&&tools.checkMatch(NotIndexing_2, Value2)){
						Output+="1";
					}else{
						Output+="0";
					}
				}
		}
		return Output;
	}
	public String compineWithOrOneIndex(String indexedColumen,SQLTerm NotIndexing) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		String TableName =NotIndexing._strTableName;
		String[] ArrindexedColumen =indexedColumen.split("");
		table table =tools.getTable(TableName+"/"+TableName+".class");
		ArrayList<int[]> pageContent =table.pageContent;
		String Output="";
		for(int i =0;i<ArrindexedColumen.length;i++){
			if(ArrindexedColumen[i].equals("0")){
				int x=0;
				int pageIndex = 0;
				int numperOfarray=0;
				for(int c=0;c<pageContent.size();c++){
					x+=pageContent.get(c)[1];
					if(i+1<=x){
						pageIndex=pageContent.get(c)[0];
						break;
					}else{
						numperOfarray=pageContent.get(c)[1];
					}
				}
				page page =tools.getPage(TableName+"/"+TableName+" "+pageIndex+".class");
				int indexInPage = (i+1)-numperOfarray;
				Hashtable<String,Object> MyRow = page.tuples.get(indexInPage);
				Object Value = MyRow.get(NotIndexing._strColumnName);
				if(tools.checkMatch(NotIndexing, Value)){
					Output+="1";
				}else{
					Output+="0";
				}
			}else{
				Output+="0";
			}
		}
		return Output;
	}
	public String compineWithOrNoIndex(SQLTerm NotIndexing_1,SQLTerm NotIndexing_2) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		String TableName =NotIndexing_1._strTableName;
		table table =tools.getTable(TableName+"/"+TableName+".class");
		int pageLAstIndex =table.getLastIndex();
		String Output="";
		for(int i =0;i<pageLAstIndex+1;i++){
				page page =tools.getPage(TableName+"/"+TableName+" "+i+".class");
				for(int c=0;c<page.tuples.size();c++){
					Hashtable<String,Object> MyRow = page.tuples.get(c);
					Object Value1 = MyRow.get(NotIndexing_1._strColumnName);
					Object Value2 = MyRow.get(NotIndexing_2._strColumnName);
					if(tools.checkMatch(NotIndexing_1, Value1)||tools.checkMatch(NotIndexing_2, Value2)){
						Output+="1";
					}else{
						Output+="0";
					}
				}
		}
		return Output;
	}
	public String compineWithXorOneIndex(String indexedColumen,SQLTerm NotIndexing) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		String TableName =NotIndexing._strTableName;
		String[] ArrindexedColumen =indexedColumen.split("");
		table table =tools.getTable(TableName+"/"+TableName+".class");
		ArrayList<int[]> pageContent =table.pageContent;
		String Output="";
		for(int i =0;i<ArrindexedColumen.length;i++){
			int x=0;
			int pageIndex = 0;
			int numperOfarray=0;
			for(int c=0;c<pageContent.size();c++){
				x+=pageContent.get(c)[1];
				if(i+1<=x){
					pageIndex=pageContent.get(c)[0];
					break;
				}else{
					numperOfarray=pageContent.get(c)[1];
				}
			}
			page page =tools.getPage(TableName+"/"+TableName+" "+pageIndex+".class");
			if(ArrindexedColumen[i].equals("1")){
				int indexInPage = (i+1)-numperOfarray;
				Hashtable<String,Object> MyRow = page.tuples.get(indexInPage);
				Object Value = MyRow.get(NotIndexing._strColumnName);
				if(!tools.checkMatch(NotIndexing, Value)){
					Output+="1";
				}else{
					Output+="0";
				}
			}else{
				int indexInPage = (i+1)-numperOfarray;
				Hashtable<String,Object> MyRow = page.tuples.get(indexInPage);
				Object Value = MyRow.get(NotIndexing._strColumnName);
				if(tools.checkMatch(NotIndexing, Value)){
					Output+="1";
				}else{
					Output+="0";
				}
			}
		}
		return Output;
	}
	public String compineWithXorNoIndex(SQLTerm NotIndexing_1,SQLTerm NotIndexing_2) throws FileNotFoundException, ClassNotFoundException, IOException, DBAppException{
		String TableName =NotIndexing_1._strTableName;
		table table =tools.getTable(TableName+"/"+TableName+".class");
		int pageLAstIndex =table.getLastIndex();
		String Output="";
		for(int i =0;i<pageLAstIndex+1;i++){
				page page =tools.getPage(TableName+"/"+TableName+" "+i+".class");
				for(int c=0;c<page.tuples.size();c++){
					Hashtable<String,Object> MyRow = page.tuples.get(c);
					Object Value1 = MyRow.get(NotIndexing_1._strColumnName);
					Object Value2 = MyRow.get(NotIndexing_2._strColumnName);
					if((tools.checkMatch(NotIndexing_1, Value1)&&!tools.checkMatch(NotIndexing_2, Value2)||(!tools.checkMatch(NotIndexing_1, Value1)&&tools.checkMatch(NotIndexing_2, Value2)))){
						Output+="1";
					}else{
						Output+="0";
					}
				}
		}
		return Output;
	}
	@SuppressWarnings("rawtypes")
	public Iterator FillIter(String TableName, String values) throws FileNotFoundException, ClassNotFoundException, IOException{
		ArrayList<Hashtable<String,Object>> output =new ArrayList<Hashtable<String,Object>>();
		String[] ArrindexedColumen =values.split("");
		table table =tools.getTable(TableName+"/"+TableName+".class");
		ArrayList<int[]> pageContent =table.pageContent;
		for(int i =0;i<ArrindexedColumen.length;i++){
			int x=0;
			int pageIndex = 0;
			int numperOfarray=0;
			for(int c=0;c<pageContent.size();c++){
				x+=pageContent.get(c)[1];
				if(i+1<=x){
					pageIndex=pageContent.get(c)[0];
					break;
				}else{
					numperOfarray=pageContent.get(c)[1];
				}
			}
			page page =tools.getPage(TableName+"/"+TableName+" "+pageIndex+".class");
			int indexInPage = (i+1)-numperOfarray;
			Hashtable<String,Object> MyRow = page.tuples.get(indexInPage);
			output.add(MyRow);
		}
		return output.iterator();
	}

}
