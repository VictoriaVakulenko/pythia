package app.controller;

import app.model.PAD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PADController {

    ArrayList<String> predicates = new ArrayList<>();
    public  static  final HashMap<String, String> predicatesAssociation;
    static {
        ArrayList<String> attributes = new ArrayList<>();
        predicatesAssociation = new HashMap<>();
        attributes.add("admired, bold, creative, powerful, vigorous");
        predicatesAssociation.put("+P +A +D", attributes.get(0));
        attributes.clear();
        attributes.add("amazed, awed, fascinated, impressed, infatuated");
        predicatesAssociation.put("+P +A -D", attributes.get(0));
        attributes.clear();
        attributes.add("comfortable, leisurely, relaxed, satisfied, unperturbed");
        predicatesAssociation.put("+P -A +D", attributes.get(0));
        attributes.clear();
        attributes.add("consoled, docile, protected, sleepy, tranquilized");
        predicatesAssociation.put("+P -A -D", attributes.get(0));
        attributes.clear();
        attributes.add("antagonistic, belligerent, cruel, hateful, hostile");
        predicatesAssociation.put("-P +A +D", attributes.get(0));
        attributes.clear();
        attributes.add("bewildered, distressed, humiliated, in pain, upset");
        predicatesAssociation.put("-P +A -D", attributes.get(0));
        attributes.clear();
        attributes.add("disdainful, indifferent, selfish-uninterested, uncaring, unconcerned");
        predicatesAssociation.put("-P -A +D", attributes.get(0));
        attributes.clear();
        attributes.add("bored, depressed, dull, lonely, sad");
        predicatesAssociation.put("-P -A -D", attributes.get(0));
    }

    public PADController(){}

    public void showPAD(ArrayList<PAD> pads){
        for(PAD p : pads){
            System.out.println("Pleasure: " + p.getPleasure() + " Arousal: " + p.getArousal() + " Dominance: " + p.getDominance());
        }
    }

    public void showDirectAssociation(ArrayList<PAD> pads){
        ArrayList<String> predicates = getPredicates(pads);
        for(String s : predicates){
            if(predicatesAssociation.containsKey(s)){
                String association = predicatesAssociation.get(s);
                System.out.println(association);
            } else {
                System.out.println("Predicate " + s + " is unidentified!");
            }
        }
    }

    public String getAssociations(PAD pad){
        String predicate = createPredicate(pad);
        String association = predicatesAssociation.get(predicate);
        return association;
    }

    public void showPredicates(ArrayList<PAD> pads){
        predicates = getPredicates(pads);
        for(String p : predicates){
            System.out.println(p);
        }
    }

    public ArrayList<String> getPredicates(ArrayList<PAD> pads){
        formatePredicates(pads);
        return predicates;
    }

    public String createPredicate(PAD p){
        String predicate;
        if(p.getPleasure() < 0){
            predicate = "-P";
        } else {
            predicate = "+P";
        }
        if(p.getArousal() < 0){
            predicate += " -A";
        } else{
            predicate +=" +A";
        }
        if(p.getDominance() < 0){
            predicate += " -D";
        } else {
            predicate += " +D" ;
        }
        return predicate;
    }

    public void formatePredicates(ArrayList<PAD> pads){
        String predicate;
        for(PAD p : pads){
            if(p.getPleasure() < 0){
                predicate = "-P";
            } else {
                predicate = "+P";
            }
            if(p.getArousal() < 0){
                predicate += " -A";
            } else{
                predicate +=" +A";
            }
            if(p.getDominance() < 0){
                predicate += " -D";
            } else {
                predicate += " +D" ;
            }
            predicates.add(predicate);
        }
    }

}
