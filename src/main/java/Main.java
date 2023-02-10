import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    Frame h1 = new FrameDefaultImpl("-----");
    Frame v1 = new FrameDefaultImpl("|", "|", "|");
    Frame empty = new FrameDefaultImpl("       ", "       ", "       ", "       ", "       ");

    public static void main(String[] args) {

        List<List<ElementList.Element>> groups = new ArrayList<>();
        for(ElementList.Element e : new ElementList().elements){
            int row = e.getPeriod();
            if(row > groups.size()){
                List<ElementList.Element> elementsRow = new ArrayList<>();
                groups.add(elementsRow);
                groups.get(row-1).add(e);
            }
            else{
                groups.get(row-1).add(e);
            }

        }


        List<Frame> rows = new ArrayList<>();
        for(List<ElementList.Element> list : groups){
            Frame row;
            int flag = 0;
            //init the first element. Can be empty;
            if(list.get(0).getGroup() > 1) {
                row = new Main().emptyBox();
                flag = 1;
            }else{
                row = new Main().elementBox(list.get(0));
            }
            for(int i = 2, k = 1 - flag; i <= 18 && k < list.size(); i++){
                if(i != list.get(k).getGroup()) {
                    row = row.atLeftOf(new Main().emptyBox());
                }
                else{
                    row = row.atLeftOf(new Main().elementBox(list.get(k)));
                    k++;
                }
            }
            rows.add(row);
        }


        Frame table = rows.get(0);
        for (int j = 1 ; j < rows.size(); j++){
            table = table.onTopOf(rows.get(j));
        }

        System.out.println(table.toString());


    }

    private Frame elementFrame(ElementList.Element element){
        int padding = h1.getList().get(0).length() - element.getSymbol().length();
        Frame symbolFrame = new FrameDefaultImpl(" ".repeat(padding - (padding/2)) + element.getSymbol() + " ".repeat(padding/2));
        Frame atomicNmbFrame = new FrameDefaultImpl(Integer.toString(element.getAtomicNumber()));

        return atomicNmbFrame.onTopOf(symbolFrame);
    }

    private  Frame elementBox(ElementList.Element element){
        Frame elementFrame = elementFrame(element);
        Frame box = v1.atLeftOf(elementFrame).atLeftOf(v1);
        box = h1.onTopOf(box.onTopOf(h1));
        return box;
    }

    private Frame emptyBox(){
        return empty;
    }


}
