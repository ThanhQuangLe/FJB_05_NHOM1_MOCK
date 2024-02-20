package fa.mock.DTO.VaccineType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PagingDTO {
    private String pageNum;
    private String pageSize;
    private String input;
}
