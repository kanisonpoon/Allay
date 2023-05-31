package cn.allay.server.utils.palette.bitarray;

import lombok.Getter;
import org.cloudburstmc.math.GenericMath;

import java.util.Arrays;

/**
 * Author: JukeboxMC | daoge_cmd <br>
 * Date: 2023/4/14 <br>
 * Allay Project <br>
 */
@Getter
public final class PaddedBitArray implements BitArray {

    private final int[] words;
    private final BitArrayVersion version;
    private final int size;

    public PaddedBitArray(BitArrayVersion version, int size, int[] words) {
        this.size = size;
        this.version = version;
        this.words = words;
        int expectedWordsLength = GenericMath.ceil((float) size / version.entriesPerWord);
        if (words.length != expectedWordsLength) {
            throw new IllegalArgumentException("Invalid length given for storage, got: " + words.length +
                    " but expected: " + expectedWordsLength);
        }
    }

    @Override
    public void set(int index, int value) {
        final int arrayIndex = index / this.version.entriesPerWord;
        final int offset = (index % this.version.entriesPerWord) * this.version.bits;
        this.words[arrayIndex] = this.words[arrayIndex] & ~(this.version.maxEntryValue << offset) | (value & this.version.maxEntryValue) << offset;
    }

    @Override
    public int get(int index) {
        final int arrayIndex = index / this.version.entriesPerWord;
        final int offset = (index % this.version.entriesPerWord) * this.version.bits;
        return (this.words[arrayIndex] >>> offset) & this.version.maxEntryValue;
    }

    @Override
    public BitArray copy() {
        return new PaddedBitArray(this.version, this.size, Arrays.copyOf(this.words, this.words.length));
    }
}