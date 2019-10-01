package de.fhdortmund.j2t.wise2019.storyframework;

import java.util.Objects;

public class Option {
    private static long createdOptions = 0;

    private final long id = ++createdOptions;
    private String text;

    public Option(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
