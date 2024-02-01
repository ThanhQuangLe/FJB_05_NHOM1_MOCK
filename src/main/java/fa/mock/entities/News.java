package fa.mock.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Integer id;

    @Column(columnDefinition = "Text")
    private String content;


//    @Column(columnDefinition = "NVARCHAR(1000)")
//    private String preview;

    @Column(columnDefinition = "NVARCHAR(300)")
    private String tittle;

    @ManyToOne
    @JoinColumn(name = "news_type_id")
    private NewsType newsType;
}
