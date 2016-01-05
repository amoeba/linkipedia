/**
 * Linkipedia, Copyright (c) 2015 Tetherless World Constellation 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.rpi.tw.linkipedia.search.recognizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.rpi.tw.linkipedia.search.recognizer.EntityRecognizer;
import edu.rpi.tw.linkipedia.search.test.ParallelTesting;

public class ParallelEntityRecognizer extends Thread{
	
	static ArrayList<String> sentences = new ArrayList<String>();
	static ArrayList<String> global_mentions = new ArrayList<String>();
	static int counter = 0;
	static EntityRecognizer eg ;
	private String sentence;
	private ArrayList<String> mentions;
	
	public ParallelEntityRecognizer(){}
	public static void setEntityRecognizer(EntityRecognizer myeg){
		eg = myeg;
	}
	public static void setSentences(ArrayList<String> mysentences){
		sentences = mysentences;
	}
	public static ArrayList<String> getAllMentions(){
		return global_mentions;
	}
	public void run() { 
		while(getCurrentSentence()){
			mentions = eg.getEntityMentions(sentence);
			addToGlobal();
		}
	} 
	public ArrayList<String> getMentions(){
		return mentions;
	}
	public synchronized boolean getCurrentSentence(){
		if(counter == sentences.size())
			return false;
		sentence = sentences.get(counter);
		System.out.println(getName()+" assign "+sentence);
		counter++;
		return true;
		//return mySentence;
	}
	public synchronized void addToGlobal(){
		global_mentions.addAll(mentions);
	}
	public static synchronized void reset(){
		sentences = new ArrayList<String>();
		global_mentions.clear();
		counter = 0;
	}
	
	/*
	public static void main(String [] args) throws InterruptedException{
		

		
		String index = "Dataset/db_surface_form_base";
		float threshold = 1;
		if(args.length >= 1){
			index = args[0];
		}
		
		if(args.length == 2){
			threshold = Float.parseFloat(args[1]);
		}
		
		try{
			InputStreamReader inReader = new InputStreamReader(System.in, "UTF-8");
		    BufferedReader in = new BufferedReader(inReader);
			//EntityRecognizer 
			eg = new EntityRecognizer(index);
			eg.setThreshold(threshold);
		while(true){
			global_mentions.clear();
			String query = "";
			query = in.readLine();
		    if(query.equals("quit"))
		    	break;
		    //query = query.toLowerCase();
			long start = System.currentTimeMillis();
			
			//ArrayList<String> 
			sentences = eg.getSentences(query);
			
			//ArrayList<ParallelTesting> pthreads = new ArrayList<ParallelTesting>();
			//ParallelTesting pthreads[] = new ParallelTesting[2];
			//int senIndex = 0;
			//while(senIndex < sentences.size()){
			ParallelTesting pthread1 = new ParallelTesting();//(sentences.get(senIndex),eg);
			ParallelTesting pthread2 = new ParallelTesting();//(sentences.get(senIndex),eg);
			//ParallelTesting pthread3 = new ParallelTesting();//(sentences.get(senIndex),eg);
			//ParallelTesting pthread4 = new ParallelTesting();//(sentences.get(senIndex),eg);

			pthread1.start();
			pthread2.start();
			//pthread3.start();
			//pthread4.start();
			pthread1.join();
			pthread2.join();
			//pthread3.join();
			//pthread4.join();
			//}
			/*
			for(int i = 0 ; i < sentences.size(); i ++){
				//System.out.println(sentences.get(i));
				ParallelTesting pthread = new ParallelTesting(sentences.get(i),eg);
				pthreads.add(pthread);
				pthread.start();
			}
			for(Thread thread:pthreads){
				thread.join();
			}
			
			//pthread.wait();
			long end = System.currentTimeMillis();

			for(String m:global_mentions){
				System.out.println("mentions: "+m);
			}		
			System.out.println("total time: "+(end-start));
		}
		inReader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		


	}
	*/



}
