package fa.mock.DTO.VaccineResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagingDTO {
	private String pageNum;
    private String pageSize;
    private String input;
}
