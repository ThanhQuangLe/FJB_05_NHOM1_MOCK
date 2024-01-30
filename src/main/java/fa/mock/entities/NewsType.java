package fa.mock.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_type_id")
    private Integer id;

    @Column(columnDefinition = "NVARCHAR(10)")
    private String description;

    @Column(name = "news_type_name" , columnDefinition = "NVARCHAR(50)")
    private String newsTypeName;

    @OneToMany(mappedBy = "newsType")
    private List<News> newsList ;
}
