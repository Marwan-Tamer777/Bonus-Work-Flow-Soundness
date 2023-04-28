/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.bonus.work.flow.soundness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author 10
 */
public class BonusWorkFlowSoundness {
    //Indicates flow direction.
    final static Integer pt =0;
    final static Integer tp =1;
    
    public static void main(String[] args) {

        //HardCoded TestCases From the Lecture "Lec05_06_PN-WFnets_Soundness-2023.pdf"
        //Please input Places in order of Inital Marking example here (1,0,0,0,0,0,0) is saying the token starts at i
        //As the inital order is (i,p1,p2,p3,p4,p5,o).
        ArrayList<Place> p = new ArrayList<>();
        //PN1
        /*
        p.add(new Place("i"));p.add(new Place("p1"));p.add(new Place("p2"));p.add(new Place("p3"));p.add(new Place("p4"));
        p.add(new Place("p5"));p.add(new Place("o"));
        */
        //PN2
        
        p.add(new Place("i"));p.add(new Place("p1"));p.add(new Place("p2"));p.add(new Place("p3"));p.add(new Place("p4"));
        p.add(new Place("p5"));p.add(new Place("p6"));p.add(new Place("p7"));p.add(new Place("o"));
        
        //PN3
        /*
        p.add(new Place("i"));p.add(new Place("p1"));p.add(new Place("p2"));p.add(new Place("p3"));p.add(new Place("p4"));
        p.add(new Place("o"));
        */
        
        ArrayList<Transition> t= new ArrayList<>();
        //PN1
        /*
        t.add(new Transition("t1"));t.add(new Transition("t2"));t.add(new Transition("t3"));t.add(new Transition("t4"));
        t.add(new Transition("t5"));t.add(new Transition("t6"));
        
        //PN2
        
        t.add(new Transition("t1"));t.add(new Transition("t2"));t.add(new Transition("t3"));t.add(new Transition("t4"));
        t.add(new Transition("t5"));t.add(new Transition("t6"));t.add(new Transition("t7"));t.add(new Transition("t8"));
        */
        //PN3
        /*
        t.add(new Transition("t1"));t.add(new Transition("t2"));t.add(new Transition("t3"));t.add(new Transition("t4"));
        t.add(new Transition("t5"));t.add(new Transition("t6"));
        */
        
        ArrayList<Flow> f = new ArrayList<>();
        //PN1
        /*
        f.add(new Flow(new Place("i"),new Transition("t1")));f.add(new Flow(new Transition("t5"),new Place("o")));
        f.add(new Flow(new Transition("t1"),new Place("p1")));f.add(new Flow(new Place("p4"),new Transition("t4")));
        f.add(new Flow(new Place("p1"),new Transition("t2")));f.add(new Flow(new Transition("t4"),new Place("p5")));
        f.add(new Flow(new Transition("t2"),new Place("p2")));f.add(new Flow(new Place("p5"),new Transition("t6")));
        f.add(new Flow(new Transition("t2"),new Place("p4")));f.add(new Flow(new Transition("t6"),new Place("o")));
        f.add(new Flow(new Place("p2"),new Transition("t3")));f.add(new Flow(new Place("p3"),new Transition("t5")));
        f.add(new Flow(new Transition("t3"),new Place("p3")));
        */
        //PN2
        
        f.add(new Flow(new Place("i"),new Transition("t1"),pt));f.add(new Flow(new Place("p1"),new Transition("t1"),tp));
        f.add(new Flow(new Place("p1"),new Transition("t2"),pt));f.add(new Flow(new Place("p1"),new Transition("t3"),pt));
        f.add(new Flow(new Place("p2"),new Transition("t2"),tp));f.add(new Flow(new Place("p2"),new Transition("t4"),pt));
        f.add(new Flow(new Place("p3"),new Transition("t4"),tp));f.add(new Flow(new Place("p4"),new Transition("t6"),pt));
        f.add(new Flow(new Place("p4"),new Transition("t6"),tp));f.add(new Flow(new Place("p4"),new Transition("t8"),pt));
        f.add(new Flow(new Place("0"),new Transition("t8"),tp));f.add(new Flow(new Place("p5"),new Transition("t3"),tp));
        f.add(new Flow(new Place("p5"),new Transition("t5"),pt));f.add(new Flow(new Place("p6"),new Transition("t5"),tp));
        f.add(new Flow(new Place("p6"),new Transition("t7"),pt));f.add(new Flow(new Place("p7"),new Transition("t7"),tp));
        f.add(new Flow(new Place("p7"),new Transition("t8"),pt));
        
        //PN3
        /*
        f.add(new Flow(new Place("i"),new Transition("t1"),pt));f.add(new Flow(new Place("p2"),new Transition("t1"),tp));
        f.add(new Flow(new Place("p3"),new Transition("t1"),tp));f.add(new Flow(new Place("p2"),new Transition("t2"),pt));
        f.add(new Flow(new Place("p1"),new Transition("t2"),tp));f.add(new Flow(new Place("p1"),new Transition("t3"),pt));
        f.add(new Flow(new Place("p2"),new Transition("t3"),tp));f.add(new Flow(new Place("p3"),new Transition("t5"),pt));
        f.add(new Flow(new Place("p4"),new Transition("t5"),tp));f.add(new Flow(new Place("p4"),new Transition("t4"),pt));
        f.add(new Flow(new Place("p3"),new Transition("t4"),tp));f.add(new Flow(new Place("p2"),new Transition("t6"),pt));
        f.add(new Flow(new Place("p3"),new Transition("t6"),pt));f.add(new Flow(new Place("o"),new Transition("t6"),tp));
        */
        
        ArrayList<Integer> state = new ArrayList<>();
        //Test
        //state.add(0);state.add(1);state.add(0);state.add(1);state.add(0);state.add(0);
        //PN1
        //state.add(1);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);
        //PN2
        state.add(1);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);
        //PN3
        //state.add(1);state.add(0);state.add(0);state.add(0);state.add(0);state.add(0);
        
        WorkFlow wf = new WorkFlow(p, t,f);
        
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
        wf.IsItSound(state);
        wf.printAllAttributes();
        wf.printReachabilityGraph();
        
    }
    
}



