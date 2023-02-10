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
            result.addAll(this.lines);
            result.addAll(other.getList());
        }
        return new FrameDefaultImpl(result);
    }

    @Override
    public Frame atLeftOf(Frame other) {
        List<String> result = new ArrayList<>();
        //Frame result = new FrameDefaultImpl(lines);

        //In case the dimension are the same the for will exe normally and the next if-else will be ignored
        for(int i = 0; i < Math.min(this.getList().size(), other.getList().size()); i++){
            result.add(this.getList().get(i) + other.getList().get(i));
        }


        if(this.getList().size() > other.getList().size()){ //add the last element if this is bigger
            result.add(this.getList().get(this.getList().size() - 1) + " ".repeat(other.getList().get(0).length()));

        } else if (this.getList().size() < other.getList().size()) { // add the last element if other is bigger
            result.add(" ".repeat(this.getList().get(0).length()) + other.getList().get(other.getList().size() - 1));

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




    private String setCorrectLength(String original, int padding){
        return " ".repeat(padding - (padding/2)) + original + " ".repeat(padding/2);
    }


    @Override
    public List<String> getList() {
        return Collections.unmodifiableList(this.lines);
    }
}
