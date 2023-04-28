/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bonus.work.flow.soundness;

import java.util.*;


/**
 *
 * @author 10
 */
public class WorkFlow {
    //Indicates flow direction.
    final static Integer pt =0;
    final static Integer tp =1;
    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private ArrayList<Flow> flows;
    private ArrayList<Transition> visitedTransactions;
    private LinkedHashMap< ArrayList<Integer>,ArrayList<ArrayList<Integer>> > possibleStates;
    private ArrayList<ArrayList<Integer>> goalStates;
    private boolean Sound = true;
    
    public WorkFlow(ArrayList<Place> p, ArrayList<Transition> t,ArrayList<Flow> f){
        places = p;
        transitions = t;
        flows = f;
        visitedTransactions = new ArrayList<>();
        possibleStates = new LinkedHashMap<>();
        goalStates = new ArrayList<>();
    }
    
    
    public void IsItSound(ArrayList<Integer> inital){
        ArrayList<ArrayList<Integer>> open = new ArrayList<>();
        open.add(inital);
        this.solveReachabilityGraph(open, new ArrayList<ArrayList<Integer>>());
    }
    
    //An algorithm to explore the reachability graph with Bradth First Open Closed search.
    //A State's children will be added to the open list and the state itelf will be added to the closesd list in each iteration.
    //A Child State won't be added to the open List in 1 of 3 conditions.
    //The same State exisits in the Closed List.
    //The state Last place have a token, the state's element value is 1.
    //The state cannot have any children because of a Transition Deadlock, in this case it will just be moved to closed list.
    
    private void solveReachabilityGraph(ArrayList<ArrayList<Integer>> open,ArrayList<ArrayList<Integer>> closed){
        //Stops the Recursion when open list is empty, meaning there are no more states to explore.
        if (open != null && open.isEmpty()) {return;}
        ArrayList<Integer> currentState = open.get(0);
        //The children are gurantted to not exist in the closed list.
        ArrayList<ArrayList<Integer>> children = this.getStateChildren(currentState,open, closed);

        //An end state is if the token reached the O place (Last Element in the place list)
        //It also checks if there are remanining tokens or not.
        open.remove(0);
        closed.add(currentState);
        
        
        if(currentState.get(currentState.size()-1) == 1){
            goalStates.add(currentState);
            possibleStates.put(currentState,new ArrayList<ArrayList<Integer>>());
            int numOf1s = 0;
            for(int i=0;i<currentState.size();i++){
                if(currentState.get(i)==1){numOf1s++;}
            }
            if(numOf1s>1){Sound=false;}
        }else if(children != null && children.isEmpty()){
        //Check wether the current State is an End state or not.
        //Must be the last checked end state because it is assume it have not reached the goal state.
            //The state is in a deadlock.
            possibleStates.put(currentState,new ArrayList<ArrayList<Integer>>());
            goalStates.add(currentState);
            Sound = false;
        }else{
            possibleStates.put(currentState,children);
            //If not then get it's children,add them to the open list.
            
            for(int x = 0;x<children.size();x++){
                ArrayList<Integer> child = children.get(x);
                
                //Make sure the state dones't exist in the previous visited states.
                boolean exists1 = false;
                for(int y =0;y<closed.size();y++){
                    ArrayList<Integer> tempSate= closed.get(y);
                    int count =0;
                    for(int z =0;z<child.size();z++){
                        if(Objects.equals(child.get(z), tempSate.get(z))){
                            count++;
                        }
                    }
                    if(count==tempSate.size()){exists1 = true;}
                }
                
                //Make sure the state dones't exist in the previous visited states.
                boolean exists2 = false;
                for(int y =0;y<open.size();y++){
                    ArrayList<Integer> tempSate= open.get(y);
                    int count =0;
                    for(int z =0;z<child.size();z++){
                        if(Objects.equals(child.get(z), tempSate.get(z))){
                            count++;
                        }
                    }
                    if(count==tempSate.size()){exists2 = true;}
                }
                
                if(!exists1 && !exists2){open.add(children.get(x));}
                
            }
            
            
        }
        //Recursion till the list is empty
        this.solveReachabilityGraph(open, closed);
    }
    
    
    public ArrayList<ArrayList<Integer>> getStateChildren(ArrayList<Integer> state,ArrayList<ArrayList<Integer>> open,ArrayList<ArrayList<Integer>> visited){
        ArrayList<Place> currentPlaces = new ArrayList<>();
        ArrayList<Flow> possibleFlows = new ArrayList<>();
        ArrayList<Transition> enabledTransitions = new ArrayList<>();
        ArrayList<ArrayList<Integer>> children = new ArrayList<>();
        
        //Put the current Places into the ArrayList.
        for(int i = 0;i< state.size();i++){
            if(state.get(i) == 1){
                currentPlaces.add(places.get(i));
            }
        }
        
        //From the flows see which flows and transtions are enabled by those places.
        possibleFlows = this.getFlowsFromPlaces(currentPlaces);
        //Get Enabled Transitions from Said Flows;
        enabledTransitions = this.getEnabledTransitionsFromFlows(currentPlaces,possibleFlows);
        //Use said Transitions to make new states
        for(int x=0; x<enabledTransitions.size();x++){
            Transition T = enabledTransitions.get(x);
            ArrayList<Integer> stateCopy = new ArrayList<>();
            
            for(int y=0; y<state.size();y++){
                stateCopy.add(state.get(y));
            }
            
            //For possibleFlows which are all pt flows, change the source places values to 0.
            for(int y=0; y<possibleFlows.size();y++){
                if(possibleFlows.get(y).transition.transition.equals(T.transition)){
                    Place p = possibleFlows.get(y).place;
                    for(int z=0;z<places.size();z++){
                        if(places.get(z).place.equals(p.place)){
                            stateCopy.set(z,0);
                        }
                    }
                }
            }
            
            //For possibleFlows which are all tp flows, change the source places values to 1.
            for(int y=0; y<flows.size();y++){
                if(flows.get(y).flow == tp&&flows.get(y).transition.transition.equals(T.transition)){
                    Place p = flows.get(y).place;
                    for(int z=0;z<places.size();z++){
                        if(places.get(z).place.equals(p.place)){
                            stateCopy.set(z,1);
                        }
                    }
                }
            }
            
            children.add(stateCopy);
            
        }
        
        return children;
    }
    
    
    public void printAllAttributes(){
        System.out.println();
        System.out.print("Places: ");
        places.forEach((place) -> System.out.print(place.place + ","));
        System.out.println();
        
        System.out.print("Transitions: ");
        transitions.forEach((transition) -> System.out.print(transition.transition + ","));
        System.out.println();
        
        System.out.print("Flows: ");
        flows.forEach((flow) -> {
                if(flow.flow==tp){
                    System.out.print(flow.transition.transition + " -> " + flow.place.place);
                } else {
                    System.out.print(flow.place.place  + " -> " + flow.transition.transition);
                }
                System.out.print(" || ");
        });
        System.out.println();
        
        
        System.out.print("Enabled Transitions: ");
        visitedTransactions.forEach((transition) -> System.out.print(transition.transition + ","));
        System.out.println();
        System.out.println("Reachabilty Graph States: " + possibleStates);
        System.out.println("ReachabilityEnd States : " +goalStates);
        System.out.println("Is It Sound?: " +Sound);
    }
    
    public void printReachabilityGraph(){
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        ArrayList<ArrayList<Integer>> possibleStatesKeys = new ArrayList<ArrayList<Integer>>(possibleStates.keySet());
        //Make a 2D matrix of all the possible States.
        for(int x=0;x<possibleStates.size();x++){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int y=0;y<possibleStates.size();y++){
                 temp.add(0);
            }
            matrix.add(temp);
        }
        
        //If a state maps to other states change the intersection to 1.
        for(int x=0;x<possibleStates.size();x++){
            ArrayList<ArrayList<Integer>> currentChildren = possibleStates.get(possibleStatesKeys.get(x));
            for(int y=0;y<currentChildren.size();y++){
                int childIndex = -1;
                ArrayList<Integer> currentChild = currentChildren.get(y);
                
                //Get the current child's index in the possible states hashmap.
                for(int z=0;z<possibleStatesKeys.size();z++){
                    boolean exactReplica = true;
                    
                    for(int xy=0;xy<possibleStatesKeys.get(z).size();xy++){
                        if(!Objects.equals(possibleStatesKeys.get(z).get(xy), currentChild.get(xy))){
                            exactReplica = false;
                            break;
                        }
                    }
                    
                    if(exactReplica){childIndex = z;}
                }
                 matrix.get(childIndex).set(x, 1);
            }
        }
        
        for(int x=-1;x<possibleStates.size();x++){
            
            if(x==-1){System.out.format("%30s","");
            }else{System.out.print(possibleStatesKeys.get(x));}
            
            for(int y=0;y<possibleStates.size();y++){
                if(x==-1){
                    System.out.format("%25s",possibleStatesKeys.get(y));
                } else{
                    System.out.format("%25s",matrix.get(x).get(y));
                }
            }
            System.out.println();
        }
    }
    
    //Various Low-Quality Util functions, Hey at least i am honest about my code Quality XD.
    
    //Search for Transitions or Place in a List of flows.
    private ArrayList<Flow> getFlowsFromPlaces(ArrayList<Place> Ps){
        ArrayList<Flow> possibleFlows = new ArrayList<>();
        Flow tempFlow;
        
        for(int x = 0;x<flows.size();x++){
            if(flows.get(x).flow == pt){
                tempFlow = flows.get(x);
                for(int y = 0;y<Ps.size();y++){
                    if(tempFlow.place.place.equals(Ps.get(y).place)){
                        possibleFlows.add(tempFlow);
                    }
                }
            }
        }
        
        return possibleFlows;
    }
    
    //Returns a list of only the enabled transitions by giving it a list of the current places the tokens are on.
    //For each transition (Flow) iterate over all Flows checking where than transitions occurs
    //To check if all of it's required places exist in the given Places the tokens are on.
    //If Yes add that transition to the list.
    private ArrayList<Transition> getEnabledTransitionsFromFlows(ArrayList<Place> Ps,ArrayList<Flow> Fs){
        ArrayList<Transition> enabledTransitions = new ArrayList<>();
        
        //Get Each flow;
        for(int x = 0; x<Fs.size();x++){
            Flow f = Fs.get(x);
            Transition T = f.transition;
            int enabled = 0;
            int repeatedFlows = 0;
            //For each Flow/Transition in the list iterate over it again.
            for(int y = 0; y<flows.size();y++){
                //for each transitions check all the flows that have it then check if that flow's place is in the places list.
                if(Objects.equals(flows.get(y).flow, pt)&&flows.get(y).transition.transition.equals(T.transition)){
                   repeatedFlows++;
                   Place p = flows.get(y).place;
                   for(int z =0; z<Ps.size();z++){
                       if(Ps.get(z).place.equals(p.place)){
                           enabled++;
                       }
                   }
                }
            }
            
            //Checks if a transitions is enabled by seeing how many flows it was the target in.
            //And how many of those flows's places have a token in them.
            if(repeatedFlows == enabled){
                enabledTransitions.add(T);
                
                boolean exists = false;
                for(int z=0;z<visitedTransactions.size();z++){
                    if(visitedTransactions.get(z).transition.equals(T.transition)){
                        exists = true;
                    }
                }
                if(!exists){
                    visitedTransactions.add(T);
                }
            }
        }
        return enabledTransitions;
    }
}

class Place{
    public String place;
    public Place(String p){place =p;}
    public boolean Equals(Place p){
        return (this.place.equals(p.place));
    }
}
class Transition{
    public String transition;
    public Transition(String t){transition =t;}
    public boolean Equals(Transition t){
        return (this.transition.equals(t.transition));
    }
}

class Flow{

    public Place place;
    public Transition transition;
    //0 for P to T, Place to Transitions;
    //1 for T to P, Transitions to Place;
    public Integer flow;
    
    public Flow(Place p,Transition t){
        place = p;
        transition = t;
        flow = 0;
    }
    
    public Flow(Transition t,Place p){
        place = p;
        transition = t;
        flow = 1;
    }
    public Flow(Place p,Transition t,Integer f){
        place = p;
        transition = t;
        flow = f;
    }
    public Flow(Transition t,Place p,Integer f){
        place = p;
        transition = t;
        flow = f;
    }
}