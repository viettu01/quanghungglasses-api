package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationMapper {
    public <T> PaginationDTO<T> mapToPaginationDTO(Page<T> page) {
        PaginationDTO<T> paginationDTO = new PaginationDTO<>();
        paginationDTO.setContent(page.getContent());
        paginationDTO.setTotalPages(page.getTotalPages());
        paginationDTO.setTotalElements(page.getTotalElements());
        paginationDTO.setNumberOfElements(page.getNumberOfElements());
        paginationDTO.setPageNumber(page.getPageable().getPageNumber() + 1);
        paginationDTO.setPageSize(page.getPageable().getPageSize());
        paginationDTO.setFirstElementOnPage(page.getPageable().getPageNumber() * page.getPageable().getPageSize() + 1);
        paginationDTO.setLastElementOnPage(paginationDTO.getFirstElementOnPage() + page.getNumberOfElements() - 1);

        Sort sort = page.getPageable().getSort();
        for (Sort.Order order : sort)
        {
            paginationDTO.setSortBy(order.getProperty());
            paginationDTO.setSortDirection(order.getDirection().name());
        }
        return paginationDTO;
    }
}
