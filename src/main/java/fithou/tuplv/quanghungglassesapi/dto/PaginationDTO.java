package fithou.tuplv.quanghungglassesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO<T> {
    private List<T> content;
    private int totalPages; // Tổng số trang
    private long totalElements; // Tổng số phần tử trên tất cả các trang
    private int numberOfElements; // Số phần tử trên trang hiện tại
    private int pageSize; // Số phần tử tối đa trên 1 trang
    private int pageNumber; // Số trang hiện tại
    private int firstElementOnPage; // Số thứ tự phần tử đầu tiên trên trang hiện tại
    private int lastElementOnPage; // Số thứ tự phần tử cuối cùng trên trang hiện tại
}
