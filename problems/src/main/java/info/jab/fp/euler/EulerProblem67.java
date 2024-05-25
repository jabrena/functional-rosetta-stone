package info.jab.fp.euler;

import io.vavr.Function3;
import io.vavr.collection.Vector;

public class EulerProblem67 {

    public final Function3<Vector<Vector<Integer>>, Integer, Integer, Integer> VAVRSolution = Function3.of(
            (Vector<Vector<Integer>> tr, Integer row, Integer col) -> {
                int value = tr.get(row).get(col);
                if (row == tr.length() - 1) {
                    return tr.get(row).get(col);
                } else {
                    return value + Math.max(this.VAVRSolution.apply(tr, row + 1, col), this.VAVRSolution.apply(tr, row + 1, col + 1));
                }
            }
    ).memoized();

}
