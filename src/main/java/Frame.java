import java.util.List;

public interface Frame {
    Frame atLeftOf(Frame other);
    Frame onTopOf(Frame other);

    List<String> getList();

}
