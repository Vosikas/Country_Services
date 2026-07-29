package db;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Country extends PanacheEntity{
    @Column(nullable = false)
    public String names;
    @Column
    public String currency;
}
