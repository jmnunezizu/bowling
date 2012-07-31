package org.sample.bowling.cli;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.sample.bowling.domain.BowlingLane;
import org.sample.bowling.domain.Frame;
import org.sample.bowling.domain.Player;

/**
 *
 * @author jmnunezizu
 */
public class BowlingLaneScoreBoardConsolePrinter {

    private static final String ROW_FORMAT = "| %1$-15s | %2$-10s | %3$-10s | %4$-10s | %5$-10s | %6$-10s | %7$-10s | %8$-10s | %9$-10s | %10$-10s | %11$-10s | %12$-10s |\n";

    private PrintStream out = System.out;

    /**
     * Prints the score board.
     */
    public void printScoreBoard(final BowlingLane bl) {
        printTableHeader();

        for (Player player : bl.getPlayers()) {
            List<String> frameForScoring = new ArrayList<String>(10);
            frameForScoring.add(player.getName());
            for (Frame frame : player.getFrames()) {
                frameForScoring.add(frame.getDisplayValue());
            }
            frameForScoring.add(String.valueOf(player.getTotalScore()));
            out.format(ROW_FORMAT, frameForScoring.toArray());
        }
    }

    /**
     * Prints the score board table header.
     */
    private void printTableHeader() {
        out.format(
                ROW_FORMAT,
                "Player", "Frame 1", "Frame 2", "Frame 3", "Frame 4", "Frame 5",
                "Frame 6", "Frame 7", "Frame 8", "Frame 9", "Frame 10", "Total"
        );
    }

}
