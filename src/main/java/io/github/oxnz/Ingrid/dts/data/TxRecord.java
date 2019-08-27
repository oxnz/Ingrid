package io.github.oxnz.Ingrid.dts.data;

import io.github.oxnz.Ingrid.dts.TxCategory;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TxRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TxCategory cat;
    private String ref;
    private String state;
    private String city;

    public TxRecord() {
    }

    public TxRecord(TxCategory cat, String ref, String state, String city) {
        this.cat = cat;
        this.ref = ref;
        this.state = state;
        this.city = city;
    }


    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public TxCategory getCat() {
        return cat;
    }

    public String getRef() {
        return ref;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", id)
                .append("cat", cat)
                .append("ref", ref)
                .append("state", state)
                .append("city", city)
                .toString();
    }
}
