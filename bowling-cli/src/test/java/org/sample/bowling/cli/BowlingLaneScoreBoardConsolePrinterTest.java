package org.sample.bowling.cli;

import org.junit.Test;
import org.sample.bowling.domain.BowlingLane;
import org.sample.bowling.domain.Player;

/**
 *
 * @author jmnunezizu
 */
public class BowlingLaneScoreBoardConsolePrinterTest {

    private BowlingLaneScoreBoardConsolePrinter underTest = new BowlingLaneScoreBoardConsolePrinter();

    @Test
    public void scoreBoardForSinglePlayerAllStrikes() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("Mr Bowl"));
        bl.start();

        for (int i = 0; i < 12; i++) {
            bl.dropPins(10);
        }

        underTest.printScoreBoard(bl);
    }

    @Test
    public void scoreBoardForSinglePlayerMixed() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("Mr Bowl"));
        bl.start();

        // fr1, strike
        bl.dropPins(10);

        // fr2, spare
        bl.dropPins(3);
        bl.dropPins(7);

        // fr3, open
        bl.dropPins(4);
        bl.dropPins(3);

        // fr4, open
        bl.dropPins(2);
        bl.dropPins(4);

        // fr5, strike
        bl.dropPins(10);

        // fr6, spare
        bl.dropPins(9);
        bl.dropPins(1);

        // fr7, open
        bl.dropPins(2);
        bl.dropPins(2);

        // fr8, strike
        bl.dropPins(10);

        // fr9, strike
        bl.dropPins(10);

        // fr10, open
        bl.dropPins(8);
        bl.dropPins(1);


        underTest.printScoreBoard(bl);
    }

    @Test
    public void scoreBoardMultiplePlayersMixed() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("Mr Bowl"));
        bl.addPlayer(Player.newWithName("Bowler Van Pin"));
        bl.start();

        // p1, fr1, strike
        bl.dropPins(10);

        // p2, fr1, strike
        bl.dropPins(10);

        // p1, fr2, open
        bl.dropPins(5);
        bl.dropPins(2);

        // p2, fr2, open
        bl.dropPins(3);
        bl.dropPins(3);

        // p1, fr3, strike
        bl.dropPins(10);

        // p2, fr3, spare
        bl.dropPins(9);
        bl.dropPins(1);

        // p1, fr4, strike
        bl.dropPins(10);

        // p2, fr4, strike
        bl.dropPins(10);

        // p1, fr5, strike
        bl.dropPins(10);

        // p2, fr5, strike
        bl.dropPins(10);

        // p1, fr6, strike
        bl.dropPins(10);

        // p2, fr6, strike
        bl.dropPins(10);

        // p1, fr7, spare
        bl.dropPins(9);
        bl.dropPins(1);

        // p2, fr7, strike
        bl.dropPins(10);

        // p1, fr8, strike
        bl.dropPins(10);

        // p2, fr8, strike
        bl.dropPins(10);

        // p1, fr9, open
        bl.dropPins(3);
        bl.dropPins(2);

        // p2, fr9, strike
        bl.dropPins(10);

        // p1, fr10, spare
        bl.dropPins(8);
        bl.dropPins(2);
        bl.dropPins(10);

        // p2, fr10, strike
        bl.dropPins(10);
        bl.dropPins(10);
        bl.dropPins(10);

        underTest.printScoreBoard(bl);
    }

}
