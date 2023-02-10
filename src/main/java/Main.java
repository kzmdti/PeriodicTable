import java.util.*;

public class Main {

    Frame h1 = new FrameDefaultImpl("-----");//horizontal line
    Frame v1 = new FrameDefaultImpl("|", "|", "|");//vertical line
    Frame empty = new FrameDefaultImpl("       ", "       ", "       ", "       ", "       ");//empty box
    Frame vertical1 = new FrameDefaultImpl("\\", "/", "\\");//left oriented vertical bar
    Frame vertical2 = new FrameDefaultImpl("/", "\\", "/");//right oriented vertical bar

    public static void main(String[] args) {
        Frame table = new Main().createTable();
        System.out.println(table);


    }

    private Frame createTable(){

        //group elements by row(period)
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

        //Create frames for every row
        List<Frame> rows = new ArrayList<>();
        for(List<ElementList.Element> list : groups){
            Frame row;
            int flag = 0;
            //init the first element. Can be empty box;
            if(list.get(0).getGroup() > 1) {
                row = emptyBox();
                flag = 1;
            }else{
                row = elementBox(list.get(0));
            }
            for(int i = 2, k = 1 - flag; i <= 18 && k < list.size(); i++){
                if(i != list.get(k).getGroup()) {
                    row = row.atLeftOf(emptyBox());
                }
                else{
                    row = row.atLeftOf(elementBox(list.get(k)));
                    k++;
                }
            }
            rows.add(row);
        }


        Frame table = rows.get(0); //init
        //create table
        for (int j = 1 ; j < rows.size(); j++){
            if(j == 7){
                table = table.onTopOf(emptyBox());
            }
            table = table.onTopOf(rows.get(j));
        }

        return table;

    }

    private Frame elementFrame(ElementList.Element element){
        int padding = h1.getList().get(0).length() - element.getSymbol().length();
        //init the symbol to create the width of the box
        Frame symbolFrame = new FrameDefaultImpl(" ".repeat(padding - (padding/2)) + element.getSymbol() + " ".repeat(padding/2));
        Frame atomicNmbFrame = new FrameDefaultImpl(Integer.toString(element.getAtomicNumber()));

        return atomicNmbFrame.onTopOf(symbolFrame);
    }

    private  Frame elementBox(ElementList.Element element){
        Frame elementFrame = elementFrame(element);
        Frame box;
        if(element.getAtomicNumber() == 57 || element.getAtomicNumber() == 71 ){
            box = v1.atLeftOf(elementFrame).atLeftOf(vertical1);
        }else if(element.getAtomicNumber() == 58){
            box = vertical1.atLeftOf(elementFrame).atLeftOf(v1);
        } else if (element.getAtomicNumber() == 89 || element.getAtomicNumber() == 103 ) {
            box = v1.atLeftOf(elementFrame).atLeftOf(vertical2);
        } else if (element.getAtomicNumber() == 90) {
            box = vertical2.atLeftOf(elementFrame).atLeftOf(v1);
        }else{
            box = v1.atLeftOf(elementFrame).atLeftOf(v1);
        }
        box = h1.onTopOf(box.onTopOf(h1));
        return box;
    }

    private Frame emptyBox(){
        return empty;
    }


}
