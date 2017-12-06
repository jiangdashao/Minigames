package me.synapz.paintball.utils;

import me.synapz.paintball.enums.ScoreboardLine;
import me.synapz.paintball.enums.Tag;

import java.util.List;

import static me.synapz.paintball.storage.Settings.THEME;

public class ArrayUtil {

    private final List<String> array;

    public ArrayUtil(List<String> array) {
        this.array = array;
    }

    public void addLine(ScoreboardLine line, String text) {
        array.add(THEME + new MessageBuilder(line.getMessage()).replace(Tag.AMOUNT, text).build());
    }

    public void addLine(ScoreboardLine line, int value) {
        array.add(THEME + new MessageBuilder(line.getMessage()).replace(Tag.AMOUNT, "" + value).build());
    }

    public void addLine(ScoreboardLine line, String text, boolean toAdd) {
        if (toAdd) {
            addLine(line, text);
        }
    }

    public void addLine(ScoreboardLine line, int text, boolean toAdd) {
        if (toAdd) {
            addLine(line, text);
        }
    }

    public void addLine(ScoreboardLine line, boolean toAdd) {
        if (toAdd) {
            addLine(line);
        }
    }

    public void addLine(ScoreboardLine line) {
        array.add(line.toString());
    }

}
