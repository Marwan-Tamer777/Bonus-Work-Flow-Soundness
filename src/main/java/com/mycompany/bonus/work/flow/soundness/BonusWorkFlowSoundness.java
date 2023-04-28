/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.bonus.work.flow.soundness;

import java.util.*;


/**
 *
 * @author 10
 */
public class BonusWorkFlowSoundness {
    
    //Indicates flow direction.
    final static Integer pt =0;
    final static Integer tp =1;
    static ArrayList<Place> p = new ArrayList<>();
    static ArrayList<Transition> t= new ArrayList<>();
    static ArrayList<Flow> f = new ArrayList<>();
    static ArrayList<Integer> state = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) {

        System.out.println("Build a workflow manually or use pre-defined lecture examples? 0 for pre-defined 1 for manual.");
        int manual = Integer.parseInt(scan.nextLine());
        
        if(manual==0){
            int exampleNum = 0;
            while(!(exampleNum>0 && exampleNum<4)){
                System.out.print("Please input a number between 1 and 3 to correspomd to lecture examples 1 to 3: ");
                exampleNum = Integer.parseInt(scan.nextLine());
            }
            useClassExamples(exampleNum);
        } else {
            userInput();
        }
        
        
        WorkFlow wf = new WorkFlow(p, t,f);
        wf.IsItSound(state);
        wf.printAllAttributes();
        wf.printReachabilityGraph();
        
    }
    
    public static void userInput(){
        int next = 1;
        String s;
        
        while(next==1){
            System.out.print("Please input a place name in order of it's marking list: ");
            s = scan.nextLine();
            p.add(new Place(s));
            System.out.print("Input Anotet? 1 for yes, 0 for no: ");
            next = Integer.parseInt(scan.nextLine());
        }
        next =1;
        
        while(next==1){
            System.out.print("Please input a Transitions name: ");
            s = scan.nextLine();
            t.add(new Transition(s));
            System.out.print("Input Anotet? 1 for yes, 0 for no: ");
            next = Integer.parseInt(scan.nextLine());
        }
        
        next = 1;
        
        while(next==1){
            System.out.print("Please input a flow's place name: ");
            s = scan.nextLine();
            Place pTemp = new Place(s);
            System.out.print("Please input a flow's transition name: ");
            s = scan.nextLine();
            Transition tTemp = new Transition(s);
            System.out.print("Please input a flow's direction 0 for place to transition, 1 for transition to place: ");
            int direction = Integer.parseInt(scan.nextLine());
            
            switch (direction) {
                case 0:
                    f.add(new Flow(pTemp,tTemp,pt));
                    break;
                case 1:
                    f.add(new Flow(pTemp,tTemp,tp));
                    break;
                default:
                    System.out.print("Error, non-valid direction, please re-input this flow.");
                    break;
            }
            System.out.print("Input Anotet? 1 for yes, 0 for no: ");
            next = Integer.parseInt(scan.nextLine());
        }
        
        for(int i=0;i<p.size();i++){
            if(i==0){state.add(1);}else{state.add(0);}
        }
    }
    
    public static void useClassExamples(int num){
        //HardCoded TestCases (The 3 examples) From the Lecture "Lec05_06_PN-WFnets_Soundness-2023.pdf"
        //Please input Places in order of Inital Marking example here (1,0,0,0,0,0,0) is saying the token starts at i
        //As the inital order is (i,p1,p2,p3,p4,p5,o).
        
        switch(num){
            case 1:
                p.add(new Place("i"));p.add(new Place("p1"));p.add(new Place("p2"));p.add(new Place("p3"));p.add(new Place("p4"));
                p.add(new Place("p5"));p.add(new Place("o"));
                t.add(new Transition("t1"));t.add(new Transition("t2"));t.add(new Transition("t3"));t.add(new Transition("t4"));
                t.add(new Transition("t5"));t.add(new Transition("t6"));
                f.add(new Flow(new Place("i"),new Transition("t1")));f.add(new Flow(new Transition("t5"),new Place("o")));
                f.add(new Flow(new Transition("t1"),new Place("p1")));f.add(new Flow(new Place("p4"),new Transition("t4")));
                f.add(new Flow(new Place("p1"),new Transition("t2")));f.add(new Flow(new Transition("t4"),new Place("p5")));
                f.add(new Flow(new Transition("t2"),new Place("p2")));f.add(new Flow(new Place("p5"),new Transition("t6")));
                f.add(new Flow(new Transition("t2"),new Place("p4")));f.add(new Flow(new Transition("t6"),new Place("o")));
                f.add(new Flow(new Place("p2"),new Transition("t3")));f.add(new Flow(new Place("p3"),new Transition("t5")));
                f.add(new Flow(new Transition("t3"),new Place("p3")));
                state.add(1);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);
                break;
            case 2:
                p.add(new Place("i"));p.add(new Place("p1"));p.add(new Place("p2"));p.add(new Place("p3"));p.add(new Place("p4"));
                p.add(new Place("p5"));p.add(new Place("p6"));p.add(new Place("p7"));p.add(new Place("o"));
                t.add(new Transition("t1"));t.add(new Transition("t2"));t.add(new Transition("t3"));t.add(new Transition("t4"));
                t.add(new Transition("t5"));t.add(new Transition("t6"));t.add(new Transition("t7"));t.add(new Transition("t8"));
                f.add(new Flow(new Place("i"),new Transition("t1"),pt));f.add(new Flow(new Place("p1"),new Transition("t1"),tp));
                f.add(new Flow(new Place("p1"),new Transition("t2"),pt));f.add(new Flow(new Place("p1"),new Transition("t3"),pt));
                f.add(new Flow(new Place("p2"),new Transition("t2"),tp));f.add(new Flow(new Place("p2"),new Transition("t4"),pt));
                f.add(new Flow(new Place("p3"),new Transition("t4"),tp));f.add(new Flow(new Place("p4"),new Transition("t6"),pt));
                f.add(new Flow(new Place("p4"),new Transition("t6"),tp));f.add(new Flow(new Place("p4"),new Transition("t8"),pt));
                f.add(new Flow(new Place("0"),new Transition("t8"),tp));f.add(new Flow(new Place("p5"),new Transition("t3"),tp));
                f.add(new Flow(new Place("p5"),new Transition("t5"),pt));f.add(new Flow(new Place("p6"),new Transition("t5"),tp));
                f.add(new Flow(new Place("p6"),new Transition("t7"),pt));f.add(new Flow(new Place("p7"),new Transition("t7"),tp));
                f.add(new Flow(new Place("p7"),new Transition("t8"),pt));
                state.add(1);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);
                break;
            case 3:
                p.add(new Place("i"));p.add(new Place("p1"));p.add(new Place("p2"));p.add(new Place("p3"));p.add(new Place("p4"));
                p.add(new Place("o"));
                t.add(new Transition("t1"));t.add(new Transition("t2"));t.add(new Transition("t3"));t.add(new Transition("t4"));
                t.add(new Transition("t5"));t.add(new Transition("t6"));
                f.add(new Flow(new Place("i"),new Transition("t1"),pt));f.add(new Flow(new Place("p2"),new Transition("t1"),tp));
                f.add(new Flow(new Place("p3"),new Transition("t1"),tp));f.add(new Flow(new Place("p2"),new Transition("t2"),pt));
                f.add(new Flow(new Place("p1"),new Transition("t2"),tp));f.add(new Flow(new Place("p1"),new Transition("t3"),pt));
                f.add(new Flow(new Place("p2"),new Transition("t3"),tp));f.add(new Flow(new Place("p3"),new Transition("t5"),pt));
                f.add(new Flow(new Place("p4"),new Transition("t5"),tp));f.add(new Flow(new Place("p4"),new Transition("t4"),pt));
                f.add(new Flow(new Place("p3"),new Transition("t4"),tp));f.add(new Flow(new Place("p2"),new Transition("t6"),pt));
                f.add(new Flow(new Place("p3"),new Transition("t6"),pt));f.add(new Flow(new Place("o"),new Transition("t6"),tp));
                state.add(1);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);
                break;
            default:
                
        }
               //HardCode unit testing for the GetChildren function of the workflow;
//        ArrayList<ArrayList<Integer>> tests = wf.getStateChildren(state,new ArrayList<ArrayList<Integer>>(),new ArrayList<ArrayList<Integer>>());
//        tests = wf.getStateChildren(tests.get(0),new ArrayList<ArrayList<Integer>>(),new ArrayList<ArrayList<Integer>>());
//        tests = wf.getStateChildren(tests.get(0),new ArrayList<ArrayList<Integer>>(),new ArrayList<ArrayList<Integer>>());
//        
//        System.out.println("TESTING: ");
//        for(int x=0; x<tests.size();x++){
//            ArrayList<Integer> test = tests.get(x);
//            System.out.print("(");
//            for(int y=0;y<test.size();y++){
//                System.out.print(test.get(y) + ",");
//            }
//            System.out.print("),");
//        }
    }
}



