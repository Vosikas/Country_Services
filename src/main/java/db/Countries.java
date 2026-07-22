package db;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Countries extends PanacheEntity{
    @Column(nullable = false)
    public String names;
    @Column
    public String currency;

    public String getName(String name){
        return name;
    }

}
