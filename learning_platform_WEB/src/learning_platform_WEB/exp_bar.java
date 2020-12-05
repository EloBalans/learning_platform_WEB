package learning_platform_WEB;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;

@Named
@RequestScoped
public class exp_bar {
	private HorizontalBarChartModel  horizontalmodel;
	
	@PostConstruct
	public void init() {
		horizontalmodel = new HorizontalBarChartModel();
		
		ChartSeries exp = new ChartSeries();	
		exp.setLabel("Doœwiadczenie");
			
		exp.set(1, 10.0);
		
		horizontalmodel.addSeries(exp);
		
		
	}
        
}
