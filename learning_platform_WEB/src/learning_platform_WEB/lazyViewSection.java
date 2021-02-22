package learning_platform_WEB;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jsf.learning_platform.entities.Section;

public class lazyViewSection implements Serializable {

private LazyDataModel<Section> lazyModel;
private List<Section> datasource;

    private Section selectedSection;
    
   
    
    @PostConstruct
    public void init() {
        lazyModel = new LazyCustomerDataModel(load());
    }

    public LazyDataModel<Section> getLazyModel() {
        return lazyModel;
    }

    public List<Section> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        long rowCount = datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();

        // apply offset & filters
        List<Section> section = datasource.stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());

        // sort
        if (!sortBy.isEmpty()) {
            List<Comparator<Section>> comparators = sortBy.values().stream()
                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<Section> cp = ComparatorUtils.chainedComparator(comparators); // from apache
            section.sort(cp);
        }

        // rowCount
        setRowCount((int) rowCount);

        return section;
    }

   
}
