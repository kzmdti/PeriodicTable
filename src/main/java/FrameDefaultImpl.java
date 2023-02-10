import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FrameDefaultImpl implements Frame {

    private final List<String> lines;

    public FrameDefaultImpl(String...args) {
        this.lines = new ArrayList<>(Arrays.asList(args));
    }
    public FrameDefaultImpl(List<String> lines){
        this.lines = lines;
    }



    @Override
    public Frame onTopOf(Frame other) {
        List<String> result = new ArrayList<>();
        int thisLength = this.lines.get(0).length();
        int otherLength = other.getList().get(0).length();
        if (thisLength > otherLength){
            result.addAll(this.lines);
            for (String s : other.getList()){
                result.add(setCorrectLength(s, thisLength - otherLength));
            }
        }
        else if(thisLength < otherLength){
            for (String s : this.lines){
                result.add(setCorrectLength(s, otherLength - thisLength));
            }
            result.addAll(other.getList());
        }
        else {
            boolean firstLongest = countDash(this.lines.get(this.lines.size()-1), other.getList().get(0));
            if(firstLongest){
                result.addAll(this.lines);
                for(int n = 1; n < other.getList().size(); n++) result.add(other.getList().get(n));
                for(int n = 0; n < this.lines.size()-1; n++) result.add(this.lines.get(n));
                result.addAll(other.getList());

            }
            else{
                for(int n = 0; n < this.lines.size()-1; n++) result.add(this.lines.get(n));
                result.addAll(other.getList());
            }
        }
        return new FrameDefaultImpl(result);
    }

    @Override
    public Frame atLeftOf(Frame other) {
        List<String> result = new ArrayList<>();

        if(this.lines.get(0).length() == 1 || other.getList().get(0).length() == 1){ // case when we are building a box
            for(int i = 0; i < Math.min(this.getList().size(), other.getList().size()); i++){
                result.add(this.getList().get(i) + other.getList().get(i));
            }
            if(this.getList().size() > other.getList().size()){ //add the last element if this is bigger. We are building a box.
                result.add(this.getList().get(this.getList().size() - 1) + " ".repeat(other.getList().get(0).length()));
            }
            else if (this.getList().size() < other.getList().size()) { // add the last element if other is bigger. We are building a box.
                result.add(" ".repeat(this.getList().get(0).length()) + other.getList().get(other.getList().size() - 1));
            }
        }
        else{
            char lastChar = this.lines.get(this.lines.size() / 2).charAt(this.lines.get(this.lines.size() / 2).length() -1);
            char firstChar = other.getList().get(other.getList().size() /2).charAt(0);
            if(lastChar == firstChar || lastChar > ' ' ){
                for(int i = 0; i < this.getList().size(); i++){
                    result.add(this.getList().get(i) + other.getList().get(i).substring(1)); // if are the same keep the left one
                }
            }
            else{
                for(int i = 0; i < this.getList().size(); i++){
                    result.add(this.getList().get(i).substring(0, this.getList().get(i).length()-1) + other.getList().get(i)); // if are the same keep the left one
                }

            }

        }
        return new FrameDefaultImpl(result);
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder();
        for(String e: this.lines)
            a.append(e).append("\n");
        return a.toString();
    }

    private boolean countDash(String a, String b){
        int countA = 0, countB = 0;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == '-') countA++;
            if(b.charAt(i) == '-') countB++;
        }
        return (countA > countB);
    }



    private String setCorrectLength(String original, int padding){
        return " ".repeat(padding - (padding/2)) + original + " ".repeat(padding/2);
    }


    @Override
    public List<String> getList() {
        return Collections.unmodifiableList(this.lines);
    }
}
