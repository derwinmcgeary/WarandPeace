# WarandPeace

Compile a frequency table for War and Peace using Java.

This is just a quick exercise in Java IO.

## What is a word?

There are plenty of definitions, but for this program now a word is Latin
alphabet characters (plus "-" which is used in compounds and "'" for possessives) surrounded by non-alphabet characters.  However if we allow words to contain apostrophes then "ermalov's" is counted as a separate word from "ermalov" which seems suboptimal. I have taken this to be better than having "s" and "ll" counted as high-frequency English words, although I am open to debate on this.
