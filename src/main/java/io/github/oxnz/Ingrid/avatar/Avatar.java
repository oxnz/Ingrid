package io.github.oxnz.Ingrid.avatar;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int size;
    private String alt;

    protected Avatar() {
    }

    public Avatar(int size, String alt) {
        this.size = size;
        this.alt = alt;
    }

    public Long getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public String getAlt() {
        return alt;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", id)
                .append("size", size)
                .append("alt", alt)
                .toString();
    }
}
